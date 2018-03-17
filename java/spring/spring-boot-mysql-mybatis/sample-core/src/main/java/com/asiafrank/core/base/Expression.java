package com.asiafrank.core.base;

import java.util.List;

public class Expression {
    private int type;
    private String column;
    private String operator;
    private Object value;
    private Object value1;
    private List<?> values;

    public Expression(int type, String column, String operator) {
        this.type = type;
        this.column = column;
        this.operator = operator;
    }

    public Expression(int type, String column, String operator, Object value) {
        this.type = type;
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Expression(int type, String column, String operator, Object value, Object value1) {
        this.type = type;
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.value1 = value1;
    }

    public Expression(int type, String column, String operator, List<?> values) {
        this.type = type;
        this.column = column;
        this.operator = operator;
        this.values = values;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue1() {
        return value1;
    }

    public void setValue1(Object value1) {
        this.value1 = value1;
    }

    public List<?> getValues() {
        return values;
    }

    public void setValues(List<?> values) {
        this.values = values;
    }
}
