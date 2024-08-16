package com.example.service;

import com.example.pojo.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier findBySupplierName(String username);

    void supplierRegister(String username, String password);


    void updateSupplier(Supplier supplier);


    void updateSupplierPwd(String newPwd);

    List<Supplier> list();

    void addScore(Supplier supplier);

    void access(Integer id, int status);

    List<Supplier> listWait();

    Supplier getSupplierStatus(int status);
}
