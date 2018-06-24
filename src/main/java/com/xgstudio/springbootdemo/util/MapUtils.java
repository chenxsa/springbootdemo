package com.xgstudio.springbootdemo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxsa
 * @date: 2018-6-7 19:33
 * @Description:
 */
public class MapUtils {
    /**
     *
     * @param key
     * @param value
     * @return
     */
    public static Map<String,Object> create(String key,Object value){
        Map<String,Object> result=new HashMap<>(1);
        result.put(key,value);
        return  result;
    }
    /**
     *
     * @param keys
     * @param values
     * @return
     */
    public static Map<String,Object> create(String[] keys,Object[] values){
        if ( keys.length != values.length){
            throw  new IllegalArgumentException("keys.length != values.length");
        }
        Map<String,Object> result=new HashMap<>(1);
        for (int i=0;i< keys.length;i++){
            result.put(keys[i],values[i]);
        }
        return  result;
    }
}
