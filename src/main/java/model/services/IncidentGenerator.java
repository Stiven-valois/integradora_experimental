package model.services;

import model.SimulationModel;
import model.domain.Incident;
import model.domain.Road;
import model.domain.Vehicle;

import java.util.List;
import java.util.Random;

public class IncidentGenerator {

    private final SimulationModel model;
    private final AccidentRuleEngine ruleEngine;
    private final Random random;
    private int incidentCounter;

    public IncidentGenerator(SimulationModel model) {
        this.model = model;
        this.ruleEngine = new AccidentRuleEngine();
        this.random = new Random();
        this.incidentCounter = 1;
    }

    public Incident generateRandomIncident(String nodeId) {
        boolean isRobbery = random.nextBoolean();
        String type = isRobbery ? "ROBO" : "INCENDIO";
        int severity = random.nextInt(5) + 1;

        String incidentId = "INC-" + (incidentCounter++);
        Incident incident = new Incident(incidentId, type, severity, nodeId);

        model.enqueueIncident(incident);
        return incident;
    }

    public Incident checkAndGenerateAccident(Road road, List<Vehicle> vehiclesInNode, String nodeId) {
        boolean hasCongestion = ruleEngine.evaluateCongestionRule(road);
        boolean hasIntersectionRisk = ruleEngine.evaluateVehicleIntersectionRule(vehiclesInNode);
        boolean hasRoadRisk = ruleEngine.evaluateRoadRiskRule(road);

        if (hasCongestion || hasIntersectionRisk || hasRoadRisk) {
            String incidentId = "INC-" + (incidentCounter++);
            int severity = random.nextInt(3) + 3;
            Incident accident = new Incident(incidentId, "ACCIDENTE", severity, nodeId);

            model.enqueueIncident(accident);
            return accident;
        }

        return null;
    }
}