package com.autotest.LiuMa.controller;

import com.autotest.LiuMa.common.utils.FileUtils;

import com.autotest.LiuMa.service.ThymeleafService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

@RestController
@RequestMapping("/autotest/reports")
public class ThymeleafController {

    @Resource
    private ThymeleafService thymeleafService;
    @GetMapping("/download/{reportId}")
    public void Exporter(@PathVariable String reportId,HttpServletResponse response) throws IOException {
        FileUtils.downloadFile(thymeleafService.CreatHtml(reportId),response);
    }

}
