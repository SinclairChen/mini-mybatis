package com.sinclair.mybaits.minimybatis.session;

import com.sinclair.mybaits.minimybatis.executor.MiniExecutor;

/**
 * @Description MyBatis的api
 * @Date 2021/2/16 10:59
 * @Author by Saiyong.Chen
 */
public class MiniDefaultSqlSession {

    private MiniConfiguration configuration;

    private MiniExecutor executor;


    public MiniDefaultSqlSession(MiniConfiguration configuration, MiniExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public MiniConfiguration getConfiguration() {
        return configuration;
    }


    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }


    public <T> T selectOne(String statement, Object[] parameters, Class pojo) {
        String sql = getConfiguration().getMapperStatement(statement);
        return executor.query(sql, parameters, pojo);
    }
}
