package com.example.appoperation.db.base;

import java.util.ArrayList;
import java.util.List;

public class ExpressionChain {
    private List<Expression> expressionList;

    public ExpressionChain() {
        expressionList = new ArrayList<Expression>();
    }

    public ExpressionChain and(Expression expression) {
        expressionList.add(expression);
        return this;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }
}
