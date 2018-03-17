package com.asiafrank.learn.springboot.bo;

import com.asiafrank.learn.springboot.model.Sample;
import com.asiafrank.learn.springboot.repo.SampleRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * SampleBO
 * <p>
 * </p>
 * Created at 28/12/2016.
 *
 * @author asiafrank
 */
public interface SampleBO extends BO<Sample, String, SampleRepo<Sample, String>> {
    void sharedCustomMethod(String id);

    // findAll for caching
    List<Sample> find();

    Sample find(String name);

    // save for cache clean
    Sample create(Sample entity);

    // save for cache clean
    Sample update(Sample entity);

    // deleteAll for cache clean
    void delete();

    List<Sample> find(List<String> ids);

    Page<Sample> find(Sample prob, Pageable pageable);
}
