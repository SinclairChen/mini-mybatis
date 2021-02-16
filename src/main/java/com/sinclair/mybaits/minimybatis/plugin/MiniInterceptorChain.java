package com.sinclair.mybaits.minimybatis.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 插件的执行链
 * @Date 2021/2/16 15:56
 * @Author by Saiyong.Chen
 */
public class MiniInterceptorChain {

    private final List<MiniInterceptor> interceptors = new ArrayList<>();

    public void addInterceptor(MiniInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * 对目标对象进行代理
     * @param target
     * @return
     */
    public Object pluginAll(Object target) {
        for (MiniInterceptor interceptor : interceptors) {

            target = interceptor.plugin(target);
        }
        return target;
    }

    public boolean hasPlugin() {
        if (interceptors.size() == 0) {
            return false;
        }
        return true;
    }
}
