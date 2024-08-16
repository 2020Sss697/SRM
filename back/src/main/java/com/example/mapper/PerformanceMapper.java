package com.example.mapper;

import com.example.pojo.Performance;
import com.example.pojo.Quotation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PerformanceMapper {

    @Insert("insert into performance(supplier_id,supplier_name,status,buyer_id,time_limit,score) " +
            "values(#{supplierId},#{supplierName},#{status},#{buyerId},#{timeLimit},#{score})")
    void add(Performance performance);

    @Select("select * from performance")
    List<Performance> list();

    @Update("update performance set quality=#{quality},service=#{service},deliver=#{deliver},score=#{score},remarks=#{remarks},status=#{status} where id=#{id}")
    void updatePerformance(Performance performance);

    @Select("select * from performance")
    List<Performance> listAll();

    @Select("select * from performance where supplier_id = #{supplierId}")
    List<Performance> listMy(int supplierId);
}
