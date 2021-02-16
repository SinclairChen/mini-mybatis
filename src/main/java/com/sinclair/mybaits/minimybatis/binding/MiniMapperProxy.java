package com.sinclair.mybaits.minimybatis.binding;

import com.sinclair.mybaits.minimybatis.session.MiniDefaultSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description  MapperProxy代理类，用于代理Mapper接口
 * @Date 2021/2/16 17:52
 * @Author by Saiyong.Chen
 */
public class MiniMapperProxy implements InvocationHandler {

    private MiniDefaultSqlSession sqlSession;
    private Class object;

    public MiniMapperProxy(MiniDefaultSqlSession sqlSession, Class object) {
        this.sqlSession = sqlSession;
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;
        if (sqlSession.getConfiguration().hashStatement(statementId)) {
            return sqlSession.selectOne(statementId, args, object);
        }
        return method.invoke(proxy, args);
    }
}
