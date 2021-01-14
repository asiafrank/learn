package com.example.appoperation.db.dao;

import com.example.appoperation.db.base.DAO;
import com.example.appoperation.db.po.OperationResourcePO;
import com.example.appoperation.db.po.OperationResourceWeeklySortingPO;
import com.example.appoperation.db.query.OperationResourceWeeklySortingQuery;

import java.util.List;

public interface OperationResourceWeeklySortingDAO extends DAO<OperationResourceWeeklySortingPO, OperationResourceWeeklySortingQuery, Integer> {
    List<OperationResourcePO> findResource(Integer locationId, Long todayBeginLong);
}
