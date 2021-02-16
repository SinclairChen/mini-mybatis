package com.sinclair.mybaits.minimybatis.cache;

/**
 * @Description TODO
 * @Date 2021/2/16 16:07
 * @Author by Saiyong.Chen
 */
public class MiniCacheKey {

    private static final int DEFAULT_HASHCODE = 17;
    private static final int DEFAULT_MULTIPLE = 37;

    private int hashCode;
    private int count;
    private int multipler;

    public MiniCacheKey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.count = 0;
        this.multipler = DEFAULT_MULTIPLE;
    }

    public int getCode() {
        return hashCode;
    }

    public void update(Object object) {
        int baseHashCode = object == null ? 1 : object.hashCode();
        count++;
        baseHashCode *= count;
        hashCode = multipler * hashCode + baseHashCode;
    }
}
