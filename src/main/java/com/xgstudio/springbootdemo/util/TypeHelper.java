package com.xgstudio.springbootdemo.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 类型帮助类
 * @author: chenxsa
 * @date: 2018-5-10 16:30
 * @Description:
 */
public final class TypeHelper {
    /**
     *
     * 获取泛型实体的真实类型
     *
     * @param: [instanceClass]
     * @return: java.lang.reflect.Type[]
     * @author: chenxsa
     * @date: 2018-5-10 16:34
     */
    public  static Type[] getGenericType(Class instanceClass){
        ParameterizedType superclass  = (ParameterizedType) instanceClass.getGenericSuperclass();
        if (superclass != null) {
            return superclass.getActualTypeArguments();
        }
        else {
            List<Type> result=new ArrayList<Type>();
            Type[] types= instanceClass.getGenericInterfaces();
            if ( types!=null && types.length>0) {
                for (Type type :types) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (parameterizedType!=null){
                        for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                            result.add(actualTypeArgument);
                        }
                    }
                }
            }
            if (result.size()>0) {
                return (Type[]) result.toArray(new Type[result.size()]);
            }
        }
        return  new Type[]{};
    }
    /**
     *
     * 判断一个类型是否是另一个类型的泛型类型
     *
     * @param: [instanceClass]
     * @return: java.lang.reflect.Type[]
     * @author: chenxsa
     * @date: 2018-5-10 16:34
     */
    public  static  boolean isGenericType(Class sourceType,Class targetClass){
        Type[] types=TypeHelper.getGenericType(targetClass);
        for (Type type:types){
            if ( sourceType== type){
                return  true;
            }
        }
        return  false;
    }
}
