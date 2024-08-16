package com.example.service;

import com.example.pojo.Buyer;

public interface BuyerService {
    Buyer findByBuyerName(String username);

    void updateBuyer(Buyer buyer);

}

