package com.czg.export.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionDemoData {
    /**
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}