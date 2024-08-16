package com.example.service;

import com.example.pojo.Performance;
import com.example.pojo.Quotation;

import java.util.List;

public interface PerformanceService {
    void add(Performance performance);

    List<Performance> list();

    void updatePerformance(Performance performance);

    List<Performance> listAll();

    List<Performance> listMy();
}
