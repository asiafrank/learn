package com.asiafrank.demo.core.bo;

import com.asiafrank.demo.core.base.BO;
import com.asiafrank.demo.core.model.Sample;
import com.asiafrank.demo.core.vo.SampleVO;

public interface SampleBO extends BO<Sample, SampleVO, Long> {
    void take();
}
