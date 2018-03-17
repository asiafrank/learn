package com.asiafrank.learn.springboot.repo.impl;

import com.asiafrank.learn.springboot.model.Sample;
import com.asiafrank.learn.springboot.repo.AbstractCustom;
import com.asiafrank.learn.springboot.repo.SampleRepoCustom;

import java.util.List;

/**
 * SampleRepoImpl
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
public class SampleRepoImpl extends AbstractCustom implements SampleRepoCustom {
    @Override
    @SuppressWarnings("unchecked")
    public List<Sample> find(List<String> ids) {
        return getEm().createQuery("SELECT s FROM Sample WHERE s.id IN :ids")
                      .setParameter("ids", ids)
                      .getResultList();
    }
}
