package com.sinclair.mybaits.minimybatis.session;

import sun.jvm.hotspot.debugger.win32.coff.COFFLineNumber;

/**
 * @Description 会话工厂
 * @Date 2021/2/16 18:21
 * @Author by Saiyong.Chen
 */
public class MiniSqlSessionFactory {

    private MiniConfiguration configuration;

    public MiniSqlSessionFactory build() {
        this.configuration = new MiniConfiguration();
        return this;
    }

    public MiniDefaultSqlSession openSession() {
        return new MiniDefaultSqlSession(configuration);
    }
}
