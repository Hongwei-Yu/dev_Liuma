package com.autotest.LiuMa.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.autotest.LiuMa.dto.ReportCollectionCaseDTO;

import java.util.List;

public class ReportCollection {
    @ExcelProperty("通过用例数")
    private Integer passCount;
    @ExcelProperty("失败用例数")
    private Integer failCount;
    @ExcelProperty("错误用例数")
    private Integer errorCount;

}
