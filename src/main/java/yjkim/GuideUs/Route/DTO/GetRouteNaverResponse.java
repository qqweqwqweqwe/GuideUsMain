package yjkim.GuideUs.Route.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetRouteNaverResponse {

    private int code;
    private String message;
    private String currentDateTime;
    private Route route;

    @Getter
    @Setter
    public class Route {
        private List<RouteOption> trafast;        // 실시간 빠른 길
        private List<RouteOption> tracomfort;    // 실시간 편한 길
        private List<RouteOption> traoptimal;    // 실시간 최적 길
        private List<RouteOption> traavoidtoll;  // 무료 우선
        private List<RouteOption> traavoidcaronly; // 자동차 전용 도로 회피 우선
    }

    @Getter
    @Setter
    public static class RouteOption {
        private Summary summary;
        private List<List<Double>> path;
        private List<Section> section;
        private List<Guide> guide;
    }

    @Getter
    @Setter
    public static class Summary {
        private Start start;
        private Goal goal;
        private int distance;
        private int duration;
        private String departureTime;
        private List<List<Double>> bbox;
        private int tollFare;
        private int taxiFare;
        private int fuelPrice;
    }

    @Getter
    @Setter
    public static class Start {
        private List<Double> location;
    }

    @Getter
    @Setter
    public static class Goal {
        private List<Double> location;
        private int dir;
    }

    @Getter
    @Setter
    public static class Section {
        private int pointIndex;
        private int pointCount;
        private int distance;
        private String name;
        private int congestion;
        private int speed;
    }

    @Getter
    @Setter
    public static class Guide {
        private int pointIndex;
        private int type;
        private String instructions;
        private int distance;
        private long duration;
    }
}
