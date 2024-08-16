package com.example.mapper;

import com.example.pojo.Supplier;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SupplierMapper {

    @Select("select * from supplier where username=#{username}")
    Supplier findBySupplierName(String username);

    @Insert("insert into supplier(username,password,register_time)" +
            " values(#{username},#{password},now())")
    void addSupplier(String username, String password);

    @Update("update supplier set name=#{name},contacts=#{contacts},phone=#{phone},email=#{email},address=#{address} where id=#{id}")
    void updateSupplier(Supplier supplier);

    @Update("update supplier set password=#{md5String} where id=#{id}")
    void updateSupplierPwd(String md5String, int id);

    @Select("select * from supplier")
    List<Supplier> list();

    @Update("update supplier set score=#{score} where id=#{id}")
    void addScore(Supplier supplier);

    @Update("update supplier set status = #{status} where id = #{id}")
    void access(int id, int status);

    @Select("select * from supplier where status = 0")
    List<Supplier> listWait();

    @Select("select * from supplier where id=#{id}")
    Supplier getSupplierStatus(Integer id);
}
