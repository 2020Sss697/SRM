package com.example.service.impl;

import com.example.mapper.PoMapper;
import com.example.pojo.Po;
import com.example.service.PoService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PoServiceImpl implements PoService {

    @Autowired
    private PoMapper poMapper;

    @Override
    public void add(Po po) {
        po.setCreateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer buyerId = (Integer) map.get("id");
        po.setBuyerId(buyerId);
        poMapper.add(po);
    }

    @Override
    public void addTrackingNum(Po po) {
        po.setStatus(3);
        poMapper.addTrackingNum(po);
    }

    @Override
    public List<Po> list() {
        return poMapper.list();
    }

    @Override
    public void approve(Integer id, int status) {
        poMapper.approve(id, status);
    }

    @Override
    public void reject(Integer id, int status) {
        poMapper.reject(id, status);
    }

    @Override
    public void deliver(Integer id, int status) {
        poMapper.deliver(id, status);
    }

    @Override
    public void receive(Integer id, int status) {
        poMapper.receive(id, status);
    }

    @Override
    public List<Po> myPo() {
        Map<String,Object> map = ThreadLocalUtil.get();
        String supplierName = (String) map.get("name");
        return poMapper.myPo(supplierName);
    }
}
