package com.asiafrank.learn.spring.repo;

import com.asiafrank.learn.spring.model.Sample;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * SampleRepoCustom
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
@NoRepositoryBean
public interface SampleRepoCustom {
    List<Sample> find(List<String> ids);
}
