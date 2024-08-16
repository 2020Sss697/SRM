package com.example.service.impl;

import com.example.pojo.Inquiry;
import com.example.service.InquiryService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.InquiryMapper;

import java.util.List;
import java.util.Map;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryMapper inquiryMapper;

    @Override
    public void add(Inquiry inquiry) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer buyerId = (Integer) map.get("id");
        inquiry.setBuyerId(buyerId);
        inquiryMapper.add(inquiry);
    }

    @Override
    public List<Inquiry> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        int buyerId = (Integer) map.get("id");
        return inquiryMapper.list(buyerId);
    }

    @Override
    public List<Inquiry> listAll() {
        Map<String,Object> map = ThreadLocalUtil.get();
        return inquiryMapper.listAll();
    }

    @Override
    public void deleteInquiry(Integer id) {
        inquiryMapper.deleteInquiry(id);
    }
}
