package com.example.service.impl;

import com.example.mapper.SupplierMapper;
import com.example.pojo.Supplier;
import com.example.service.SupplierService;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Override
    public Supplier findBySupplierName(String username) {
        Supplier s = supplierMapper.findBySupplierName(username);
        return s;
    }

    @Override
    public Supplier getSupplierStatus(int status) {
        Supplier st = supplierMapper.getSupplierStatus(status);
        return st;
    }

    @Override
    public void supplierRegister(String username, String password) {

        String md5String = Md5Util.getMD5String(password);
        supplierMapper.addSupplier(username,md5String);
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        supplierMapper.updateSupplier(supplier);
    }

    @Override
    public void updateSupplierPwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        supplierMapper.updateSupplierPwd(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public List<Supplier> list() {
        return supplierMapper.list();
    }

    @Override
    public void addScore(Supplier supplier) {
        supplierMapper.addScore(supplier);
    }

    @Override
    public void access(Integer id, int status) {
        supplierMapper.access(id, status);
    }

    @Override
    public List<Supplier> listWait() {
        return supplierMapper.listWait();
    }
}
