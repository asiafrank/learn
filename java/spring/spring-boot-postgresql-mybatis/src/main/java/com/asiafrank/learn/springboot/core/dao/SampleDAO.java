package com.asiafrank.learn.springboot.core.dao;

import com.asiafrank.learn.springboot.core.base.DAO;
import com.asiafrank.learn.springboot.core.model.Sample;
import com.asiafrank.learn.springboot.core.vo.SampleVO;

import java.util.Collection;

/**
 * SampleDAO
 * <p>
 * </p>
 * Created at 4/6/2017.
 *
 * @author zhangxf
 */
public interface SampleDAO extends DAO<Sample, SampleVO, Long> {
    void batchInsert(Collection<Sample> coll);

    long queryTest(int x, int y);

    String queryTest2(String str);
}
