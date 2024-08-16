package com.example.service;

import com.example.pojo.Requisition;

import java.util.List;

public interface RequisitionService {

    void add(final Requisition requisition);

    List<Requisition> list();

    void deleteById(int id);

    void approve(int id, int status);

    List<Requisition> listAll();

    void reject(Integer id, int status);

    void update(final Requisition requisition);
}
