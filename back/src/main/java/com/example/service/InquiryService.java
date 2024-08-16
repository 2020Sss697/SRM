package com.example.service;

import com.example.pojo.Inquiry;

import java.util.List;

public interface InquiryService {

    void add(Inquiry inquiry);

    List<Inquiry> list();

    List<Inquiry> listAll();

    void deleteInquiry(Integer id);
}
