package com.example.appoperation.db.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.appoperation.db.base.AbstractDAO;
import com.example.appoperation.db.dao.OperationResourceDAO;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.query.OperationResourceQuery;

@Repository("operationResourceDAO")
public class OperationResourceDAOImpl extends AbstractDAO<OperationResourcePO, OperationResourceQuery, Integer> implements OperationResourceDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "operationResourceDAO";
    }
}
