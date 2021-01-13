package com.example.appoperation;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.time.Duration;

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
}