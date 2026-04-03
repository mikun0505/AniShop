package com.example.java.anishop.util;

import java.util.Map;



public class MapUtils {
    public static <T> T getObect(Map<String,Object> params,String key,Class<T> tClass){
        Object ob=params.getOrDefault(key, null);
        if(ob!=null){
            if(tClass.getTypeName().equals("java.lang.Long")){
                ob=ob !="" ? Long.valueOf(ob.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.Integer")){
                ob=ob!="" ? Integer.valueOf(ob.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.Double")){
                ob=ob!="" ? Double.valueOf(ob.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.Float")){
                ob=ob!="" ? Float.valueOf(ob.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.String")){
                ob=ob!="" ? ob.toString() : null;
            }
            
            else if(tClass.getTypeName().equals("java.lang.Boolean")){
                ob=ob!="" ? Boolean.valueOf(ob.toString()) : null;
            }
            return tClass.cast(ob);
        }
        return null;
    }

}
