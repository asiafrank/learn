package com.example.appoperation.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appoperation.db.base.AbstractBO;
import com.example.appoperation.db.base.DAO;
import com.example.appoperation.db.service.OperationResourceService;
import com.example.appoperation.db.dao.OperationResourceDAO;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.query.OperationResourceQuery;

@Service("operationResourceService")
public class OperationResourceServiceImpl extends AbstractBO<OperationResourcePO, OperationResourceQuery, Integer> implements OperationResourceService {
    @Autowired
    private OperationResourceDAO operationResourceDAO;

    @Override
    protected DAO<OperationResourcePO, OperationResourceQuery, Integer> getDAO() {
        return operationResourceDAO;
    }
}
