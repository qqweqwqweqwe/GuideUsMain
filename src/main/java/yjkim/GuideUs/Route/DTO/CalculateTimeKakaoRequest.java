package yjkim.GuideUs.Route.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CalculateTimeKakaoRequest {

    private String priority;
    private String[] avoid;
    private int roadevent;
    private boolean alternatives;
    private boolean road_details;
    private int car_type;
    private String car_fuel;
    private boolean car_hipass;
    private boolean summary;
    private Origin origin;
    private Destination destination;
    private Waypoint[] waypoints;

    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class Origin{
        private String name;
        private String x;
        private String y;
    }

    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class Destination{
        private String name;
        private String x;
        private String y;
    }
    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class Waypoint{
        private String name;
        private String x;
        private String y;

    }


}
