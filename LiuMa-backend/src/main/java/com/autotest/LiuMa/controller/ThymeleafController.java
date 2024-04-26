package com.autotest.LiuMa.controller;

import com.autotest.LiuMa.dto.ReportDTO;
import com.autotest.LiuMa.service.ReportService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;

//@RestController
////@RequestMapping("/hello")
@Controller
public class ThymeleafController {
    TemplateEngine templateEngine = new TemplateEngine();
    @Resource
    private ReportService reportService;
    @GetMapping("/report")
    public String Exporter(Model model) throws IOException {
//
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("/templates/");
//        //模板文件后缀
//        resolver.setSuffix(".html");
//        templateEngine.setTemplateResolver(resolver);
//
////
        ReportDTO report = reportService.getPlanResult("3111be1b-febf-4cfc-a52e-b6360f56d774");
        Context context =new Context();
        context.setVariable("name",report.getName());
        context.setVariable("startTime",report.getStartTime());
        context.setVariable("total",report.getTotal());
        context.setVariable("failCount",report.getFailCount());
        context.setVariable("errorCount",report.getErrorCount());
        context.setVariable("run_time",report.getEndTime()- report.getStartTime());
        context.setVariable("updateUser",report.getUpdateUser());
        context.setVariable("passCount",report.getPassCount());
        context.setVariable("passRate",report.getPassRate());
        context.setVariable("collectionList",report.getCollectionList());

        FileWriter writer = new FileWriter("result.html");
        templateEngine.process("report",context,writer);
        model.addAttribute("name",report.getName());
        model.addAttribute("startTime",report.getStartTime());
        model.addAttribute("total",report.getTotal());
        model.addAttribute("failCount",report.getFailCount());
        model.addAttribute("errorCount",report.getErrorCount());
        model.addAttribute("run_time",report.getEndTime()- report.getStartTime());
        model.addAttribute("updateUser",report.getUpdateUser());
        model.addAttribute("passCount",report.getPassCount());
        model.addAttribute("passRate",report.getPassRate());
        model.addAttribute("collectionList",report.getCollectionList());
        return "report";

    }

}
