package yjkim.GuideUs.Route.Service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import yjkim.GuideUs.Kafka.Service.KafkaService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RouteService {


    // 토픽은 route-calculate

    private final KafkaService kafkaService;

    public void sendRouteDataToKafka(String topic, String key, String value){
        KafkaProducer<String, String> kafkaProducer = kafkaService.createProducer();
        kafkaProducer.send(new ProducerRecord<>(topic,key,value));
        kafkaProducer.close();
    }

    public void calculateShortestRouteFromKafka(){
        KafkaConsumer<String, String> kafkaConsumer = kafkaService.createConsumer();


    }

    public String getRouteFromKafka(String key, KafkaConsumer<String,String> kafkaConsumer){

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            if (record.key().equals(key)) {
                System.out.printf("Found key: %s, value: %s%n", record.key(), record.value());
                break;
            }
        }


    }

    public calculateShortestRoute(){




    }

}
