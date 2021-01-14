package com.example.appoperation.db.service.impl;

import com.example.appoperation.db.po.OperationResourcePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appoperation.db.base.AbstractBO;
import com.example.appoperation.db.base.DAO;
import com.example.appoperation.db.service.OperationResourceWeeklySortingService;
import com.example.appoperation.db.dao.OperationResourceWeeklySortingDAO;
import com.example.appoperation.db.po.OperationResourceWeeklySortingPO;
import com.example.appoperation.db.query.OperationResourceWeeklySortingQuery;

import java.util.List;

@Service("operationResourceWeeklySortingService")
public class OperationResourceWeeklySortingServiceImpl extends AbstractBO<OperationResourceWeeklySortingPO, OperationResourceWeeklySortingQuery, Integer> implements OperationResourceWeeklySortingService {
    @Autowired
    private OperationResourceWeeklySortingDAO operationResourceWeeklySortingDAO;

    @Override
    protected DAO<OperationResourceWeeklySortingPO, OperationResourceWeeklySortingQuery, Integer> getDAO() {
        return operationResourceWeeklySortingDAO;
    }

    @Override
    public List<OperationResourcePO> findResource(Integer locationId, Long todayBeginLong) {
        return operationResourceWeeklySortingDAO.findResource(locationId, todayBeginLong);
    }
}
