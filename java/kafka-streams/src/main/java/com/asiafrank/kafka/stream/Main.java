package com.asiafrank.kafka.stream;

public class Main {
    public static void main(String[] args) {
        String bootstrapServers = "10.1.53.20:9092";
        String topic = "streams-plaintext-input";

        WordCountProducer producer = new WordCountProducer();
        producer.produce(bootstrapServers, topic);

        WordCountStreamConsumer consumer = new WordCountStreamConsumer();
        consumer.consume(bootstrapServers, topic);
    }
}
