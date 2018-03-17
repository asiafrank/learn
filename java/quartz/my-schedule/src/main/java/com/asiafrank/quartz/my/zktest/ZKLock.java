package com.asiafrank.quartz.my.zktest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.apache.zookeeper.ZooDefs.Ids;

/**
 * 参考：
 * zookeeper/docs/recipes.html
 * zookeeper/src/recipes/lock/src/java/org/apache/zookeeper/recipes/lock/WriteLock.java
 *
 * @author zhangxf created at 3/1/2018.
 */
public class ZKLock {
    private static final Logger LOG = LoggerFactory.getLogger(ZKLock.class);

    private final ZooKeeper zk;

    private final String dir; // "/_locknode_"

    private static final String childPrefix = "guid-lock-";

    private String    id;       // created znode path

    private ZNodeName lockName; // ZNodeName object of id
    private LockListener callback;

    public ZKLock(ZooKeeper zk, String dir) {
        this.zk = zk;
        this.dir = dir;
    }

    public ZKLock(ZooKeeper zk, String dir, LockListener callback) {
        this.zk = zk;
        this.dir = dir;
        this.callback = callback;
    }

    /**
     * Attempts to acquire the exclusive lock returning whether or not it was
     * acquired. Note that the exclusive lock may be acquired some time later after
     * this method has been invoked due to the current lock owner going away.
     */
    public synchronized boolean lock() throws KeeperException, InterruptedException {
        ensureExists(dir, CreateMode.PERSISTENT);

        do {
            // 建立锁节点
            if (id == null) {
                id = zk.create(dir + "/" + childPrefix, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                lockName = new ZNodeName(id);
            }

            // 尝试获取锁的判断
            if (id != null) {
                List<String> childrenNames = zk.getChildren(dir, false);
                if (childrenNames.isEmpty()) {
                    LOG.warn("No children in: {} when we've just created one! Lets recreate it...", dir);
                    // 创建了 id 但是获取 children 为空
                    // 则 id 忽略重设为 null，跳到下一次循环重新 create
                    id = null;
                    continue;
                }

                SortedSet<ZNodeName> sortedNames = new TreeSet<>();
                for (String cn : childrenNames) {
                    sortedNames.add(new ZNodeName(dir + "/" + cn));
                }
                String lowestId = sortedNames.first().getName();

                // 如果 lockName 就是最小的那个节点，则获取到了 lock
                // 否则监听前一个节点
                SortedSet<ZNodeName> lessThanMe = sortedNames.headSet(lockName);
                if (lessThanMe.isEmpty()) {
                    if (lowestId != null && id.equals(lowestId)) {
                        if (callback != null) {
                            callback.lockAcquired();
                        }
                        return true; // 获取到了锁
                    } else {
                        LOG.warn("lowestId is null! Lets getChildren again...");
                    }
                } else {
                    ZNodeName lastChildName = lessThanMe.last();
                    String lastChildId = lastChildName.getName();

                    Stat stat = zk.exists(lastChildId, new LockWatcher());
                    if (stat != null)
                        return false; // 没有获取到锁，等待 LockWatcher 的通知处理
                    else
                        LOG.warn("Could not find the stats for less than me node: {}", lastChildId);
                }
            }
        } while (id == null);

        return false;
    }

    /**
     * Removes the lock or associated znode if
     * you no longer require the lock. this also
     * removes your request in the queue for locking
     * in case you do not already hold the lock.
     * @throws RuntimeException throws a runtime exception
     * if it cannot connect to zookeeper.
     */
    public synchronized void unlock() {
        if (id != null) {
            try {
                zk.delete(id, -1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (KeeperException e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                if (callback != null) {
                    callback.lockReleased();
                }
                id = null;
            }
        }
    }

    /**
     * 当上一个锁释放后，接收通知，尝试获取锁
     *
     * 见 lock() 里的 zk.exists(lastChildId, new LockWatcher());
     */
    private class LockWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            try {
                lock(); // 这时候 id 不为 null，尝试获取锁
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ensureExists(String path, CreateMode flags) throws KeeperException, InterruptedException {
        KeeperException exception = null;
        for (int i = 0; i < 10; i++) {
            try {
                Stat stat = zk.exists(path, false);
                if (stat != null) {
                    return;
                }
                zk.create(path, null, Ids.OPEN_ACL_UNSAFE, flags);
                return;
            } catch (KeeperException.SessionExpiredException e) {
                LOG.warn("Session expired for: " + zk + " so reconnecting due to: " + e, e);
                throw e;
            } catch (KeeperException.ConnectionLossException e) {
                if (exception == null) {
                    exception = e;
                }
                LOG.debug("Attempt " + i + " failed with connection loss so " +
                          "attempting to reconnect: " + e, e);
                retryDelay(i);
            }
        }
        throw exception;
    }

    private void retryDelay(int attemptCount) {
        if (attemptCount > 0) {
            try {
                Thread.sleep(attemptCount * 500L);
            } catch (InterruptedException e) {
                LOG.debug("Failed to sleep: " + e, e);
            }
        }
    }

    public String getDir() {
        return dir;
    }

    public String getId() {
        return id;
    }

    public LockListener getCallback() {
        return callback;
    }

    public void setCallback(LockListener callback) {
        this.callback = callback;
    }
}
