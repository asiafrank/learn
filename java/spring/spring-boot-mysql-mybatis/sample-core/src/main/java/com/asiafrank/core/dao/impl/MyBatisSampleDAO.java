package com.asiafrank.core.dao.impl;

import com.asiafrank.core.base.AbstractDAO;
import com.asiafrank.core.dao.SampleDAO;
import com.asiafrank.core.model.Sample;
import com.asiafrank.core.vo.SampleVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("sampleDAO")
public class MyBatisSampleDAO extends AbstractDAO<Sample, SampleVO, Long> implements SampleDAO {
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
