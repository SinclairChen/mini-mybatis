package com.sinclair.mybaits.minimybatis.executor;

/**
 * @Description 执行器
 * @Date 2021/2/16 10:09
 * @Author by Saiyong.Chen
 */
public interface MiniExecutor {

    /**
     * 查询
     * @param statement
     * @param parameter
     * @param pojo
     * @param <T>
     * @return
     */
    <T> T query(String statement, Object[] parameter, Class pojo);
}
