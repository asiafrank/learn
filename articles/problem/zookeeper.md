## zookeeper 问题
### 1. 官方提供的分布式锁 WriteLock, 其 id 属性在同一 zk session 下的疑惑。
##### WriteLock.id 未修改

模拟的单元测试如下(注意 WriteLock.id)：
```java
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
```

上述单元测试做了以下事情：

- 1.连接 Zookeeper
- 2.建立对应a,b,c的Listener
- 3.使用一个Zookeeper实例和相同的 dir 值，新建三个WriteLock实例a,b,c
- 4.a,b,c 三个锁依次调用 lock 方法，打印锁状态
- 5.a,b,c 依次释放锁，并且间隔1秒，让 listener 充分执行，然后打印锁id

我们预想当中，运行结果应该是：

- 1.b_a = true, b_b = false, b_c = false，并且 a,b,c 的 id 各不相同，zookeeper 会存在三个临时节点
- 2.当a锁释放时，打印 released，b(或c)锁获得锁，然后打印 acquired
- 3.当b锁释放时，打印 released，c(或b)锁获得锁，然后打印 acquired
- 4.最后 c 释放锁，打印 released

然而真实运行结果如下：

```shell
a acquired
========= before unlock ======
a.lock = true a.id = /lock/x-72353314515517462-0000000026
b.lock = false b.id = x-72353314515517462-0000000026
c.lock = false c.id = x-72353314515517462-0000000026
a released
18-07-13 20:43:27.835/GMT+08:00 WARN  No children in: /lock when we've just created one! Lets recreate it...
b acquired
b released
18-07-13 20:43:28.835/GMT+08:00 WARN  No children in: /lock when we've just created one! Lets recreate it...
c acquired
c released
========= after unlock ======
a.id = null
b.id = null
c.id = null
```
- 1.b_a = true, b_b = false, b_c = false，只有 a 的 id 包含 dir 部分，b与c的 id 一样，且他们没有 dir 部分。这时候 zookeeper 上只有 /lock/x-72353314515517460-0000000021 这一个临时节点
- 2.a 释放锁后，zookeeper 的唯一节点删除，/lock 下没有节点，b 收到了 a 释放锁的消息，尝试获取锁。获取过程中发现没有任何节点，然后重新建一个临时节点作为当前 b 锁的节点。
- 3.同样 b 释放锁后，zookeeper 的唯一节点删除，/lock 下没有节点，c 收到了 b 释放锁的消息，尝试获取锁。获取过程中发现没有任何节点，然后重新建一个临时节点作为当前 c 锁的节点。
- 5.c 锁释放。打印a,b,c锁的id，全部都变成了 null

上述结果，虽然可以作为锁正常使用。但锁的 id 值不统一，和 zookeeper 上节点变化并不是预想当中的状态，而且打印 "No children in: /lock when we've just created one! Lets recreate it..." 的警告后重新创建临时节点的举措，也像是为了补救 id 值不统一所产生的"窟窿"。下面尝试修正。

##### WriteLock.id 修改一（此修改不可取！）
LockZooKeeperOperation 内部类的 findPrefixInChildren 方法中有 `id = name;` 语句，因该 name 是节点名，不包含 dir，这里赋值给 id 不合理。（这里也是上面打印结果中 b.id 和 c.id 不一致的原因）现修改如下：
```java
id = dir + "/" + name;
```
然而经过这一修正过后，同一 zk session 下，锁不起互斥作用了。

结果打印如下：
```shell
WatchedEvent state:SyncConnected type:None path:null
a acquired
b acquired
c acquired
========= before unlock ======
a.lock = true a.id = /lock/x-72353314515517464-0000000032
b.lock = true b.id = /lock/x-72353314515517464-0000000032
c.lock = true c.id = /lock/x-72353314515517464-0000000032
a released
b released
c released
========= after unlock ======
a.id = null
b.id = null
c.id = null
```
a还没释放，b，c就打印了 acquired。

分析原因：findPrefixInChildren 方法里 id 赋值为正确格式之后，其 id 就是拥有锁的那个节点名。然后b,c锁接下来的判断(尤其是less then me判断)就当成自己已经获取到锁了。最终锁失效。findPrefixInChildren 中，获取之前相同前缀的节点循环中不应该赋值给 id。

##### WriteLock.id 修正二
findPrefixInChildren 中删除赋值语句即可。该方法变为如下形式：
```java
private void findPrefixInChildren(String prefix, ZooKeeper zookeeper, String dir) 
    throws KeeperException, InterruptedException {
    if (id == null) {
        id = zookeeper.create(dir + "/" + prefix, data, 
                getAcl(), EPHEMERAL_SEQUENTIAL);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Created id: " + id);
        }
    }
}
```
如此一来，节点创建正确，能正确支持同一 session 锁互斥。结果如下：

