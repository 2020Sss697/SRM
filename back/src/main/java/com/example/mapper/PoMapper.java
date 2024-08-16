package com.example.mapper;

import com.example.pojo.Po;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PoMapper {
    @Insert("insert into po(supplier_name,buyer_id,material_name,material_num,price,create_time,status) " +
            "values(#{supplierName},#{buyerId},#{materialName},#{materialNum},#{price},#{createTime},#{status})")
    void add(Po po);

    @Select("select * from po")
    List<Po> list();

    @Update("update po set tracking_num = #{trackingNum} where id = #{id}")
    void addTrackingNum(Po po);

    @Update("update po set status = #{status} where id = #{id}")
    void approve(int id, int status);

    @Update("update po set status = #{status} where id = #{id}")
    void reject(int id, int status);

    @Update("update po set status = #{status} where id = #{id}")
    void deliver(int id, int status);

    @Update("update po set status = #{status} where id = #{id}")
    void receive(int id, int status);

    @Select("select * from po where supplier_name = #{supplierName}")
    List<Po> myPo(String supplierName);
}
