package com.example.mapper;

import com.example.pojo.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ManagerMapper {

    @Select("select * from manager where username=#{username}")
    Manager findByManagerName(String username);

    @Update("update manager set name=#{name} where id=#{id}")
    void updateManager(Manager manager);
}
