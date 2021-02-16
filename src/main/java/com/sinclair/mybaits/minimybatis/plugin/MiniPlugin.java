package com.sinclair.mybaits.minimybatis.plugin;

import com.sinclair.mybaits.minimybatis.annotation.Intercepts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description TODO
 * @Date 2021/2/16 16:24
 * @Author by Saiyong.Chen
 */
public class MiniPlugin implements InvocationHandler {

    private Object target;
    private MiniInterceptor interceptor;

    public MiniPlugin(Object target, MiniInterceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 对被代理对象进行代理，返回
     * @param object
     * @param interceptor
     * @return
     */
    public static Object wrap(Object object, MiniInterceptor interceptor) {
        Class<?> clazz = object.getClass();

        return Proxy.newProxyInstance(
                clazz.getClassLoader(),
                clazz.getInterfaces(),
                new MiniPlugin(object, interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            if (method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return interceptor.intercept(new MiniInvocation(target, method, args));
            }
        }
        return method.invoke(target, method, args);
    }
}
