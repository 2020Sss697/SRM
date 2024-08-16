package com.example.service.impl;

import com.example.mapper.RequisitionMapper;
import com.example.pojo.Requisition;
import com.example.service.RequisitionService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RequisitionServiceImpl implements RequisitionService {
    @Autowired
    private RequisitionMapper requisitionMapper;

    @Override
    public void add(final Requisition requisition) {
        requisition.setCreateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer buyerId = (Integer) map.get("id");
        requisition.setBuyerId(buyerId);
        String buyerName = (String) map.get("name");
        requisition.setBuyerName(buyerName);
        requisitionMapper.add(requisition);
    }

    @Override
    public List<Requisition> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        int buyerId = (int) map.get("id");
        return requisitionMapper.list(buyerId);
    }

    @Override
    public void deleteById(int id) {
        requisitionMapper.deleteById(id);
    }
    @Override
    public void update(final Requisition requisition) {
        requisitionMapper.update(requisition);
    }
    @Override
    public void approve(int id, int status) {
        requisitionMapper.approve(id, status);
    }

    @Override
    public List<Requisition> listAll() {
        return requisitionMapper.listAll();
    }

    @Override
    public void reject(Integer id, int status) {
        requisitionMapper.reject(id, status);
    }
}
