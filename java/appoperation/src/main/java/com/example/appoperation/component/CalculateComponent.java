package com.example.appoperation.component;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 分群计算的 component
 * @author zhangxiaofan 2021/01/12-13:26
 */
@Slf4j
public class CalculateComponent {

    public boolean activeDays(int num, String s, Map<Integer, String> param) {
        log.info("activeDays invoke......num={}, s={}, param={}", num, s, param);
        return false;
    }
}
