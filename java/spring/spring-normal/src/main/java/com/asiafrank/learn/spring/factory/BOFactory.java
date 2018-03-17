package com.asiafrank.learn.spring.factory;

import com.asiafrank.learn.spring.bo.SampleBO;
import com.asiafrank.learn.spring.bo.ScheduleJobBO;

/**
 * BOs - Business Objects
 * <p>
 * For Actors to use.
 * </p>
 * Created at 30/12/2016.
 *
 * @author asiafrank
 */
public final class BOFactory {
    private final static BOFactory instance = new BOFactory();

    public static BOFactory instance() {
        return instance;
    }

    private SampleBO sampleBO;

    private ScheduleJobBO scheduleJobBO;

    public SampleBO getSampleBO() {
        return sampleBO;
    }

    public void setSampleBO(SampleBO sampleBO) {
        this.sampleBO = sampleBO;
    }

    public ScheduleJobBO getScheduleJobBO() {
        return scheduleJobBO;
    }

    public void setScheduleJobBO(ScheduleJobBO scheduleJobBO) {
        this.scheduleJobBO = scheduleJobBO;
    }
}
