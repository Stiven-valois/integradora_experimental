package model.services;

import model.domain.Road;
import model.domain.Vehicle;

import java.util.List;

public class AccidentRuleEngine {

    private static final double CONGESTION_THRESHOLD = 0.8; // 80% de congestión


    public boolean evaluateCongestionRule(Road road) {
        if (road == null) return false;
        return road.getCongestionFactor() >= CONGESTION_THRESHOLD;
    }


    public boolean evaluateVehicleIntersectionRule(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.size() < 2) return false;

        for (int i = 0; i < vehicles.size(); i++) {
            for (int j = i + 1; j < vehicles.size(); j++) {
                Vehicle v1 = vehicles.get(i);
                Vehicle v2 = vehicles.get(j);
                if (v1.getCurrentNodeId() != null &&
                        v1.getCurrentNodeId().equals(v2.getCurrentNodeId())) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean evaluateRoadRiskRule(Road road) {
        if (road == null) return false;
        return road.isHighRiskZone() && road.getCongestionFactor() > 0.5;
    }
}