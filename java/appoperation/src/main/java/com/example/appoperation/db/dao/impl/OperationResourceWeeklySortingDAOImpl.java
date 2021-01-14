package com.example.appoperation.db.dao.impl;

import com.example.appoperation.db.po.OperationResourcePO;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.appoperation.db.base.AbstractDAO;
import com.example.appoperation.db.dao.OperationResourceWeeklySortingDAO;
import com.example.appoperation.db.po.OperationResourceWeeklySortingPO;
import com.example.appoperation.db.query.OperationResourceWeeklySortingQuery;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("operationResourceWeeklySortingDAO")
public class OperationResourceWeeklySortingDAOImpl extends AbstractDAO<OperationResourceWeeklySortingPO, OperationResourceWeeklySortingQuery, Integer> implements OperationResourceWeeklySortingDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "operationResourceWeeklySortingDAO";
    }

    @Override
    public List<OperationResourcePO> findResource(Integer locationId, Long todayBeginLong) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("locationId", locationId);
        params.put("weekBegin", todayBeginLong);

        return getSqlSessionTemplate().selectList(getNamespace() + ".findResource", params);
    }
}
