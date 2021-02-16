package com.sinclair.mybaits.minimybatis.binding;



import com.sinclair.mybaits.minimybatis.binding.MiniMapperProxyFactory;
import com.sinclair.mybaits.minimybatis.session.MiniDefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 维护接口和工厂类的关系，用于获取MapperProxy代理对象
 * @Date 2021/2/16 10:52
 * @Author by Saiyong.Chen
 */
public class MimiMapperRegistry {

    private final Map<Class<?>, MiniMapperProxyFactory> knownMappers = new HashMap<>();


    public <T> void addMapper(Class<T> clazz, Class pojo) {
        knownMappers.put(clazz, new MiniMapperProxyFactory(clazz, pojo));
    }


    public <T> T getMapper(Class<T> clazz, MiniDefaultSqlSession sqlSession) {
        MiniMapperProxyFactory proxyFactory = knownMappers.get(clazz);

        if (proxyFactory == null) {
            throw new RuntimeException("Type: " + "can not find");
        }
        return (T) proxyFactory.newInstance(sqlSession);

    }
}
