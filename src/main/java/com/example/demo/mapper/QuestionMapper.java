package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into post(title,content,gmt_create,gmt_modified,creator,tag) values(#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insert(Question question);
    @Select("select * from post")
    List<Question> list();
}
