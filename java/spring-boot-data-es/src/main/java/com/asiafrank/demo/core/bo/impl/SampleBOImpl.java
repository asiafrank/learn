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

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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
        Sample s = new Sample();
        s.setName("插入时间实验");
        s.setDescription("插入时间实验");
        sampleDAO.insert(s);
        System.out.println("insert time," + LocalDateTime.now());
        try {
            TimeUnit.MINUTES.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("transaction end time," + LocalDateTime.now());
    }
}
