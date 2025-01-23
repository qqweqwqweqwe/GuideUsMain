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
    public String sendRoute(@RequestBody RouteCalcualteRequest routeCalcualteRequest){
        String requestId = UUID.randomUUID().toString();


        // 브로커에 삽입만
        kafkaService.send(KafkaService.ROUTE_CALCULATE_KAFKA_TOPIC, requestId, routeCalcualteRequest);
        String message = "Route calculated successfully!";
        // todo 이거 로컬 서버로 바꿔라 환경변수로
        String redirectUrl = "http://localhost:8080/maps/result/" + requestId;

        return "redirect:/" + "maps/result/" + requestId; // 303 Redirect
    }


    @GetMapping("/calculate/{requestId}")
    public ResponseEntity<?> calculateShortestRoute(
            @PathVariable String requestId
    ){
        String value = redisService.get(requestId);
        String placeName = redisService.get(requestId + "name");
        String key = requestId;
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("key",key);
        responseBody.put("value",value);
        responseBody.put("placeName",placeName);
        return ResponseEntity.ok(responseBody);
    }


}