package com.asiafrank.demo.core.dao;

import com.asiafrank.demo.core.base.DAO;
import com.asiafrank.demo.core.model.Sample;
import com.asiafrank.demo.core.vo.SampleVO;

import java.util.List;

public interface SampleDAO extends DAO<Sample, SampleVO, Long> {
    List<Sample> findForUpdate();
}
