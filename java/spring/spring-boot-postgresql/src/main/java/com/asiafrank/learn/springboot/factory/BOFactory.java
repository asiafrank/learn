package com.asiafrank.learn.springboot.factory;


import com.asiafrank.learn.springboot.bo.SampleBO;

/**
 * BOs - Business Objects
 * <p>
 * </p>
 * Created at 30/12/2016.
 *
 * @author asiafrank
 */
public final class BOFactory {
    private static final BOFactory instance = new BOFactory();

    private SampleBO sampleBO;

    public static BOFactory instance() {
        return instance;
    }

    public SampleBO getSampleBO() {
        return sampleBO;
    }

    public void setSampleBO(SampleBO sampleBO) {
        this.sampleBO = sampleBO;
    }
}
