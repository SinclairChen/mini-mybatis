package com.sinclair.mybaits.minimybatis.executor;

/**
 * @Description TODO
 * @Date 2021/2/16 15:48
 * @Author by Saiyong.Chen
 */
public class MiniSimpleExecutor implements MiniExecutor{

    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {

        MiniStatementHandler statementHandler = new MiniStatementHandler();
        return statementHandler.query(statement, parameter, pojo);
    }
}
