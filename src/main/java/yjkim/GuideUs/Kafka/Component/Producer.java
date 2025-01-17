package yjkim.GuideUs.Kafka.Component;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


public class Producer {


    public KafkaProducer<String,String> Producer(){
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "localhost:9092"); // Kafka 브로커 주소
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(configs);

    }

}
