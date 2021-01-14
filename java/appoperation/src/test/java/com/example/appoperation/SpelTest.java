package com.example.appoperation;

import com.example.appoperation.component.CalculateComponent;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Spel tutorial: https://www.baeldung.com/spring-expression-language
 * @author zhangxiaofan 2021/01/12-11:42
 */
public class SpelTest {

    @Test
    public void test() {
        CalculateComponent c = new CalculateComponent();

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expr = expressionParser.parseExpression("activeDays(1,'123', #param) or activeDays(10,'567', #param)");

        Map<String, Object> param = new HashMap<>();
        param.put("1", "-----");

        EvaluationContext ctx = new StandardEvaluationContext(c);
        ctx.setVariable("param", param);

        Boolean result = (Boolean) expr.getValue(ctx);
        Assertions.assertEquals(Boolean.FALSE, result);
    }
}
