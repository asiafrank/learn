package com.asiafrank.akka.fsm;

import akka.actor.ActorRef;

/**
 * SetTarget
 * <p>
 * </p>
 * Created at 1/11/2017.
 *
 * @author zhangxf
 */
public final class SetTarget {
    final ActorRef ref;

    public SetTarget(ActorRef ref) {
        this.ref = ref;
    }
}
