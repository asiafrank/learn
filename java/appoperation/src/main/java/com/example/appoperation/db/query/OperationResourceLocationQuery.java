package com.example.appoperation.db.query;

import java.util.ArrayList;
import java.util.List;

import com.example.appoperation.db.base.Expression;
import com.example.appoperation.db.base.ExpressionChain;
import com.example.appoperation.db.po.OperationResourceLocationPO;

public class OperationResourceLocationQuery extends OperationResourceLocationPO {
    private static final long serialVersionUID = 1L;

    private List<ExpressionChain> expressionChainList;

    public OperationResourceLocationQuery() {
        expressionChainList = new ArrayList<ExpressionChain>();
    }

    public OperationResourceLocationQuery or(ExpressionChain expressionChain) {
        expressionChainList.add(expressionChain);
        return this;
    }

    public OperationResourceLocationQuery or(Expression expression) {
        expressionChainList.add(new ExpressionChain().and(expression));
        return this;
    }

    public OperationResourceLocationQuery and(Expression expression) {
        if (expressionChainList.isEmpty()) {
            expressionChainList.add(new ExpressionChain());
        }
        expressionChainList.get(0).and(expression);
        return this;
    }

    public List<ExpressionChain> getExpressionChainList() {
        return expressionChainList;
    }

    public void setExpressionChainList(List<ExpressionChain> expressionChainList) {
        this.expressionChainList = expressionChainList;
    }
}
