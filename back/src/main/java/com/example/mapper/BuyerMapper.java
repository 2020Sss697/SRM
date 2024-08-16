package com.example.mapper;

import com.example.pojo.Buyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BuyerMapper {

    @Select("select * from buyer where username=#{username}")
    Buyer findByBuyerName(String username);

    @Update("update buyer set name=#{name},phone=#{phone} where id=#{id}")
    void updateBuyer(Buyer buyer);

}
