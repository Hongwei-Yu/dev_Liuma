package com.autotest.LiuMa.common.excel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ReportCollectionCaseTrans {
    @ExcelProperty("执行结果")
    private String status;
    @ExcelProperty("执行过程id")
    private String transId;
    @ExcelProperty("接口名称")
    private String transName;
    @ExcelProperty("接口地址")
    private String content;
    @ExcelProperty("过程描述")
    private String description;
    @ExcelProperty("执行日志")
    private String execLog;
    @ExcelProperty("执行用时")
    private String during;
    @ExcelProperty("截屏")
    private String screenshotList;
    @ExcelIgnore
    private Boolean showViewer = false;

}
