package com.xgstudio.springbootdemo.util;

import org.springframework.util.StringUtils;

/**
 *
 * @author chenxsa
 */
public class StringUtil {
    /**
     * 首字母大写
     * @param str 目标
     * @return
     */
    public static String upperCase(String str) {
        if (StringUtils.isEmpty(str)){
            return  str;
        }
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 判断2个string想等
     * @param str1
     * @param str2
     * @return 同时为空或者想等返回true
     */
    public static boolean equals(String str1,String str2) {
        if (StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)){
            return  true;
        }
        if (StringUtils.isEmpty(str1) && !StringUtils.isEmpty(str2)){
            return false;
        }
        if (!StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)){
            return false;
        }
        return  str1.equals(str2);
    }
}
