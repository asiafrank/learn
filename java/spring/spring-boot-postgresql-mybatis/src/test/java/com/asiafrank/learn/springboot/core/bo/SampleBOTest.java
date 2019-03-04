package com.asiafrank.learn.springboot.core.bo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author zhangxiaofan 2019/03/04-19:12
 */
@RunWith(SpringRunner.class)
public class SampleBOTest {

    @MockBean
    private SampleBO sampleBO;

    @Before
    public void set() {
        Mockito.when(sampleBO.count()).thenReturn(10);
    }

    @Test
    public void test() {
        assertEquals(sampleBO.count(), 10);
    }
}
