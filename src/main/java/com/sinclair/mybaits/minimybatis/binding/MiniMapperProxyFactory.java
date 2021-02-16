package com.sinclair.mybaits.minimybatis.binding;

import com.sinclair.mybaits.minimybatis.session.MiniDefaultSqlSession;

/**
 * @Description TODO
 * @Date 2021/2/16 10:56
 * @Author by Saiyong.Chen
 */
public class MiniMapperProxyFactory {
    public <T> MiniMapperProxyFactory(Class<T> clazz, Class pojo) {

    }

    public <T> T newInstance(MiniDefaultSqlSession sqlSession) {

        return null;
    }
}
