package yjkim.GuideUs.Route.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GetRouteNaverRequest {

    private final String start;   //12312312.13,123123.123
    private final String goal;
    private final String waypoints; // 12312312,13123.123|123123,123
    private String option = "traoptimal";
    private int cartype = 1;
    private String fueltype = "gasoline";
    private  double mileage = 14;
    private String lang = "ko";


}
