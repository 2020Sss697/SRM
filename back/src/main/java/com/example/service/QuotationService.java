package com.example.service;

import com.example.pojo.Quotation;

import java.util.List;

public interface QuotationService {
    void add(Quotation quotation);

    List<Quotation> list();

    List<Quotation> listAll();

    void deleteQuotation(int id);

    void updateQuotation(final Quotation quotation);

    List<Quotation> listI(Integer inquiryId);

    List<Quotation> listIi(Integer inquiryId);

    void win(Integer id, int status);
}
