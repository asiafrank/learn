package com.asiafrank.learn.spring.akka.container;

import com.asiafrank.learn.spring.akka.actor.Task;

import java.util.HashMap;
import java.util.Map;

/**
 * TaskContainer -
 * <p>
 * </p>
 * Created at 12/12/2016.
 *
 * @author asiafrank
 */
public class TaskContainer {

    private final Map<String, Task> container = new HashMap<>();
}
