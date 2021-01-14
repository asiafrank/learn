package com.example.appoperation.db.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.appoperation.db.base.AbstractDAO;
import com.example.appoperation.db.dao.OperationResourceConditionRelationDAO;
import com.example.appoperation.db.po.OperationResourceConditionRelationPO;
import com.example.appoperation.db.query.OperationResourceConditionRelationQuery;

@Repository("operationResourceConditionRelationDAO")
public class OperationResourceConditionRelationDAOImpl extends AbstractDAO<OperationResourceConditionRelationPO, OperationResourceConditionRelationQuery, Integer> implements OperationResourceConditionRelationDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "operationResourceConditionRelationDAO";
    }
}
