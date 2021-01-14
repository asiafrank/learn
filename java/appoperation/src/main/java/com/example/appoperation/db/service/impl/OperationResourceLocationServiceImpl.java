package com.example.appoperation.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appoperation.db.base.AbstractBO;
import com.example.appoperation.db.base.DAO;
import com.example.appoperation.db.service.OperationResourceLocationService;
import com.example.appoperation.db.dao.OperationResourceLocationDAO;
import com.example.appoperation.db.po.OperationResourceLocationPO;
import com.example.appoperation.db.query.OperationResourceLocationQuery;

@Service("operationResourceLocationService")
public class OperationResourceLocationServiceImpl extends AbstractBO<OperationResourceLocationPO, OperationResourceLocationQuery, Integer> implements OperationResourceLocationService {
    @Autowired
    private OperationResourceLocationDAO operationResourceLocationDAO;

    @Override
    protected DAO<OperationResourceLocationPO, OperationResourceLocationQuery, Integer> getDAO() {
        return operationResourceLocationDAO;
    }
}
