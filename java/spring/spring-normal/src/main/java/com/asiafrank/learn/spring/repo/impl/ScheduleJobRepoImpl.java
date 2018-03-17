package com.asiafrank.learn.spring.repo.impl;

import com.asiafrank.learn.spring.model.ScheduleJob;
import com.asiafrank.learn.spring.repo.AbstractCustom;
import com.asiafrank.learn.spring.repo.ScheduleJobRepoCustom;

/**
 * ScheduleJobRepoImpl
 * <p>
 * </p>
 * Created at 12/23/2016.
 *
 * @author asiafrank
 */
public class ScheduleJobRepoImpl extends AbstractCustom implements ScheduleJobRepoCustom {
    @Override
    public ScheduleJob customMethod() {
        System.out.println("custom method");
        return null;
    }
}
