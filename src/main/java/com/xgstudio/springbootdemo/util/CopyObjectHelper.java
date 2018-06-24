package com.xgstudio.springbootdemo.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;

/**
 * 复制对象
 */
public class CopyObjectHelper {

    /**
     * 将origin属性注入到destination中
     * @param origin
     * @param destination
     * @throws Exception
     */
    public static void mergeObject(Object origin, Object destination) throws Exception {

        if (origin == null || destination == null) {
            return;
        }

        if (!origin.getClass().equals(destination.getClass())) {
            return;
        }

        Field[] fields = origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            JsonIgnore expose = fields[i].getAnnotation(JsonIgnore.class);
            if (expose == null) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(origin);
                if (null != value) {
                    fields[i].set(destination, value);
                }
                fields[i].setAccessible(false);
            }
        }
    }
}
