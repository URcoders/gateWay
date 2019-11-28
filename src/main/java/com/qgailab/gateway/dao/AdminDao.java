package com.qgailab.gateway.dao;

import com.qgailab.gateway.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface AdminDao {

    @Select("select * from administrator where username = #{username} and psw = #{password}")
    User login(User user);

}
