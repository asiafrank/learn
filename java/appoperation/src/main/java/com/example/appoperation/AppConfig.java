package com.example.appoperation;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.example.appoperation.component.HBaseComponent;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.Filter;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class AppConfig {

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    /**
     *  设置 redis 数据默认过期时间，默认1天
     *  设置@cacheable 序列化方式
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer)).entryTtl(Duration.ofDays(1));
        return configuration;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //序列化
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        // value值的序列化采用 jdk serializer
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // key的序列化采用 StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);

        template.setEnableTransactionSupport(false);
        return template;
    }

    @Value("${zkAddress}")
    private String zkAddress;

    @Value("${zkUserName:root}")
    private String zkUserName;

    @Value("${zkPassword:root}")
    private String zkPassword;

    @Bean
    public HBaseComponent hBaseComponent() throws IOException {
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set(HConstants.ZOOKEEPER_QUORUM, zkAddress);
        config.set(HConstants.USERNAME, zkUserName);
        config.set(HConstants.PASSWORD, zkPassword);
        config.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181");
        config.set(HConstants.HBASE_RPC_TIMEOUT_KEY, "1800000");
        config.set(HConstants.HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, "1800000");
        Connection conn = ConnectionFactory.createConnection(config);
        return new HBaseComponent(conn);
    }


    /**
     * 自定义线程池。从 HBase，redis 等地方获取请求并行处理。
     * 目的是尽可能多地处理请求：
     * corePoolSize: 30,  由于是 io 请求，所以线程多一点
     * maximumPoolSize: 15, 当队列满时，新来一个任务，就建一个 Thread 来加快处理。
     * keepAliveTime: 1min, 如果1分钟都没有新请求进来，则空闲的线程终止，回归的 corePoolSize 的线程数量
     * workQueue: 队列大小设置 2万，我们为了支撑 2000 以上的 qps，每个请求需要3个以上的并发io任务（6000）,再宽松点就设置了 2万.
     * ThreadFactory: 线程创建的工厂，设置个线程名
     * rejectedExecutorHandler: 拒绝策略，当线程数量达到 maximumPoolSize，并且队列仍满了。
     *                          则触发 rejectedExecutorHandler.
     *                          我们设置 callerRunsPolicy，由请求的线程去执行
     */
    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                40, 45,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(20000),
                new ThreadFactory() {
                    private final AtomicInteger id = new AtomicInteger(0);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("appOperation-io-pool-" + id.getAndIncrement());
                        return t;
                    }
                }, new CallerRunsPolicy2());
    }

    public static class CallerRunsPolicy2 implements RejectedExecutionHandler {
        public CallerRunsPolicy2() { }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.out.println("--------------rejectedExecution---------------");
            if (!e.isShutdown()) {
                r.run();
            }
        }
    }

    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}