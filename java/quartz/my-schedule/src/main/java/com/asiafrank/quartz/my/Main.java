package com.asiafrank.quartz.my;

import com.asiafrank.quartz.my.zktest.LockListener;
import com.asiafrank.quartz.my.zktest.ZKLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * ----------------------------------------------
 * 这仅仅是个例子
 * ----------------------------------------------
 *
 * 要求：（为了降低难度，一个 Job 只能对应一个 Trigger）
 * 1.跑多个进程，同一个 JobKey 的 Job 只能在一个进程中调度运行。
 * 2.当其中一个进程将 Job 的 Trigger 更改后，reschedule 该 Job。
 *
 * 实现：
 * 1.使用 zookeeper 分布式锁，获取锁的那个节点才能运行该 Job
 * 2.Trigger 的数据保存在 dir 节点上，获取锁的那个节点只需监听 dir 节点的数据变更
 *
 * @author zhangxf created at 2/26/2018.
 */
public class Main {

    private static ZooKeeper zk;

    private static Scheduler sch;

    private static String jobName       = "myHelloJob";
    private static String jobGroupName  = "group";
    private static String triggerName   = "myTrigger";
    private static String triggerGroup  = "group";
    private static String triggerPrefix = triggerName + "-" + triggerGroup + "-";
    private static int triggerTime = 10;

    private static String dir;

    private static final String charsetName = "UTF-8";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("please enter host:port");
            System.exit(2);
        }

        String hostPort      = args[0];

        dir = "/" + jobName + "-" + jobGroupName;

        ZKLock lock = null;
        try {
            zk = new ZooKeeper(hostPort, 3000, new DefaultWatcher());

            // dir 节点初始化
            Stat stat = zk.exists(dir, false);
            if (stat == null) {
                zk.create(dir, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } else {
                byte[] data = zk.getData(dir, false, stat);
                if (data == null || data.length == 0) { // 不存在 data，则设置初始值
                    zk.setData(dir, (triggerPrefix + triggerTime).getBytes(), -1);
                } else { // 获取 triggerTime
                    String dataStr = new String(data, charsetName);
                    try {
                        String triggerTimeStr = dataStr.substring(triggerPrefix.length());
                        triggerTime = Integer.valueOf(triggerTimeStr);
                    } catch (Exception e) {
                        // 字符串并没有满足 triggerPrefix-triggerTime 格式
                        e.printStackTrace();
                        return;
                    }
                }
            }

            sch = new StdSchedulerFactory().getScheduler();
            sch.start();
            System.out.println(zk.getSessionId() + "-schedule");

            lock = new ZKLock(zk, dir, new DefaultLockListener());
            if (lock.lock()) { // 获取到锁后，监听 dir 节点 data 的变更操作
                zk.getData(dir, new TriggerWatcher(), null);
            } else {
                System.out.println("get lock fail");
            }
        } catch (SchedulerException | InterruptedException | IOException | KeeperException e) {
            e.printStackTrace();
        }
        // ignore sch, ignore unlock
    }

    // 获取到锁后，监听 dir 节点 data 的变更操作
    private static class TriggerWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            // 获取到的 data 即 trigger 的调度间隔（单位秒）
            try {
                byte[] data = zk.getData(dir, false, null);
                if (data == null) { // data 为 null，unschedule
                    sch.unscheduleJob(new TriggerKey(triggerName, triggerGroup));
                } else { // 获取 triggerTime 并且 reschedule
                    String dataStr = new String(data, charsetName);
                    String triggerTimeStr = dataStr.substring(triggerPrefix.length());
                    triggerTime = Integer.valueOf(triggerTimeStr);

                    Trigger trigger = TriggerBuilder.newTrigger()
                                                    .withIdentity(triggerName, triggerGroup)
                                                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(triggerTime))
                                                    .build();
                    sch.rescheduleJob(trigger.getKey(), trigger);
                }
            } catch (KeeperException | InterruptedException | SchedulerException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DefaultWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println(event);
        }
    }

    private static class DefaultLockListener implements LockListener {

        @Override
        public void lockAcquired() {
            System.out.println("lock acquired");

            JobDetail job = JobBuilder.newJob(MyHelloJob.class)
                                      .withIdentity(jobName, jobGroupName)
                                      .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                                            .withIdentity(triggerName, triggerGroup)
                                            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(triggerTime))
                                            .forJob(job)
                                            .build();

            try {
                sch.scheduleJob(job, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void lockReleased() {
            System.out.println("lock released");
        }
    }
}
