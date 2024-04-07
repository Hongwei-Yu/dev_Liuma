package com.autotest.LiuMa.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;

//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//import java.util.List;
@Getter
@Setter
public class ReportCollection {
    @ExcelProperty("通过用例数")
    private Integer passCount;
    @ExcelProperty("失败用例数")
    private Integer failCount;
    @ExcelProperty("错误用例数")
    private Integer errorCount;

    @ExcelProperty("测试集合名")
    private String collectionName;

    @ExcelProperty("用例总数数")
    private Integer caseTotal;

}
