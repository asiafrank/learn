package com.asiafrank.core.bo.impl;

import com.asiafrank.core.base.AbstractBO;
import com.asiafrank.core.base.DAO;
import com.asiafrank.core.bo.SampleBO;
import com.asiafrank.core.dao.SampleDAO;
import com.asiafrank.core.model.Sample;
import com.asiafrank.core.vo.SampleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sampleBO")
public class DefaultSampleBO extends AbstractBO<Sample, SampleVO, Long> implements SampleBO {
    @Autowired
    private SampleDAO sampleDAO;

    @Override
    protected DAO<Sample, SampleVO, Long> getDAO() {
        return sampleDAO;
    }
}
