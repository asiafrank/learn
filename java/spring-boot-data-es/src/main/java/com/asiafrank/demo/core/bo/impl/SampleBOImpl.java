package com.asiafrank.demo.core.bo.impl;

import com.asiafrank.demo.core.base.AbstractBO;
import com.asiafrank.demo.core.base.DAO;
import com.asiafrank.demo.core.bo.SampleBO;
import com.asiafrank.demo.core.dao.SampleDAO;
import com.asiafrank.demo.core.model.Sample;
import com.asiafrank.demo.core.vo.SampleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sampleBO")
public class SampleBOImpl extends AbstractBO<Sample, SampleVO, Long> implements SampleBO {
    @Autowired
    private SampleDAO sampleDAO;

    @Override
    protected DAO<Sample, SampleVO, Long> getDAO() {
        return sampleDAO;
    }

    @Transactional
    @Override
    public void take() {
        List<Sample> samples = find();
        System.out.println(samples.size());
    }
}
