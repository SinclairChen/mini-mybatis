package com.sinclair.mybaits.minimybatis.binding;

import com.sinclair.mybaits.minimybatis.session.MiniDefaultSqlSession;
import sun.java2d.opengl.OGLContext;

import java.lang.reflect.Proxy;

/**
 * @Description MapperProxy代理工厂类
 * @Date 2021/2/16 10:56
 * @Author by Saiyong.Chen
 */
public class MiniMapperProxyFactory {

    private Class mapperInterface;
    private Class object;

    public <T> MiniMapperProxyFactory(Class<T> mapperInterface, Class pojo) {
        this.mapperInterface = mapperInterface;
        this.object = pojo;

    }

    public <T> T newInstance(MiniDefaultSqlSession sqlSession) {

        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{ mapperInterface}, new MiniMapperProxy(sqlSession, object));
    }
}
