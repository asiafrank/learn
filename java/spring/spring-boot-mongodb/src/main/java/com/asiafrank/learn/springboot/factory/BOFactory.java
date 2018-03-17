package com.asiafrank.learn.springboot.factory;

import com.asiafrank.learn.springboot.bo.SampleBO;

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

    public SampleBO getSampleBO() {
        return sampleBO;
    }

    public void setSampleBO(SampleBO sampleBO) {
        this.sampleBO = sampleBO;
    }
}
