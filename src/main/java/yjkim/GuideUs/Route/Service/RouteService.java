package yjkim.GuideUs.Route.Service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import yjkim.GuideUs.Kafka.Service.KafkaService;
import yjkim.GuideUs.Route.DTO.CalculateTimeKakaoRequest;
import yjkim.GuideUs.Route.DTO.CalculateTimeKakaoResponse;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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


    public String[][] calculateMinimumTimeRoute(String[] dep, String[] des, String[][] trans) {


        int answer = 10000000;

        String[][] temp = new String[trans.length][3];
        List<List<String[]>> permutatedTranList = getPermutation(List.of(trans));
        for (int i = 0; i < permutatedTranList.size(); i++) {
            int time = calculateTime(dep,des, permutatedTranList.get(i).toArray(new String[0][]));
            if(answer>time){
                answer = time;
                temp = permutatedTranList.get(i).toArray(new String[0][]);
            }
        }

        return temp;
    }


    public int calculateTime(String[] dep, String[] des, String[][] trans){

        String depName = dep[0];
        String depLon = dep[1]; // 시작 경도
        String depLat = dep[2]; // 시작 위도

        String desName = des[0];
        String desLon = des[1]; // 목적 경도
        String desLat = des[2]; // 목적 위도

        CalculateTimeKakaoRequest calculateTimeKakaoRequest = new CalculateTimeKakaoRequest();
        calculateTimeKakaoRequest.setOrigin(new CalculateTimeKakaoRequest.Origin(depName,depLon,depLat));
        calculateTimeKakaoRequest.setDestination(new CalculateTimeKakaoRequest.Destination(desName,desLon,desLat));
        CalculateTimeKakaoRequest.Waypoint[] waypoints = new CalculateTimeKakaoRequest.Waypoint[trans.length];
        for(int i = 0; i<trans.length; i++){
            waypoints[i] = new CalculateTimeKakaoRequest.Waypoint(trans[i][0],trans[i][1],trans[i][0]);
        }
        calculateTimeKakaoRequest.setWaypoints(waypoints);

        CalculateTimeKakaoResponse calculateTimeKakaoResponse = WebClient.create("카카오 url")
                .post()
                .bodyValue(calculateTimeKakaoRequest)
                .retrieve()
                .bodyToMono(CalculateTimeKakaoResponse.class)
                .block();

        return calculateTimeKakaoResponse.getRoutes()[0].getSummary().getDuration();
    }

    public static <T> List<List<T>> getPermutation(List<T> input) {
        List<List<T>> result = new ArrayList<>();
        generate(input, new boolean[input.size()], new ArrayList<>(), result);
        return result;
    }

    // 내부에서 순열 생성
    private static <T> void generate(List<T> input, boolean[] visited, List<T> current, List<List<T>> result) {
        if (current.size() == input.size()) {
            result.add(new ArrayList<>(current)); // 순열 완성 시 결과 추가
            return;
        }

        for (int i = 0; i < input.size(); i++) {
            if (!visited[i]) {
                visited[i] = true; // 방문 처리
                current.add(input.get(i)); // 현재 요소 추가
                generate(input, visited, current, result); // 다음 단계로 진행
                current.remove(current.size() - 1); // 백트래킹
                visited[i] = false; // 방문 해제
            }
        }
    }

}
