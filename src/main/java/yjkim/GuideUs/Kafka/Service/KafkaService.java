package yjkim.GuideUs.Kafka.Service;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaService {


    public KafkaProducer<String, String> createProducer(){
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "localhost:9092"); // Kafka 브로커 주소
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(configs);
    }

    public KafkaConsumer<String, String> createConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "json-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<>(props);

    }
}
