package com.asiafrank.learn.springboot.core.dao.impl;

import com.asiafrank.learn.springboot.core.base.AbstractDAO;
import com.asiafrank.learn.springboot.core.dao.SampleDAO;
import com.asiafrank.learn.springboot.core.model.Sample;
import com.asiafrank.learn.springboot.core.vo.SampleVO;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository("sampleDAO")
public class MyBatisSampleDAO extends AbstractDAO<Sample, SampleVO, Long> implements SampleDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    protected SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    protected String getNamespace() {
        return "sampleDAO";
    }

    @Override
    public void batchInsert(Collection<Sample> coll) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            Map<String, Object> map = Maps.newHashMap();
            for (Sample s : coll) {
                map.put("entity", s);
                session.insert(getNamespace() + ".insert", map);
            }
            session.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public long queryTest(int x, int y) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("x", x);
        params.put("y", y);
        return getSqlSessionTemplate().selectOne(getNamespace() + ".queryTest", params);
    }

    @Override
    public String queryTest2(String str) {
        return getSqlSessionTemplate().selectOne(getNamespace() + ".queryTest2", str);
    }
}
