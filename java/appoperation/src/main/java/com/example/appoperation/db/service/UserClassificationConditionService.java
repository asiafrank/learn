package com.example.appoperation.db.service;

import com.example.appoperation.db.base.BO;
import com.example.appoperation.db.po.UserClassificationConditionPO;
import com.example.appoperation.db.query.UserClassificationConditionQuery;

import java.util.List;

public interface UserClassificationConditionService extends BO<UserClassificationConditionPO, UserClassificationConditionQuery, Integer> {
    List<UserClassificationConditionPO> findUserClassificationsByResourceId(Integer resourceId);
}
