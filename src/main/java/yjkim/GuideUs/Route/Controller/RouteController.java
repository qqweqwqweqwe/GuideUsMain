package yjkim.GuideUs.Route.Controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yjkim.GuideUs.Kafka.Service.KafkaService;
import yjkim.GuideUs.Route.DTO.RouteCalcualteRequest;
import yjkim.GuideUs.Route.DTO.RouteCalculateResponse;
import yjkim.GuideUs.Route.Service.RouteService;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final KafkaService kafkaService;




    // 얘의 역할은 그냥 넣어주는 걸로 하자
    @PostMapping("/send")
    public ResponseEntity<?> sendRoute(@RequestBody RouteCalcualteRequest routeCalcualteRequest){
        String requestId = UUID.randomUUID().toString();



        kafkaService.send(KafkaService.ROUTE_CALCULATE_KAFKA_TOPIC, requestId, routeCalcualteRequest);
        String message = "Route calculated successfully!";
        // 겟 요청 param에 id 담아서 보내

        // 결과 반환
        return ResponseEntity.ok(HttpStatus.OK);
    }


//    @GetMapping("/calculate/{requestId}")
//    public ResponseEntity<String> calculateShortestRoute(
//            @PathVariable String requestId
//    ){
//
//
//    }


}