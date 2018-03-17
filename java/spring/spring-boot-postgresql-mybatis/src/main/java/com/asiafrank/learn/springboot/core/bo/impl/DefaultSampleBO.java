package com.asiafrank.learn.springboot.core.bo.impl;

import com.asiafrank.learn.springboot.core.base.AbstractBO;
import com.asiafrank.learn.springboot.core.base.DAO;
import com.asiafrank.learn.springboot.core.bo.SampleBO;
import com.asiafrank.learn.springboot.core.dao.SampleDAO;
import com.asiafrank.learn.springboot.core.model.Sample;
import com.asiafrank.learn.springboot.core.vo.SampleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service("sampleBO")
public class DefaultSampleBO extends AbstractBO<Sample, SampleVO, Long> implements SampleBO {
    @Autowired
    private SampleDAO sampleDAO;

    @Override
    protected DAO<Sample, SampleVO, Long> getDAO() {
        return sampleDAO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void txTest(Sample sample) throws Exception {
        getDAO().insert(sample);
        throw new Exception("throw test");
    }

    @Override
    public void invokeTxTest(Sample sample) {
//        txTest(sample);
    }

    @Transactional
    @Override
    public void batchInsert(Collection<Sample> coll) {
        sampleDAO.batchInsert(coll);
    }

    @Override
    public long queryTest(int x, int y) {
        return sampleDAO.queryTest(x, y);
    }

    @Override
    public String queryTest2(String str) {
        return sampleDAO.queryTest2(str);
    }
}
