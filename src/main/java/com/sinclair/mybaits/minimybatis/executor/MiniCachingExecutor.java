package com.sinclair.mybaits.minimybatis.executor;

import com.sinclair.mybaits.minimybatis.cache.MiniCacheKey;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 缓存执行器
 * @Date 2021/2/16 16:05
 * @Author by Saiyong.Chen
 */
public class MiniCachingExecutor implements MiniExecutor {

    private MiniExecutor delegate;
    private static final Map<Integer, Object> cache = new HashMap<>();

    public MiniCachingExecutor(MiniExecutor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {

        MiniCacheKey cacheKey = new MiniCacheKey();
        cacheKey.update(statement);
        cacheKey.update(joinStr(parameter));

        if (cache.containsKey(cacheKey.getCode())) {
            return (T) cache.get(cacheKey.getCode());
        } else {
            Object result = delegate.query(statement, parameter, pojo);
            cache.put(cacheKey.getCode(), result);
            return (T) result;
        }
    }

    /**
     * 将数组转换成字符串
     * @param parameter
     * @return
     */
    private Object joinStr(Object[] parameter) {

        StringBuffer sb = new StringBuffer();
        if(parameter !=null && parameter.length>0){
            for (Object objStr : parameter) {
                sb.append(objStr.toString() + ",");
            }
        }
        int len = sb.length();
        if(len >0){
            sb.deleteCharAt(len-1);
        }
        return sb.toString();
    }
}
