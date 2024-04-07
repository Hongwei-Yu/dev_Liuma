package com.autotest.LiuMa.common.excel;

import com.autotest.LiuMa.common.excel.PlanReport;
import com.autotest.LiuMa.common.excel.ReportCollection;
import com.autotest.LiuMa.common.excel.ReportCollectionCase;
import com.autotest.LiuMa.common.excel.ReportCollectionCaseTrans;
import com.autotest.LiuMa.dto.ReportCollectionCaseDTO;
import com.autotest.LiuMa.dto.ReportCollectionCaseTransDTO;
import com.autotest.LiuMa.dto.ReportCollectionDTO;
import com.autotest.LiuMa.dto.ReportDTO;

import java.util.ArrayList;
import java.util.List;

public class ExportUtils{
    public static PlanReport createHomePage(ReportDTO reports){
        PlanReport HomePage = new PlanReport();
        HomePage.setName(reports.getName());
        HomePage.setUsername(reports.getUsername());
        HomePage.setStatus(reports.getStatus());
        HomePage.setTotal(reports.getTotal());
        HomePage.setPassCount(reports.getPassCount());
        HomePage.setFailCount(reports.getFailCount());
        HomePage.setStartTime(reports.getStartTime());
        HomePage.setEndTime(reports.getEndTime());
        HomePage.setPassRate(reports.getPassRate());
        HomePage.setProgress(reports.getProgress());
        HomePage.setErrorCount(reports.getErrorCount());
        return HomePage;
    }

    public static List<ReportCollection> CreateCollection(ReportDTO reports){
        List<ReportCollection> reportCollections = new ArrayList<>();
        reports.getCollectionList().forEach(reportCollectionDTO -> {
            ReportCollection elem = new ReportCollection();
            elem.setCollectionName(reportCollectionDTO.getCollectionName());
            elem.setCaseTotal(reportCollectionDTO.getCaseTotal());
            elem.setPassCount(reportCollectionDTO.getPassCount());
            elem.setFailCount(reportCollectionDTO.getFailCount());
            elem.setErrorCount(reportCollectionDTO.getErrorCount());
            reportCollections.add(elem);
        });
        return reportCollections;
    }

    public static List<ReportCollectionCase>CreateCollectionCase(ReportCollectionDTO reports){
        List<ReportCollectionCase> reportCollectionCases = new ArrayList<>();
        reports.getCaseList().forEach(reportCollectionCaseDTO -> {
            ReportCollectionCase Case = new ReportCollectionCase();
            Case.setCaseName(reportCollectionCaseDTO.getCaseName());
            Case.setCaseType(reportCollectionCaseDTO.getCaseType());
            Case.setCaseDesc(reportCollectionCaseDTO.getCaseDesc());
            Case.setStatus(reportCollectionCaseDTO.getStatus());
            Case.setDuring(reportCollectionCaseDTO.getDuring());
            Case.setRunTimes(reportCollectionCaseDTO.getRunTimes());
            reportCollectionCases.add(Case);
        });
        return reportCollectionCases;
    }

    public static List<ReportCollectionCaseTrans>CreateCollectionCasetrans(ReportCollectionCaseDTO reports){
        List<ReportCollectionCaseTrans>reportCollectionCaseTrans = new ArrayList<>();
        reports.getTransList().forEach(reportCollectionCaseTransDTO -> {
            ReportCollectionCaseTrans caseTrans = new ReportCollectionCaseTrans();
            caseTrans.setStatus(reportCollectionCaseTransDTO.getStatus());
            caseTrans.setExecLog(reportCollectionCaseTransDTO.getExecLog());
            caseTrans.setContent(reportCollectionCaseTransDTO.getContent());
            caseTrans.setDescription(reportCollectionCaseTransDTO.getDescription());
            caseTrans.setDuring(reportCollectionCaseTransDTO.getDuring());
            caseTrans.setTransName(reportCollectionCaseTransDTO.getTransName());
            caseTrans.setScreenshotList(reportCollectionCaseTransDTO.getScreenshotList());
            caseTrans.setTransId(reportCollectionCaseTransDTO.getTransId());
            reportCollectionCaseTrans.add(caseTrans);
        });
        return reportCollectionCaseTrans;
    }

}
