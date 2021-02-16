package com.sinclair.mybaits.minimybatis.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description 参数处理器
 * @Date 2021/2/16 10:12
 * @Author by Saiyong.Chen
 */
public class MiniParameterHandler {

    private PreparedStatement preparedStatement;

    public MiniParameterHandler(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    /**
     * 从方法中获取参数，遍历替换sql中的占位符
     * @param parameters
     */
    public void setParameters(Object[] parameters) {
        try {
            // PreparedStatement的序号是从1开始的
            for (int i = 0; i <parameters.length; i++) {
                int k =i+1;
                if (parameters[i] instanceof Integer) {
                    preparedStatement.setInt(k, (Integer) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    preparedStatement.setLong(k, (Long) parameters[i]);
                } else if (parameters[i] instanceof String) {
                    preparedStatement.setString(k , String.valueOf(parameters[i]));
                } else if (parameters[i] instanceof Boolean) {
                    preparedStatement.setBoolean(k, (Boolean) parameters[i]);
                } else {
                    preparedStatement.setString(k, String.valueOf(parameters[i]));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
