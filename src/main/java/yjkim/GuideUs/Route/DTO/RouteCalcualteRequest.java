package yjkim.GuideUs.Route.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RouteCalcualteRequest {

    // todo 나중에 카카오 장소 정보로 리팩할것
    private String[] dep;
    private String[] des;

    // 기본 생성자로 생성할 떄 null 방지
    private String[][] trans= new String[][]{};

}
