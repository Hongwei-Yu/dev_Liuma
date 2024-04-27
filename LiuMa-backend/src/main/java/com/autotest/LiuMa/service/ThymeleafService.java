package com.autotest.LiuMa.service;

import com.autotest.LiuMa.dto.ReportDTO;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class ThymeleafService {
    static TemplateEngine templateEngine = new TemplateEngine();
    @Resource
    private ReportService reportService;

    public  String CreatHtml(String reportId) throws IOException {
        if (!templateEngine.isInitialized()) {
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("/templates/");
            //模板文件后缀
            resolver.setSuffix(".html");
            templateEngine.setTemplateResolver(resolver);
        }
//
        ReportDTO report = reportService.getPlanResult(reportId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(report.getStartTime());

        Context context =new Context();
        context.setVariable("name",report.getName());
        context.setVariable("startTime",date);
        context.setVariable("total",report.getTotal());
        context.setVariable("failCount",report.getFailCount());
        context.setVariable("errorCount",report.getErrorCount());
        context.setVariable("run_time",(report.getEndTime()- report.getStartTime())/1000L);
        context.setVariable("updateUser",report.getUpdateUser());
        context.setVariable("passCount",report.getPassCount());
        context.setVariable("passRate",report.getPassRate());
        context.setVariable("collectionList",report.getCollectionList());
        String fileName = report.getName().replace(":",".")+".html";
        FileWriter writer = new FileWriter(fileName);
        templateEngine.process("reports",context,writer);
        writer.close();
        return fileName;
    }
}
