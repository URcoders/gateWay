package com.qgailab.gateway.util;


import java.util.Collection;

/**
 * @description 用于判空的工具类
 * @author guopei
 * @date 2019-11-18 21:21
 */
public class VerifyUtil {

    /**
     * @Description : 判断字符串是否为空
     */

    public static boolean isEmpty(String string){

        return  (null == string  || "".equals(string) );
    }

    /**
     * @Description : 判断对象是否为空
     * @Date : 2019-08-07
     */
    public static boolean isNull(Object object){

        return null == object;
    }

    /**
     * @Description : 判断集合是否为空
     * @Date : 2019-08-07
     */
    public static boolean isEmpty(Collection<?> collection){

        return  ( null == collection || collection.size() == 0 );
    }


    public static boolean isEmpty(Integer integer) {
        return (null == integer);
    }


}