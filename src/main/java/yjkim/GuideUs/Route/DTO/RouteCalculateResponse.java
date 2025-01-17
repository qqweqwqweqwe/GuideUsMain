package yjkim.GuideUs.Route.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class RouteCalculateResponse {


    private String[][] trans;
    private String[] dep;
    private String[] des;
}
