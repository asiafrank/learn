package com.example.appoperation.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appoperation.db.base.AbstractBO;
import com.example.appoperation.db.base.DAO;
import com.example.appoperation.db.service.OperationResourceConditionRelationService;
import com.example.appoperation.db.dao.OperationResourceConditionRelationDAO;
import com.example.appoperation.db.po.OperationResourceConditionRelationPO;
import com.example.appoperation.db.query.OperationResourceConditionRelationQuery;

@Service("operationResourceConditionRelationService")
public class OperationResourceConditionRelationServiceImpl extends AbstractBO<OperationResourceConditionRelationPO, OperationResourceConditionRelationQuery, Integer> implements OperationResourceConditionRelationService {
    @Autowired
    private OperationResourceConditionRelationDAO operationResourceConditionRelationDAO;

    @Override
    protected DAO<OperationResourceConditionRelationPO, OperationResourceConditionRelationQuery, Integer> getDAO() {
        return operationResourceConditionRelationDAO;
    }
}
