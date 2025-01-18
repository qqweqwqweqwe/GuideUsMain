package yjkim.GuideUs.Route.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteCalcualteRequest {

    // todo 나중에 카카오 장소 정보로 리팩할것
    private String[] dep;
    private String[] des;
    private String[][] trans;

}
