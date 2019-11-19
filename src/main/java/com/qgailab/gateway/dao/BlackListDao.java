package com.qgailab.gateway.dao;

import com.qgailab.gateway.model.BlackList;
import com.qgailab.gateway.model.Paging;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackListDao {

    @Select("select * from black_list limit #{startIndex},#{numberPerPage}")
    List<BlackList> selectByLimit(Paging paging);

    @Select("select count(*) from black_list")
    Integer countBlackList();

    @Insert("insert into black_list(ip, mac, expire) values(#{ip}, #{mac}, #{expireTime})")
    Integer addBlackList(BlackList blackList);

    @Delete("delete from black_list where id = #{id}")
    Integer deleteBlackList(BlackList blackList);
}
