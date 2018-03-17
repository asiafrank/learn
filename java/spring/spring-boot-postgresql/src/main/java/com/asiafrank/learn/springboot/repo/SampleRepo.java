package com.asiafrank.learn.springboot.repo;

import com.asiafrank.learn.springboot.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * SampleRepo
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
public interface SampleRepo<T extends Sample, ID extends Long>  extends JpaRepository<T, ID>, SampleRepoCustom {
    // Declare query methods here
    @Query("SELECT s from Sample s where s.name = :name")
    List<T> find(String name);
}
