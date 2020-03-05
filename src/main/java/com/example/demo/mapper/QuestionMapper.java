package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into post(title,content,gmt_create,gmt_modified,creator,tag) values(#{title},#{content},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insert(Question question);
    @Select("select * from post")
    List<Question> list();
    @Select("select * from post limit #{offset},#{size}")
    List<Question> listPage(@Param(value = "offset") Integer offset,@Param(value = "size")  Integer size);
    @Select("select * from post where creator = #{id} limit #{offset},#{size}")
    List<Question> listUser(@Param(value = "id") Integer id,@Param(value = "offset") Integer offset,@Param(value = "size")  Integer size);

    @Select("select count(id) from post")
    Integer count();
    @Select("select count(id) from post where creator = #{id} ")
    Integer countUser(@Param(value = "id")  Integer id);
}
