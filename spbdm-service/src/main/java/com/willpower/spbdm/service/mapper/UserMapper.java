package com.willpower.spbdm.service.mapper;

import com.willpower.spbdm.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Powersoft on 2019/4/29.
 */
@Mapper
public interface UserMapper {


    @Select("select * from user")
    List<User> findUserAll();

    @Select("select * from user where name = #{name}")
    User findUserByname(@Param("name") String name);
}

