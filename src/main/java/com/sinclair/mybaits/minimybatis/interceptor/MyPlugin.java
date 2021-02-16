package com.sinclair.mybaits.minimybatis.interceptor;

import com.sinclair.mybaits.minimybatis.annotation.Intercepts;
import com.sinclair.mybaits.minimybatis.plugin.MiniInterceptor;
import com.sinclair.mybaits.minimybatis.plugin.MiniInvocation;
import com.sinclair.mybaits.minimybatis.plugin.MiniPlugin;

/**
 * @Description 自定义插件
 * @Date 2021/2/16 16:19
 * @Author by Saiyong.Chen
 */
@Intercepts("query")
public class MyPlugin implements MiniInterceptor {


    @Override
    public Object intercept(MiniInvocation invocation) throws Throwable {
        String statement = (String) invocation.getArgs()[0];
        Object[] parameter = (Object[]) invocation.getArgs()[1];
        Class pojo = (Class) invocation.getArgs()[3];
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return MiniPlugin.wrap(target, this);
    }
}
