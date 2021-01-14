package com.example.appoperation.db.query;

import java.util.ArrayList;
import java.util.List;

import com.example.appoperation.db.base.Expression;
import com.example.appoperation.db.base.ExpressionChain;
import com.example.appoperation.db.po.OperationResourceConditionRelationPO;

public class OperationResourceConditionRelationQuery extends OperationResourceConditionRelationPO {
    private static final long serialVersionUID = 1L;

    private List<ExpressionChain> expressionChainList;

    public OperationResourceConditionRelationQuery() {
        expressionChainList = new ArrayList<ExpressionChain>();
    }

    public OperationResourceConditionRelationQuery or(ExpressionChain expressionChain) {
        expressionChainList.add(expressionChain);
        return this;
    }

    public OperationResourceConditionRelationQuery or(Expression expression) {
        expressionChainList.add(new ExpressionChain().and(expression));
        return this;
    }

    public OperationResourceConditionRelationQuery and(Expression expression) {
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
