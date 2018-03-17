package com.asiafrank.learn.springboot.repo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * AbstractCustom
 * <p>
 * </p>
 * Created at 1/16/2017.
 *
 * @author zhangxf
 */
public abstract class AbstractCustom {
    private EntityManager em;

    protected EntityManager getEm() {
        return em;
    }

    @Autowired
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
