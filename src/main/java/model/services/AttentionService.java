package model.services;

import model.SimulationModel;
import model.domain.Incident;
import model.domain.SimulationEvent;
import model.domain.Vehicle;

public class AttentionService {

    private final SimulationModel model;

    public AttentionService(SimulationModel model) {
        this.model = model;
    }


    public boolean startAttention(Incident incident, Vehicle vehicle) {
        if (incident == null || vehicle == null || !vehicle.isAvailable()) {
            return false;
        }

        vehicle.setAvailable(false);
        vehicle.setCurrentIncident(incident);
        incident.setStatus("EN_PROCESO");

        model.recordEvent(new SimulationEvent(SimulationEvent.EventType.VEHICLE_ASSIGNED, vehicle));
        model.recordEvent(new SimulationEvent(SimulationEvent.EventType.VEHICLE_DISPATCHED, incident));

        return true;
    }


    public boolean completeAttention(Incident incident, Vehicle vehicle) {
        if (incident == null || vehicle == null) {
            return false;
        }

        incident.setStatus("RESUELTO");
        vehicle.setAvailable(true);
        vehicle.setCurrentIncident(null);

        model.recordEvent(new SimulationEvent(SimulationEvent.EventType.INCIDENT_RESOLVED, incident));

        return true;
    }
}