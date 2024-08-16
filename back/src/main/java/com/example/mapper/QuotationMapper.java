package com.example.mapper;

import com.example.pojo.Quotation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuotationMapper {
    @Insert("insert into quotation(supplier_id,supplier_name,inquiry_id,material_name,material_num,price,deliver_time) " +
            "values(#{supplierId},#{supplierName},#{inquiryId},#{materialName},#{materialNum},#{price},#{deliverTime})")
    void add(Quotation quotation);

    @Select("select * from quotation where supplier_id = #{supplierId}")
    List<Quotation> list(int supplierId);

    @Update("update quotation set inquiry_id=#{inquiryId},material_name=#{materialName},price=#{price} where id=#{id}")
    void updateQuotation(final Quotation quotation);

    @Select("select * from quotation")
    List<Quotation> listAll();

    @Delete("delete from quotation where id=#{id}")
    void deleteQuotation(int id);

    @Select("select * from quotation where inquiry_id = #{inquiryId}")
    List<Quotation> listI(Integer inquiryId);

    @Select("select * from quotation where supplier_id = #{supplierId} and inquiry_id = #{inquiryId}")
    List<Quotation> listIi(int supplierId,Integer inquiryId);

    @Update("update quotation set status = #{status} where id = #{id}")
    void win(int id, int status);
}
