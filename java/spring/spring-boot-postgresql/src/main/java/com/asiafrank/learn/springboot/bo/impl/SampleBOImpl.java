package com.asiafrank.learn.springboot.bo.impl;

import com.asiafrank.learn.springboot.bo.AbstractBO;
import com.asiafrank.learn.springboot.bo.SampleBO;
import com.asiafrank.learn.springboot.model.Sample;
import com.asiafrank.learn.springboot.repo.SampleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SampleBOImpl
 * <p>
 * </p>
 * Created at 28/12/2016.
 *
 * @author asiafrank
 */
@Service("sampleBO")
public class SampleBOImpl extends AbstractBO<Sample, Long, SampleRepo<Sample, Long>> implements SampleBO {

    private SampleRepo<Sample, Long> sampleRepo;

    @Override
    protected SampleRepo<Sample, Long> getRepo() {
        return sampleRepo;
    }

    @Cacheable("index")
    @Override
    public List<Sample> findAll() {
        return getRepo().findAll();
    }

    @CacheEvict("index")
    @Override
    public void deleteAll() {
        getRepo().deleteAll();
    }

    @Override
    public List<Sample> find(String name) {
        return getRepo().find(name);
    }

    @Override
    public List<Sample> find(List<String> ids) {
        return getRepo().find(ids);
    }

    @Override
    public Page<Sample> find(Sample prob, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name", m -> m.stringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        Example<Sample> example = Example.of(prob, matcher);
        return getRepo().findAll(example, pageable);
    }

    @Autowired
    public void setSampleRepo(SampleRepo<Sample, Long> sampleRepo) {
        this.sampleRepo = sampleRepo;
    }
}
