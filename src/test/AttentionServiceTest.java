package model.services;

import model.SimulationModel;
import model.domain.Incident;
import model.domain.SimulationEvent;
import model.domain.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttentionServiceTest {

    private SimulationModel model;
    private AttentionService attentionService;

    @BeforeEach
    public void setUp() {
        model = new SimulationModel();
        attentionService = new AttentionService(model);
    }

    @Test
    public void testStartAttentionSuccess() {
        Incident incident = new Incident("INC-100", "INCENDIO", 4, "Nodo_A");
        Vehicle vehicle = new Vehicle("V-1", "Nodo_A");

        boolean started = attentionService.startAttention(incident, vehicle);

        assertTrue(started);
        assertFalse(vehicle.isAvailable());
        assertEquals("EN_PROCESO", incident.getStatus());
        assertEquals(incident, vehicle.getCurrentIncident());
        assertTrue(model.hasPendingEvents());
    }

    @Test
    public void testStartAttentionFailsIfVehicleUnavailable() {
        Incident incident = new Incident("INC-101", "ROBO", 2, "Nodo_B");
        Vehicle vehicle = new Vehicle("V-2", "Nodo_B");
        vehicle.setAvailable(false); // Vehículo ocupado

        boolean started = attentionService.startAttention(incident, vehicle);

        assertFalse(started);
        assertEquals("PENDIENTE", incident.getStatus());
    }

    @Test
    public void testCompleteAttentionFreesVehicleAndResolvesIncident() {
        Incident incident = new Incident("INC-102", "ACCIDENTE", 5, "Nodo_C");
        Vehicle vehicle = new Vehicle("V-3", "Nodo_C");

        attentionService.startAttention(incident, vehicle);
        boolean completed = attentionService.completeAttention(incident, vehicle);

        assertTrue(completed);
        assertTrue(vehicle.isAvailable());
        assertEquals("RESUELTO", incident.getStatus());
        assertNull(vehicle.getCurrentIncident());

        assertEquals(3, model.getEventQueueSize());
    }
}