package com.example.mapper;

import com.example.pojo.Inquiry;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InquiryMapper {
    @Insert("insert into inquiry(buyer_id,material_name,material_num,end_time) " +
            "values(#{buyerId},#{materialName},#{materialNum},#{endTime})")
    void add(Inquiry inquiry);

    @Select("select material_name,material_num,start_time from inquiry where buyer_id = #{buyerId}")
    List<Inquiry> list(Integer buyerId);

    @Select("select * from inquiry")
    List<Inquiry> listAll();

    @Delete("delete from inquiry where id=#{id}")
    void deleteInquiry(int id);
}
