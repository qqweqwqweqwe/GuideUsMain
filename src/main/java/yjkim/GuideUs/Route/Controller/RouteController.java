package yjkim.GuideUs.Route.Controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yjkim.GuideUs.Route.DTO.RouteCalculateResponse;
import yjkim.GuideUs.Route.Service.RouteService;

import java.util.Map;

@Controller
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    private final String ROUTE_CALCULATE_KAFKA_TOPIC = "route-calculate";



    // 얘의 역할은 그냥 넣어주는 걸로 하자
    @PostMapping("/send")
    public ResponseEntity<String> sendRoute(@RequestBody RouteCalculateResponse routeCalculateResponse){
        String[][] trans = routeCalculateResponse.getTrans();
        String[] dep = routeCalculateResponse.getDep();
        String[] des = routeCalculateResponse.getDes();
        String jsonValue;
        String key; // uuid generate;

        long id;
        // redis에id랑 key 저장하고
        routeService.sendRouteDataToKafka(this.ROUTE_CALCULATE_KAFKA_TOPIC, key,jsonValue);
        String message = "Route calculated successfully!";
        // 겟 요청 param에 id 담아서 보내

        // 결과 반환
        return ResponseEntity.ok("{\"message\": \"" + message + "\"}");
    }


    @GetMapping("/calculate/{id}")
    public ResponseEntity<String> calculateShortestRoute(
            @PathVariable long id
    ){

        String key;
        // 레디스에서 id 기반으로

        routeService.getRouteFromKafka(this.ROUTE_CALCULATE_KAFKA_TOPIC, )
    }


}