```shell
a acquired
========= before unlock ======
a.lock = true a.id = /lock/x-72353314515517463-0000000029
b.lock = false b.id = /lock/x-72353314515517463-0000000030
c.lock = false c.id = /lock/x-72353314515517463-0000000031
a released
b acquired
b released
c acquired
c released
========= after unlock ======
a.id = null
b.id = null
c.id = null
```

但是，与此同时我注意到 findPrefixInChildren 方法注释写着：
```java
find if we have been created earlier if not create our node
```
还有 execute 方法中的一段行内注释：
```java
lets try look up the current ID if we failed 
in the middle of creating the znode
```
也就是说分布式锁(不同zk session)情况下，调用 zookeeper.create 方法时失败，id 还是为 null，findPrefixInChildren 中的 id 赋值操作的目的是为了弥补这种 znode create 失败的情况。令人疑惑的是这里的 id 赋值是不包含 dir 的。而且这种不正常的赋值以 x 开头，在判断 lessThanMe 逻辑中，getChildren 方法返回的列表字符串都包含 dir，且 dir 必须以'/'开头，x 字符是比 '/' 大的，因此不管怎样，只要 getChildren 返回不为空，则这个不正常的 id 定会拿到一个 lessThanMe 节点，然后watch它，接着继续执行下去。但是这个不正常的 id 没有任何意义，所以我建议使用修正二的做法。

