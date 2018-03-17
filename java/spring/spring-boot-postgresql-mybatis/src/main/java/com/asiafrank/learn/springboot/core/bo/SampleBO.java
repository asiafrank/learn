package com.asiafrank.learn.springboot.core.bo;

import com.asiafrank.learn.springboot.core.base.BO;
import com.asiafrank.learn.springboot.core.model.Sample;
import com.asiafrank.learn.springboot.core.vo.SampleVO;

import java.util.Collection;

public interface SampleBO extends BO<Sample, SampleVO, Long> {

    void txTest(Sample sample) throws Exception;

    void invokeTxTest(Sample sample);

    void batchInsert(Collection<Sample> coll);

    long queryTest(int x, int y);

    String queryTest2(String str);
}
