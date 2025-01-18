package yjkim.GuideUs.Route.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculateTimeKakaoResponse {
    private String trans_id;
    private Route[] routes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Route{
        private int result_code;
        private String result_msg;
        private Summary summary;
        private Section[] sections;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary{
        private Origin origin;
        private Destination destination;
        private Waypoint[] waypoints;
        private String priority;
        private Bound bound;
        private Fare fare;
        private int distance;
        private int duration;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Origin{
        private String name;
        private double x;
        private double y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Destination{
        private String name;
        private double x;
        private double y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Waypoint{
        private String name;
        private double x;
        private double y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fare{
        private int taxi;
        private int toll;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bound{
        private double min_x;
        private double min_y;
        private double max_x;
        private double max_y;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Road{
        private String name;
        private int distance;
        private int duration;
        private double traffic_speed;
        private int traffic_state;
        private double[] vertexes;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Guide{
        private String name;
        private double x;
        private double y;
        private int distance;
        private int duration;
        private int type;
        private String guidance;
        private int road_index;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Section{
        private int distance;
        private int duration;
        private Bound bound;
        private Road[] roads;
        private Guide[] guides;
    }

}
