## Java 使用中的问题
### 1. ThreadLocal 实现
- Thread 对象包含一个叫 ThreadLocalMap 的属性，该 map 对象的 key 为ThreadLocal，value 为相应的值；ThreadLocalMap 存在目的是为了保证一个 Thread 实例能保存多个 ThreadLocal 的值
- 一个 ThreadLocal 只能存储一个值，如果需要在一个 Thread 实例中包含多个值，则 new 出多个 ThreadLocal 使用即可；其实 ThreadLocal 自己不会保存值，值是以 value 形式保存在 ThreadLocalMap 中（key 为 ThreadLocal 对象）；每个 ThreadLocal 对象有自己的 threadLocalHashCode，该 hashCode 由 AtomicInteger 维护
- ThreadLocalMap 并不直接暴露给用户使用，而是由 ThreadLocal 对象的 get 方法，并使用 Thread.currentThread() 从中获得 ThreadLocalMap 然后取得相应的 ThreadLocal 的值。

ThreadLocal.get 方法如下：
```java
/**
 * Returns the value in the current thread's copy of this
 * thread-local variable.  If the variable has no value for the
 * current thread, it is first initialized to the value returned
 * by an invocation of the {@link #initialValue} method.
 *
 * @return the current thread's value of this thread-local
 */
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}
```

ThreadLocal.set 方法如下：
```java
/**
 * Sets the current thread's copy of this thread-local variable
 * to the specified value.  Most subclasses will have no need to
 * override this method, relying solely on the {@link #initialValue}
 * method to set the values of thread-locals.
 *
 * @param value the value to be stored in the current thread's copy of
 *        this thread-local.
 */
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}
```

### 2. Java NIO SelectableChannel.configureBlocking 作用
- 当 SelectableChannel.configureBlocking(false) 时，每次 accept 不会阻塞，如果没有值接收，将会返回 null
- 当 SelectableChannel.configureBlocking(true) 时，每次 accept 会阻塞，有值才会返回

我们一般都将 configureBlocking 设为 false，是为了不阻塞线程，充分利用线程资源，达到多路复用的目的。

多路复用IO模型参考：Unix网络编程第六章 https://www.zhihu.com/question/28594409/answer/74003996

epoll,kqueue 原理参考 https://www.zhihu.com/question/20122137/answer/14049112

为什么不用信号驱动IO，https://www.v2ex.com/t/207186