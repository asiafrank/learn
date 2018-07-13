package com.asiafrank.learn.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author zhangxf created at 7/13/2018.
 */
public class ZKTest {

    private static final String hostPort = "<host:port>";

    @Test
    public void writeLockTest() throws Exception {
        ZooKeeper zk = new ZooKeeper(hostPort, 3000, new DefaultWatcher());
        DefaultListener a_litener = new DefaultListener("a");
        DefaultListener b_litener = new DefaultListener("b");
        DefaultListener c_litener = new DefaultListener("c");

        WriteLock a_lock = new WriteLock(zk, "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE, a_litener);
        WriteLock b_lock = new WriteLock(zk, "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE, b_litener);
        WriteLock c_lock = new WriteLock(zk, "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE, c_litener);

        boolean b_a = a_lock.lock(); // boolean of a.lock()
        boolean b_b = b_lock.lock(); // boolean of b.lock()
        boolean b_c = c_lock.lock(); // boolean of c.lock()

        System.out.println("========= before unlock ======");
        System.out.println("a.lock = " + b_a + " a.id = " + a_lock.getId());
        System.out.println("b.lock = " + b_b + " b.id = " + b_lock.getId());
        System.out.println("c.lock = " + b_c + " c.id = " + c_lock.getId());

        // 依次释放锁
        try {
            a_lock.unlock();
            Thread.sleep(1000L);
            if (b_lock.isOwner())
                b_lock.unlock();
            else
                c_lock.unlock();
            Thread.sleep(1000L);
            if (b_lock.isOwner())
                b_lock.unlock();
            else
                c_lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("========= after unlock ======");
        System.out.println("a.id = " + a_lock.getId());
        System.out.println("b.id = " + b_lock.getId());
        System.out.println("c.id = " + c_lock.getId());
    }

    @Test
    public void writeLockFixTest() throws Exception {
        ZooKeeper zk = new ZooKeeper(hostPort, 3000, new DefaultWatcher());
        DefaultListener a_litener = new DefaultListener("a");
        DefaultListener b_litener = new DefaultListener("b");
        DefaultListener c_litener = new DefaultListener("c");

        WriteLockFix a_lock = new WriteLockFix(zk, "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE, a_litener);
        WriteLockFix b_lock = new WriteLockFix(zk, "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE, b_litener);
        WriteLockFix c_lock = new WriteLockFix(zk, "/lock", ZooDefs.Ids.OPEN_ACL_UNSAFE, c_litener);

        boolean b_a = a_lock.lock(); // boolean of a.lock()
        boolean b_b = b_lock.lock(); // boolean of b.lock()
        boolean b_c = c_lock.lock(); // boolean of c.lock()

        System.out.println("========= before unlock ======");
        System.out.println("a.lock = " + b_a + " a.id = " + a_lock.getId());
        System.out.println("b.lock = " + b_b + " b.id = " + b_lock.getId());
        System.out.println("c.lock = " + b_c + " c.id = " + c_lock.getId());

        // 依次释放锁
        try {
            a_lock.unlock();
            Thread.sleep(1000L);
            if (b_lock.isOwner())
                b_lock.unlock();
            else
                c_lock.unlock();
            Thread.sleep(1000L);
            if (b_lock.isOwner())
                b_lock.unlock();
            else
                c_lock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("========= after unlock ======");
        System.out.println("a.id = " + a_lock.getId());
        System.out.println("b.id = " + b_lock.getId());
        System.out.println("c.id = " + c_lock.getId());
    }

    @Test
    public void testOrderWithSamePrefix() throws Exception {
        String[] names = { "x-3", "x-5", "x-11", "x-1" };
        String[] expected = { "x-1", "x-3", "x-5", "x-11" };
        assertOrderedNodeNames(names, expected);
    }
    @Test
    public void testOrderWithDifferentPrefixes() throws Exception {
        String[] names = { "r-3", "r-2", "r-1", "w-2", "w-1" };
        String[] expected = { "r-1", "r-2", "r-3", "w-1", "w-2" };
        assertOrderedNodeNames(names, expected);
    }

    protected void assertOrderedNodeNames(String[] names, String[] expected) {
        int size = names.length;
        assertEquals("The two arrays should be the same size!", names.length, expected.length);
        SortedSet<ZNodeName> nodeNames = new TreeSet<ZNodeName>();
        for (String name : names) {
            nodeNames.add(new ZNodeName(name));
        }

        int index = 0;
        for (ZNodeName nodeName : nodeNames) {
            String name = nodeName.getName();
            assertEquals("Node " + index, expected[index++], name);
        }
    }

    private static class DefaultListener implements LockListener {

        private String name;

        public DefaultListener(String name) {
            this.name = name;
        }

        @Override
        public void lockAcquired() {
            System.out.println(name + " acquired");
        }

        @Override
        public void lockReleased() {
            System.out.println(name + " released");
        }
    }

    private static class DefaultWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println(event);
        }
    }
}
