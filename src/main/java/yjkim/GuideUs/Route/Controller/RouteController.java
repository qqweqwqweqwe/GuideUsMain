package yjkim.GuideUs.Route.Controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yjkim.GuideUs.Kafka.Service.KafkaService;
import yjkim.GuideUs.Redis.Service.RedisService;
import yjkim.GuideUs.Route.DTO.RouteCalcualteRequest;
import yjkim.GuideUs.Route.DTO.RouteCalculateResponse;
import yjkim.GuideUs.Route.Service.RouteService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final KafkaService kafkaService;
    private final RedisService redisService;




    @PostMapping("/send")
    @ResponseBody
    public String sendRoute(@RequestBody RouteCalcualteRequest routeCalcualteRequest){
        String requestId = UUID.randomUUID().toString();


        // 브로커에 삽입만
        kafkaService.send(KafkaService.ROUTE_CALCULATE_KAFKA_TOPIC, requestId, routeCalcualteRequest);
        // todo 이거 로컬 서버로 바꿔라 환경변수로

        return requestId; // 303 Redirect
    }


    @GetMapping("/calculate/{requestId}")
    public ResponseEntity<?> calculateShortestRoute(
            @PathVariable String requestId
    ){
        try {


            String value = redisService.get(requestId);
            String placeName = redisService.get(requestId + "name");
            int retryCount = 0;
            int maxRetries = 10; // 최대 재시도 횟수
            int waitTime = 200; // 초기 대기 시간 (밀리초)
            while ((value == null || placeName == null) && retryCount < maxRetries) {
                value = redisService.get(requestId);
                placeName = redisService.get(requestId + "name");

                if (value != null && placeName != null) {
                    break; // 데이터가 들어오면 루프 탈출
                }

                retryCount++;

                Thread.sleep(waitTime);

                waitTime *= 2; // 🔥 다음 재시도에서 대기 시간 2배 증가
            }

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("key", requestId);
            responseBody.put("value", value);
            responseBody.put("placeName", placeName);
            return ResponseEntity.ok(responseBody);
        }catch (Exception e){
            return ResponseEntity.ok()
                    .body("hi");

        }
    }


}