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
public interface SampleBO extends BO<Sample, Long, SampleRepo<Sample, Long>> {
    List<Sample> find(String name);
    List<Sample> find(List<String> ids);
    Page<Sample> find(Sample prob, Pageable pageable);
}
