package com.autotest.LiuMa.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanReport {
    @ExcelProperty("执行人")
    private String username;    // 执行人名字
    @ExcelProperty("用例总数")
    private Long total;
    @ExcelProperty("成功用例数")
    private Long passCount;
    @ExcelProperty("失败用例数")
    private Long failCount;
    @ExcelProperty("错误用例数")
    private Long errorCount;
    @ExcelProperty("通过率")
    private String passRate;
    @ExcelProperty("执行状态百分比")
    private Integer progress;

    @ExcelProperty("测试计划名称")
    private String name;

    @ExcelProperty("开始时间")
    private Long startTime;

    @ExcelProperty("结束时间")
    private Long endTime;

    @ExcelProperty("执行状态")
    private String status;

}
