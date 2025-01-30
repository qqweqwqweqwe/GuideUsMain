package yjkim.GuideUs.Kafka.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import yjkim.GuideUs.Redis.Service.RedisService;
import yjkim.GuideUs.Route.DTO.GetRouteNaverRequest;
import yjkim.GuideUs.Route.DTO.GetRouteNaverResponse;
import yjkim.GuideUs.Route.DTO.RouteCalcualteRequest;
import yjkim.GuideUs.Route.Service.RouteService;

import javax.sound.sampled.Port;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String,RouteCalcualteRequest> kafkaTemplate;
    private final RouteService routeService;
    private final RedisService redisService;
    public static final String ROUTE_CALCULATE_KAFKA_TOPIC = "guide-us-route";
    private Process zookeeperProcess;
    private Process kafkaProcess;




    public void send(String topic, String key, RouteCalcualteRequest routeCalcualteRequest){
        kafkaTemplate.send(topic,key,routeCalcualteRequest);
        return;
    }

    @KafkaListener(topics = ROUTE_CALCULATE_KAFKA_TOPIC )
    public void consume(ConsumerRecord<String, RouteCalcualteRequest> record){

        String key = record.key();
        System.out.println("key : " + key);
        RouteCalcualteRequest routeCalcualteRequest = record.value();
        String[] dep = routeCalcualteRequest.getDep();
        String[] des = routeCalcualteRequest.getDes();
        String[][] trans = routeCalcualteRequest.getTrans();

        String temp = "";
        System.out.println(dep[0]);
        System.out.println(des[0]);

        try {
            trans = this.routeService.calculateMinimumTimeRoute(dep, des, trans);
            // trans에 들어가는게 순서
            //
            for(String[] tran : trans){
                temp = temp + tran[1]+","+tran[2] + "|";
            }
            GetRouteNaverRequest getRouteNaverRequest = new GetRouteNaverRequest(
                    dep[1]+","+dep[2],
                    des[1]+","+des[2],
                    temp
            );

            GetRouteNaverResponse getRouteNaverResponse = this.routeService.getRoute(getRouteNaverRequest);

            List<List<Double>> pathValue = new ArrayList<>();
            List<String[]> placeInfo = new ArrayList<>();
            List<Double> startLocation = (getRouteNaverResponse.getRoute().getTraoptimal().get(0).getSummary().getStart().getLocation());
            List<Double> goalLocation = (getRouteNaverResponse.getRoute().getTraoptimal().get(0).getSummary().getGoal().getLocation());
            List<List<Double>> paths = getRouteNaverResponse.getRoute().getTraoptimal().get(0).getPath();
            pathValue.add(startLocation);
            for(List<Double> path : paths){
                pathValue.add(path);
            }
            pathValue.add(goalLocation);

            placeInfo.add(dep);
            for(String[] tran : trans){
                placeInfo.add(tran);
            }
            placeInfo.add(des);

            ObjectMapper objectMapper = new ObjectMapper();
            redisService.save(key,objectMapper.writeValueAsString(pathValue));
            redisService.save(key+"name", objectMapper.writeValueAsString(placeInfo));


        }
        catch (Exception e){
            System.out.println("E : " + e);
        }


    }
}
