package com.example.service.impl;

import com.example.mapper.PerformanceMapper;
import com.example.pojo.Performance;
import com.example.pojo.Quotation;
import com.example.service.PerformanceService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private PerformanceMapper performanceMapper;

    @Override
    public void add(Performance performance) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer buyerId = (Integer) map.get("id");
        performance.setBuyerId(buyerId);
        performanceMapper.add(performance);
    }

    @Override
    public List<Performance> list() {
        return performanceMapper.list();
    }

    @Override
    public void updatePerformance(Performance performance) {
        int q = performance.getQuality();
        int s = performance.getService();
        int d = performance.getDeliver();
        performance.setScore(q*0.5+s*0.25+d*0.25);
        performance.setStatus(1);
        performanceMapper.updatePerformance(performance);
    }

    @Override
    public List<Performance> listAll() {
        return performanceMapper.listAll();
    }

    @Override
    public List<Performance> listMy() {
        Map<String,Object> map = ThreadLocalUtil.get();
        int supplierId = (int) map.get("id");
        return performanceMapper.listMy(supplierId);
    }
}
