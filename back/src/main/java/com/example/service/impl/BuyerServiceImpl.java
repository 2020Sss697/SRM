package com.example.service.impl;

import com.example.mapper.BuyerMapper;
import com.example.pojo.Buyer;
import com.example.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private BuyerMapper buyerMapper ;

    @Override
    public Buyer findByBuyerName(String username) {
        Buyer b = buyerMapper.findByBuyerName(username);
        return b;
    }

    @Override
    public void updateBuyer(Buyer buyer) {
        buyerMapper.updateBuyer(buyer);
    }

}
