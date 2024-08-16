package com.example.service.impl;

import com.example.mapper.ManagerMapper;
import com.example.pojo.Manager;
import com.example.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper ;

    @Override
    public Manager findByManagerName(String username) {
        Manager m = managerMapper.findByManagerName(username);
        return m;
    }


}
