package com.example.appoperation.db.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.appoperation.db.base.AbstractDAO;
import com.example.appoperation.db.dao.OperationResourceLocationDAO;
import com.example.appoperation.db.po.OperationResourceLocationPO;
import com.example.appoperation.db.query.OperationResourceLocationQuery;

@Repository("operationResourceLocationDAO")
public class OperationResourceLocationDAOImpl extends AbstractDAO<OperationResourceLocationPO, OperationResourceLocationQuery, Integer> implements OperationResourceLocationDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "operationResourceLocationDAO";
    }
}
