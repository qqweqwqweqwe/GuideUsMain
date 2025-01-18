package yjkim.GuideUs.Kafka.Service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import yjkim.GuideUs.Route.DTO.RouteCalcualteRequest;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String,RouteCalcualteRequest> kafkaTemplate;
    public static final String ROUTE_CALCULATE_KAFKA_TOPIC = "route-calculate";


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
    public void send(String topic, String key, RouteCalcualteRequest routeCalcualteRequest){
        kafkaTemplate.send(topic,key,routeCalcualteRequest);
        return;
    }

    @KafkaListener(topics = ROUTE_CALCULATE_KAFKA_TOPIC )
    public void consume(RouteCalcualteRequest routeCalcualteRequest){

        // 리스너에서 계산하고 해당 결과 redis에 저장
        //
    }
}
