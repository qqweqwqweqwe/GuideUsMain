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


    // 어플리케이션 실행하면 자동으로 주키퍼랑 카프카 실행하도록
    // 그리고 dummy-data 보냄으로써 카프카랑 연결까지 하도록
    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStart(){
        startZookeeper();
        startKafka();
    }


    public void startZookeeper() {
        try {
            String command = "bin\\windows\\zookeeper-server-start.bat config\\zookeeper.properties";
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/k", command);
            processBuilder.directory(new File("C:\\kafka_2.13-3.9.0")); // Kafka 설치 경로
            zookeeperProcess = processBuilder.start();
            System.out.println("Zookeeper started!");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (zookeeperProcess != null && zookeeperProcess.isAlive()) {
                    System.out.println("Shutting down Zookeeper...");
                    zookeeperProcess.destroy();

                }

            }));

            System.out.println("Zookeeper started. Press Ctrl+C to stop the server.");
        }
        catch (Exception e){
            System.out.println("Zookeeper error  : " + e);
        }
    }

    private void startKafka() {
        try {
            String command = "bin\\windows\\kafka-server-start.bat config\\server.properties";
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            processBuilder.directory(new File("C:\\kafka_2.13-3.9.0")); // Kafka 설치 경로
            kafkaProcess = processBuilder.start();
            System.out.println("Kafka started!");
            // 서버 종료시 자동으로 카프카 종료되도록

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (kafkaProcess != null && kafkaProcess.isAlive()) {
                    System.out.println("Shutting down kafka...");
                    kafkaProcess.destroy();
                }

            }));

        }

        catch (Exception e){
            System.out.println("Kafka error  : " + e);
        }
    }
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
