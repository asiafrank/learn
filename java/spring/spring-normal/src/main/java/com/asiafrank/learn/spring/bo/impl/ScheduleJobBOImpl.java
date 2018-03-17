package com.asiafrank.learn.spring.bo.impl;

import com.asiafrank.learn.spring.bo.AbstractBO;
import com.asiafrank.learn.spring.bo.ScheduleJobBO;
import com.asiafrank.learn.spring.model.ScheduleJob;
import com.asiafrank.learn.spring.repo.ScheduleJobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScheduleJobBOImpl
 * <p>
 * </p>
 * Created at 28/12/2016.
 *
 * @author asiafrank
 */
@Service("scheduleJobBO")
public class ScheduleJobBOImpl extends AbstractBO<ScheduleJob, String, ScheduleJobRepo<ScheduleJob, String>> implements ScheduleJobBO {

    private ScheduleJobRepo<ScheduleJob, String> scheduleJobRepo;

    @Override
    protected ScheduleJobRepo<ScheduleJob, String> getRepo() {
        return scheduleJobRepo;
    }

    @Autowired
    public void setScheduleJobRepo(ScheduleJobRepo<ScheduleJob, String> scheduleJobRepo) {
        this.scheduleJobRepo = scheduleJobRepo;
    }

    @Override
    public List<ScheduleJob> find(String name) {
        return getRepo().find(name);
    }

    @Override
    public List<ScheduleJob> findAll(boolean enable) {
        return getRepo().findAll(enable);
    }
}
