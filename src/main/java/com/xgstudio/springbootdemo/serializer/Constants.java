package com.xgstudio.springbootdemo.serializer;

import java.time.format.DateTimeFormatter;

/**
 * @author chenxsa
 * @date: 2018-5-16 15:33
 * @Description:
 */
public interface Constants {
    public static final DateTimeFormatter DATE_FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER =  DateTimeFormatter.ofPattern("HH:mm:ss");

}
