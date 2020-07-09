package com.asiafrank.demo.core.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asiafrank.demo.core.base.AbstractDAO;
import com.asiafrank.demo.core.dao.SampleDAO;
import com.asiafrank.demo.core.model.Sample;
import com.asiafrank.demo.core.vo.SampleVO;

@Repository("sampleDAO")
public class SampleDAOImpl extends AbstractDAO<Sample, SampleVO, Long> implements SampleDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "sampleDAO";
    }
}
