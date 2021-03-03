package com.example.appoperation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RocketMQ 消息实验
 * @author zhangxiaofan 2021/01/26-14:00
 */
public class RocketMQTest {
    private static final Logger log = LoggerFactory.getLogger(RocketMQTest.class);

    private final String produceMqGroupName = "producer-group";
    private final String consumeMqGroupName = "consumer-groups";
    private final String rocketNameServerAddr = "10.0.0.1:9876;10.0.0.2:9876";
    private final String rocketAccessKey = "accessKey";
    private final String rocketSecretKey = "secretKey";

    private final String topic = "my-mq-topic";

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @Ignore
    public void produceMsg() throws JsonProcessingException, MQClientException, InterruptedException {
        AclClientRPCHook aclRpcHook = new AclClientRPCHook(new SessionCredentials(rocketAccessKey, rocketSecretKey));
        TransactionMQProducer producer = new TransactionMQProducer(produceMqGroupName, aclRpcHook);
        producer.setNamesrvAddr(rocketNameServerAddr);
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                try {
                    // do something
                    log.info("execute local transaction");
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception e) {
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                try {
                    log.info("check local transaction");
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception e) {
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
        });
        producer.start();
        log.info("producer started");

        Msg m = new Msg();
        m.setContent("Hello World");
        byte[] msgBytes = mapper.writeValueAsBytes(m);

        Message msg = new Message(topic, "helloTag",msgBytes);
        producer.sendMessageInTransaction(msg, null);

        TimeUnit.MINUTES.sleep(30);
        producer.shutdown();
    }

    @Test
    @Ignore
    public void consumeMsg() throws MQClientException, InterruptedException {
        AclClientRPCHook aclRpcHook = new AclClientRPCHook(new SessionCredentials(rocketAccessKey, rocketSecretKey));
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("", consumeMqGroupName, aclRpcHook);
        // Specify name server addresses.
        consumer.setNamesrvAddr(rocketNameServerAddr);
        // Subscribe one more more topics to consume.
        consumer.subscribe(topic, "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg : msgs) {
                        byte[] body = msg.getBody();
                        Msg m = mapper.readValue(body, Msg.class);
                        log.info("msg tag:{}, content: {}", msg.getTags(), m.getContent());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();
        log.info("consumer started");

        TimeUnit.MINUTES.sleep(30);
        consumer.shutdown();
    }

    public static class Msg {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}