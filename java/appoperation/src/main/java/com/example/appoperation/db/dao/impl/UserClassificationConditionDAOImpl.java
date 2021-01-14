package com.example.appoperation.db.dao.impl;

import com.example.appoperation.db.base.AbstractDAO;
import com.example.appoperation.db.dao.UserClassificationConditionDAO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import com.example.appoperation.db.query.UserClassificationConditionQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userClassificationConditionDAO")
public class UserClassificationConditionDAOImpl extends AbstractDAO<UserClassificationConditionPO, UserClassificationConditionQuery, Integer> implements UserClassificationConditionDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "userClassificationConditionDAO";
    }

    @Override
    public List<UserClassificationConditionPO> findUserClassificationsByResourceId(Integer resourceId) {
        return getSqlSessionTemplate().selectList(getNamespace() + ".findUserClassificationsByResourceId", resourceId);
    }
}
