package com.sinclair.mybaits.minimybatis.executor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description 结果集处理
 * @Date 2021/2/16 10:17
 * @Author by Saiyong.Chen
 */
public class MiniResultSetHandler {

    public <T> T handle(ResultSet resultSet, Class type) {

        Object pojo = null;

        try {
            pojo = type.newInstance();

            if (resultSet.next()) {
                for (Field field : pojo.getClass().getDeclaredFields()) {
                    setValue(pojo, field, resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) pojo;
    }

    /**
     * 通过反射设置值
     * @param pojo
     * @param field
     * @param resultSet
     */
    private void setValue(Object pojo, Field field, ResultSet resultSet) {
        try {
            Method method = pojo.getClass().getMethod("set" + firstWordCapital(field.getName()));
            method.invoke(pojo, getResult(resultSet, field));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String firstWordCapital(String name) {
        String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }


    /**
     * 从结果集中获取值
     * @param resultSet
     * @param field
     * @return
     */
    private Object getResult(ResultSet resultSet, Field field) throws SQLException {

        Class<?> type = field.getType();
        String dataName = HumpToUnderline(field.getName());

        // 类型判断不够全
        if (Integer.class == type ) {
            return resultSet.getInt(dataName);
        }else if (String.class == type) {
            return resultSet.getString(dataName);
        }else if(Long.class == type){
            return resultSet.getLong(dataName);
        }else if(Boolean.class == type){
            return resultSet.getBoolean(dataName);
        }else if(Double.class == type){
            return resultSet.getDouble(dataName);
        }else{
            return resultSet.getString(dataName);
        }
    }

    /**
     * 将数据库字段中的下划线替换成驼峰命名
     * @param name
     * @return
     */
    private String HumpToUnderline(String name) {
        StringBuilder builder = new StringBuilder(name);

        int temp = 0;
        if (!name.contains("_")) {
            for(int i=0; i<name.length(); i++){
                if(Character.isUpperCase(name.charAt(i))){
                    builder.insert(i+temp, "_");
                    temp += 1;
                }
            }
        }
        return builder.toString().toUpperCase();
    }
}
