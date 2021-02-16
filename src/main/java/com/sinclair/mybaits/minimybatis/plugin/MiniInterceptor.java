package com.sinclair.mybaits.minimybatis.plugin;

/**
 * @Description TODO
 * @Date 2021/2/16 16:20
 * @Author by Saiyong.Chen
 */
public interface MiniInterceptor {

    /**
     * 插件的核心逻辑
     * @param invocation
     * @return
     * @throws Throwable
     */
    Object intercept(MiniInvocation invocation) throws Throwable;

    /**
     * 对被拦截对象进行代理
     * @param target
     * @return
     */
    Object plugin(Object target);
}
