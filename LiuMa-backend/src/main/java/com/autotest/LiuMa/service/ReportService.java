package com.autotest.LiuMa.service;

//import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.autotest.LiuMa.common.constants.ReportStatus;
import com.autotest.LiuMa.common.excel.*;
import com.autotest.LiuMa.common.exception.LMException;
//import com.autotest.LiuMa.common.utils.FileUtils;
import com.autotest.LiuMa.common.utils.ZipUtils;
import com.autotest.LiuMa.database.domain.Report;
import com.autotest.LiuMa.database.mapper.*;
import com.autotest.LiuMa.dto.ReportCollectionCaseDTO;
import com.autotest.LiuMa.dto.ReportCollectionCaseTransDTO;
import com.autotest.LiuMa.dto.ReportCollectionDTO;
import com.autotest.LiuMa.dto.ReportDTO;
import com.autotest.LiuMa.request.QueryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
//import java.io.File;
import java.io.IOException;
//import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private ReportCollectionMapper reportCollectionMapper;

    @Resource
    private ReportCollectionCaseMapper reportCollectionCaseMapper;

    @Resource
    private ReportCollectionCaseApiMapper reportCollectionCaseApiMapper;

    @Resource
    private ReportCollectionCaseWebMapper reportCollectionCaseWebMapper;

    @Resource
    private ReportCollectionCaseAppMapper reportCollectionCaseAppMapper;

    public void deleteReport(Report report) {
        reportCollectionCaseAppMapper.deleteByReportId(report.getId());
        reportCollectionCaseApiMapper.deleteByReportId(report.getId());
        reportCollectionCaseWebMapper.deleteByReportId(report.getId());
        reportCollectionCaseMapper.deleteByReportId(report.getId());
        reportCollectionMapper.deleteByReportId(report.getId());
        reportMapper.deleteReport(report.getId());
    }

    public List<ReportDTO> getReportList(QueryRequest request) {
        if (request.getCondition() != null && !request.getCondition().equals("")) {
            request.setCondition(("%" + request.getCondition() + "%"));
        }
        return reportMapper.getReportList(request);
    }

    public JSONObject getLastApiReport(String apiId) {
        JSONObject result = new JSONObject();
        String report = reportCollectionCaseApiMapper.getLastApiReport(apiId);
        if (report == null) {
            return null;
        }
        if (!report.contains("<br><b>响应体: ") || !report.contains("</b><br><br>")) {
            return null;
        }
        String response = report.substring(report.indexOf("<br><b>响应体: ") + 12, report.indexOf("</b><br><br>"));
        try {
            result = JSONObject.parseObject(response);
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public ReportCollectionCaseDTO getCaseResult(String taskId) {
        ReportCollectionCaseDTO reportCase = reportCollectionCaseMapper.getCaseReportByTaskId(taskId);
        if (reportCase != null) {
            List<ReportCollectionCaseTransDTO> transList;
            if (reportCase.getCaseType().equals("API")) {
                transList = reportCollectionCaseApiMapper.getReportCaseActionList(reportCase.getId());
            } else if (reportCase.getCaseType().equals("WEB")) {
                transList = reportCollectionCaseWebMapper.getReportCaseActionList(reportCase.getId());
            } else {
                transList = reportCollectionCaseAppMapper.getReportCaseActionList(reportCase.getId());
            }
            reportCase.setTransList(transList);
        }

        return reportCase;
    }

    public ReportDTO getPlanResult(String reportId) {
        ReportDTO report = reportMapper.getReportDetail(reportId);
        List<ReportCollectionDTO> reportCollectionList = reportCollectionMapper.getReportCollectionList(reportId);
        for (ReportCollectionDTO reportCollection : reportCollectionList) {
            List<ReportCollectionCaseDTO> reportCollectionCaseList = reportCollectionCaseMapper.getReportCollectionCaseList(reportCollection.getId());
            for (ReportCollectionCaseDTO reportCollectionCase : reportCollectionCaseList) {
                List<ReportCollectionCaseTransDTO> transList;
                if (reportCollectionCase.getCaseType().equals("API")) {
                    transList = reportCollectionCaseApiMapper.getReportCaseActionList(reportCollectionCase.getId());
                } else if (reportCollectionCase.getCaseType().equals("WEB")) {
                    transList = reportCollectionCaseWebMapper.getReportCaseActionList(reportCollectionCase.getId());
                } else {
                    transList = reportCollectionCaseAppMapper.getReportCaseActionList(reportCollectionCase.getId());
                }
                reportCollectionCase.setTransList(transList);
            }
            reportCollection.setCaseList(reportCollectionCaseList);
            Integer passCount = reportCollectionCaseMapper.countReportCollectionResult(ReportStatus.SUCCESS.toString(), reportCollection.getId());
            Integer failCount = reportCollectionCaseMapper.countReportCollectionResult(ReportStatus.FAIL.toString(), reportCollection.getId());
            Integer errorCount = reportCollectionCaseMapper.countReportCollectionResult(ReportStatus.ERROR.toString(), reportCollection.getId());
            reportCollection.setPassCount(passCount);
            reportCollection.setFailCount(failCount);
            reportCollection.setErrorCount(errorCount);
        }
        report.setCollectionList(reportCollectionList);
        return report;
    }

    public String ReportExporter(String reportId) throws Exception {
        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日

        String dirName = "/usr/local/Liuma/reporter/"+dateFormat.format(date)+reportId;
//        String dirName = "D://Liuma"+dateFormat.format(date);
        Path path = Paths.get(dirName);
        try {
            Path newDir = Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            // the directory already exists.
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
        ReportDTO reports = getPlanResult(reportId);

        String fileName = dirName+"/"+reports.getName().substring(6).replace(":","-") +".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
            WriteSheet HomePage = EasyExcel.writerSheet(0,reports.getName().substring(6).replace(":","-")).head(PlanReport.class).build();
            excelWriter.write(ExportUtils.createHomePage(reports),HomePage);
            WriteSheet CollectionPage = EasyExcel.writerSheet(1,"测试集合汇总信息").head(ReportCollection.class).build();
            excelWriter.write(ExportUtils.CreateCollection(reports),CollectionPage);
            reports.getCollectionList().forEach(reportCollectionDTO -> {
                try(ExcelWriter CollectionWriter = EasyExcel.write(dirName+"/"+reportCollectionDTO.getCollectionName()+".xlsx").build()){
                    WriteSheet CollectionHomePage = EasyExcel.writerSheet(0,reportCollectionDTO.getCollectionName()+"用例情况汇总").head(ReportCollectionCase.class).build();
                    CollectionWriter.write(ExportUtils.CreateCollectionCase(reportCollectionDTO),CollectionHomePage);
                    int i = 0;
                    reportCollectionDTO.getCaseList().forEach(reportCollectionCaseDTO -> {
                        WriteSheet CollectionCasePage = EasyExcel.writerSheet(reportCollectionCaseDTO.getCollectionCaseIndex(),reportCollectionCaseDTO.getCaseName()).head(ReportCollectionCaseTrans.class).build();
                        CollectionWriter.write(ExportUtils.CreateCollectionCasetrans(reportCollectionCaseDTO),CollectionCasePage);
                    });
                }
            });
        }
        try { // 打包文件
            String zipPath = "/usr/local/Liuma/zip/"+dateFormat.format(date);
//            String zipPath = "D:\\zip"+dateFormat.format(date);
            ZipUtils.compress(dirName,zipPath, reports.getId());

            return zipPath+"/"+reports.getId()+".zip";
        } catch (Exception exception) {
            throw new LMException("json文件压缩失败");
        }


    }

//    public String ReportExporHtml(String reportId){
//
//
//    }
}