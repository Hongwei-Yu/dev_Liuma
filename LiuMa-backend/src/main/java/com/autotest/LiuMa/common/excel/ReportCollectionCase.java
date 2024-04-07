package com.autotest.LiuMa.common.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCollectionCase {

    @ExcelIgnore
    private String caseId; // 用例ID
    @ExcelProperty("用例类型")
    private String caseType; //用例类型
    @ExcelProperty("用例名称")
    private String caseName;  // 用例名称
    @ExcelProperty("用例描述")
    private String caseDesc; // 用例描述
    @ExcelProperty("执行次数")
    private Integer runTimes; // 执行时间
    @ExcelProperty("执行时长")
    private String during; // 执行时长
    @ExcelProperty("状态")
    private String status; // 状态

}
