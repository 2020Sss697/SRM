package com.example.service;

import com.example.pojo.Po;

import java.util.List;

public interface PoService {
    void add(Po po);

    List<Po> list();

    void approve(Integer id, int status);

    void reject(Integer id, int status);

    void deliver(Integer id, int status);

    void receive(Integer id, int status);

    List<Po> myPo();

    void addTrackingNum(Po po);
}
