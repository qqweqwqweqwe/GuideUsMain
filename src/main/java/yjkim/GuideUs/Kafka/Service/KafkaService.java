package yjkim.GuideUs.Kafka.Service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import yjkim.GuideUs.Route.DTO.RouteCalcualteRequest;
import yjkim.GuideUs.Route.Service.RouteService;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String,RouteCalcualteRequest> kafkaTemplate;
    private final RouteService routeService;
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
    public void consume(ConsumerRecord<String, RouteCalcualteRequest> record){
        String key = record.key();
        RouteCalcualteRequest routeCalcualteRequest = record.value();
        String[] dep = routeCalcualteRequest.getDep();
        String[] des = routeCalcualteRequest.getDes();
        String[][] trans = routeCalcualteRequest.getTrans();
        trans = this.routeService.calculateMinimumTimeRoute(dep,des,trans);

        // 레디스에 저장


    }
}
