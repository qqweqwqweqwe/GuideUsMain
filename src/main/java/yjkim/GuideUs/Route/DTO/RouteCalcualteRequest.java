package yjkim.GuideUs.Route.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RouteCalcualteRequest {

    // todo 나중에 카카오 장소 정보로 리팩할것
    private String[] dep;
    private String[] des;
    private String[][] trans;

}
