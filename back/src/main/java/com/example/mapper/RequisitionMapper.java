package com.example.mapper;

import com.example.pojo.Requisition;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequisitionMapper {
    @Insert("insert into requisition(status,buyer_id,buyer_name,material_name,material_num,create_time) " +
            "values(#{status},#{buyerId},#{buyerName},#{materialName},#{materialNum},#{createTime})")
    void add(final Requisition requisition);

    @Select("select * from requisition where buyer_id = #{buyerId}")
    List<Requisition> list(int buyerId);

    @Delete("delete from requisition where id=#{id}")
    void deleteById(int id);

    @Select("select * from requisition")
    List<Requisition> listAll();

    @Update("update requisition set material_name=#{materialName},material_num=#{materialNum} where id=#{id}")
    void update(final Requisition requisition);

    @Update("update requisition set status = #{status} where id = #{id}")
    void approve(int id, int status);

    @Update("update requisition set status = #{status} where id = #{id}")
    void reject(int id, int status);
}
