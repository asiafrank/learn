package com.example.appoperation.db.service;

import com.example.appoperation.db.base.BO;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.po.OperationResourceWeeklySortingPO;
import com.example.appoperation.db.query.OperationResourceWeeklySortingQuery;

import java.util.List;

public interface OperationResourceWeeklySortingService extends BO<OperationResourceWeeklySortingPO, OperationResourceWeeklySortingQuery, Integer> {
    List<OperationResourcePO> findResource(Integer locationId, Long todayBeginLong);
}
