package com.example.appoperation.db.service.impl;

import com.example.appoperation.db.base.AbstractBO;
import com.example.appoperation.db.base.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appoperation.db.service.UserClassificationConditionService;
import com.example.appoperation.db.dao.UserClassificationConditionDAO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import com.example.appoperation.db.query.UserClassificationConditionQuery;

import java.util.List;

@Service("userClassificationConditionService")
public class UserClassificationConditionServiceImpl extends AbstractBO<UserClassificationConditionPO, UserClassificationConditionQuery, Integer> implements UserClassificationConditionService {
    @Autowired
    private UserClassificationConditionDAO userClassificationConditionDAO;

    @Override
    protected DAO<UserClassificationConditionPO, UserClassificationConditionQuery, Integer> getDAO() {
        return userClassificationConditionDAO;
    }

    @Override
    public List<UserClassificationConditionPO> findUserClassificationsByResourceId(Integer resourceId) {
        return userClassificationConditionDAO.findUserClassificationsByResourceId(resourceId);
    }
}
