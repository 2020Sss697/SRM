package com.example.service.impl;

import com.example.mapper.QuotationMapper;
import com.example.pojo.Quotation;
import com.example.service.QuotationService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationMapper quotationMapper;

    @Override
    public void add(Quotation quotation) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer supplierId = (Integer) map.get("id");
        String supplierName = (String) map.get("name");
        quotation.setSupplierId(supplierId);
        quotation.setSupplierName(supplierName);
        quotation.setMaterialNum(quotation.getMaterialNum());
        quotationMapper.add(quotation);
    }

    @Override
    public List<Quotation> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        int supplierId = (int) map.get("id");
        return quotationMapper.list(supplierId);
    }

    @Override
    public void updateQuotation(final Quotation quotation) {
        quotationMapper.updateQuotation(quotation);
    }

    @Override
    public List<Quotation> listI(Integer inquiryId) {
        return quotationMapper.listI(inquiryId);
    }
    @Override
    public List<Quotation> listIi(Integer inquiryId) {
        Map<String,Object> map = ThreadLocalUtil.get();
        int supplierId = (int) map.get("id");
        return quotationMapper.listIi(supplierId,inquiryId);
    }

    @Override
    public void win(Integer id, int status) {
        quotationMapper.win(id,status);
    }

    @Override
    public List<Quotation> listAll() {
        return quotationMapper.listAll();
    }

    @Override
    public void deleteQuotation(int id) {
        quotationMapper.deleteQuotation(id);
    }

}
