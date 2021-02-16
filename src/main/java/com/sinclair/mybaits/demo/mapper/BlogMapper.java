package com.sinclair.mybaits.demo.mapper;


import com.sinclair.mybaits.minimybatis.annotation.Entity;
import com.sinclair.mybaits.minimybatis.annotation.Select;

@Entity(Blog.class)
public interface BlogMapper {
    /**
     * 根据主键查询文章
     * @param bid
     * @return
     */
    @Select("select * from blog where bid = ?")
    public Blog selectBlogById(Integer bid);

}