相关测试代码见 [test](/java/zookeeper/src/test/java/com/asiafrank/learn/zookeeper/ZKTest.java)
##### 另，附官方的 WriteLock.java：
```java
/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asiafrank.learn.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A <a href="package.html">protocol to implement an exclusive
 *  write lock or to elect a leader</a>. <p/> You invoke {@link #lock()} to 
 *  start the process of grabbing the lock; you may get the lock then or it may be 
 *  some time later. <p/> You can register a listener so that you are invoked 
 *  when you get the lock; otherwise you can ask if you have the lock
 *  by calling {@link #isOwner()}
 *
 */
public class WriteLock extends ProtocolSupport {
    private static final Logger LOG = LoggerFactory.getLogger(WriteLock.class);

    private final String dir;
    private String id;
    private ZNodeName idName;
    private String ownerId;
    private String lastChildId;
    private byte[] data = {0x12, 0x34};
    private LockListener callback;
    private LockZooKeeperOperation zop;
    
    /**
     * zookeeper contructor for writelock
     * @param zookeeper zookeeper client instance
     * @param dir the parent path you want to use for locking
     * @param acl the acls that you want to use for all the paths,
     * if null world read/write is used.
     */
    public WriteLock(ZooKeeper zookeeper, String dir, List<ACL> acl) {
        super(zookeeper);
        this.dir = dir;
        if (acl != null) {
            setAcl(acl);
        }
        this.zop = new LockZooKeeperOperation();
    }
    
    /**
     * zookeeper contructor for writelock with callback
     * @param zookeeper the zookeeper client instance
     * @param dir the parent path you want to use for locking
     * @param acl the acls that you want to use for all the paths
     * @param callback the call back instance
     */
    public WriteLock(ZooKeeper zookeeper, String dir, List<ACL> acl, 
            LockListener callback) {
        this(zookeeper, dir, acl);
        this.callback = callback;
    }

    /**
     * return the current locklistener
     * @return the locklistener
     */
    public LockListener getLockListener() {
        return this.callback;
    }
    
    /**
     * register a different call back listener
     * @param callback the call back instance
     */
    public void setLockListener(LockListener callback) {
        this.callback = callback;
    }

    /**
     * Removes the lock or associated znode if 
     * you no longer require the lock. this also 
     * removes your request in the queue for locking
     * in case you do not already hold the lock.
     * @throws RuntimeException throws a runtime exception
     * if it cannot connect to zookeeper.
     */
    public synchronized void unlock() throws RuntimeException {
        
        if (!isClosed() && id != null) {
            // we don't need to retry this operation in the case of failure
            // as ZK will remove ephemeral files and we don't wanna hang
            // this process when closing if we cannot reconnect to ZK
            try {
                
                ZooKeeperOperation zopdel = new ZooKeeperOperation() {
                    public boolean execute() throws KeeperException,
                        InterruptedException {
                        zookeeper.delete(id, -1);   
                        return Boolean.TRUE;
                    }
                };
                zopdel.execute();
            } catch (InterruptedException e) {
                LOG.warn("Caught: " + e, e);
                //set that we have been interrupted.
               Thread.currentThread().interrupt();
            } catch (KeeperException.NoNodeException e) {
                // do nothing
            } catch (KeeperException e) {
                LOG.warn("Caught: " + e, e);
                throw (RuntimeException) new RuntimeException(e.getMessage()).
                    initCause(e);
            }
            finally {
                if (callback != null) {
                    callback.lockReleased();
                }
                id = null;
            }
        }
    }
    
    /** 
     * the watcher called on  
     * getting watch while watching 
     * my predecessor
     */
    private class LockWatcher implements Watcher {
        public void process(WatchedEvent event) {
            // lets either become the leader or watch the new/updated node
            LOG.debug("Watcher fired on path: " + event.getPath() + " state: " + 
                    event.getState() + " type " + event.getType());
            try {
                lock();
            } catch (Exception e) {
                LOG.warn("Failed to acquire lock: " + e, e);
            }
        }
    }
    
    /**
     * a zoookeeper operation that is mainly responsible
     * for all the magic required for locking.
     */
    private  class LockZooKeeperOperation implements ZooKeeperOperation {
        
        /** find if we have been created earler if not create our node
         * 
         * @param prefix the prefix node
         * @param zookeeper teh zookeeper client
         * @param dir the dir paretn
         * @throws KeeperException
         * @throws InterruptedException
         */
        private void findPrefixInChildren(String prefix, ZooKeeper zookeeper, String dir) 
            throws KeeperException, InterruptedException {
            // 建议 for 循环和 getChildren 代码删除
            List<String> names = zookeeper.getChildren(dir, false);
            for (String name : names) {
                if (name.startsWith(prefix)) {
                    id = name;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Found id created last time: " + id);
                    }
                    break;
                }
            }
            if (id == null) {
                id = zookeeper.create(dir + "/" + prefix, data, 
                        getAcl(), EPHEMERAL_SEQUENTIAL);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Created id: " + id);
                }
            }

        }
        
        /**
         * the command that is run and retried for actually 
         * obtaining the lock
         * @return if the command was successful or not
         */
        public boolean execute() throws KeeperException, InterruptedException {
            do {
                if (id == null) {
                    long sessionId = zookeeper.getSessionId();
                    String prefix = "x-" + sessionId + "-";
                    // lets try look up the current ID if we failed 
                    // in the middle of creating the znode
                    findPrefixInChildren(prefix, zookeeper, dir);
                    idName = new ZNodeName(id);
                }
                if (id != null) {
                    List<String> names = zookeeper.getChildren(dir, false);
                    if (names.isEmpty()) {
                        LOG.warn("No children in: " + dir + " when we've just " +
                        "created one! Lets recreate it...");
                        // lets force the recreation of the id
                        id = null;
                    } else {
                        // lets sort them explicitly (though they do seem to come back in order ususally :)
                        SortedSet<ZNodeName> sortedNames = new TreeSet<ZNodeName>();
                        for (String name : names) {
                            sortedNames.add(new ZNodeName(dir + "/" + name));
                        }
                        ownerId = sortedNames.first().getName();
                        SortedSet<ZNodeName> lessThanMe = sortedNames.headSet(idName);
                        if (!lessThanMe.isEmpty()) {
                            ZNodeName lastChildName = lessThanMe.last();
                            lastChildId = lastChildName.getName();
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("watching less than me node: " + lastChildId);
                            }
                            Stat stat = zookeeper.exists(lastChildId, new LockWatcher());
                            if (stat != null) {
                                return Boolean.FALSE;
                            } else {
                                LOG.warn("Could not find the" +
                                		" stats for less than me: " + lastChildName.getName());
                            }
                        } else {
                            if (isOwner()) {
                                if (callback != null) {
                                    callback.lockAcquired();
                                }
                                return Boolean.TRUE;
                            }
                        }
                    }
                }
            }
            while (id == null);
            return Boolean.FALSE;
        }
    };

    /**
     * Attempts to acquire the exclusive write lock returning whether or not it was
     * acquired. Note that the exclusive lock may be acquired some time later after
     * this method has been invoked due to the current lock owner going away.
     */
    public synchronized boolean lock() throws KeeperException, InterruptedException {
        if (isClosed()) {
            return false;
        }
        ensurePathExists(dir);

        return (Boolean) retryOperation(zop);
    }

    /**
     * return the parent dir for lock
     * @return the parent dir used for locks.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Returns true if this node is the owner of the
     *  lock (or the leader)
     */
    public boolean isOwner() {
        return id != null && ownerId != null && id.equals(ownerId);
    }

    /**
     * return the id for this lock
     * @return the id for this lock
     */
    public String getId() {
       return this.id;
    }
}
```