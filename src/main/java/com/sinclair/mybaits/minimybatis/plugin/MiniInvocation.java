package com.sinclair.mybaits.minimybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description TODO
 * @Date 2021/2/16 16:22
 * @Author by Saiyong.Chen
 */
public class MiniInvocation {

    private Object target;
    private Method method;
    private Object[] args;

    public MiniInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object proceed () throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }
}
