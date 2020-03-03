package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into post(title,content,gmt_create,gmt_modified,creator,tags,avatar_url) values(#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{avatorUrl})")
    void insert(Question question);
}
