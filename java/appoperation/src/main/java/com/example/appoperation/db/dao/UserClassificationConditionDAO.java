package com.example.appoperation.db.dao;

import com.example.appoperation.db.base.DAO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import com.example.appoperation.db.query.UserClassificationConditionQuery;

import java.util.List;

public interface UserClassificationConditionDAO extends DAO<UserClassificationConditionPO, UserClassificationConditionQuery, Integer> {
    List<UserClassificationConditionPO> findUserClassificationsByResourceId(Integer resourceId);
}
