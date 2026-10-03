package model.services;

import model.SimulationModel;
import model.domain.Incident;
import model.domain.Road;
import model.domain.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentGeneratorTest {

    private SimulationModel model;
    private IncidentGenerator generator;

    @BeforeEach
    public void setUp() {
        model = new SimulationModel();
        generator = new IncidentGenerator(model);
    }

    @Test
    public void testGenerateRandomIncidentEnqueuesInModel() {
        Incident incident = generator.generateRandomIncident("Nodo_A");

        assertNotNull(incident);
        assertEquals(1, model.getDispatchQueueSize());
        assertEquals(1, model.getEventQueueSize());
        assertTrue(incident.getType().equals("ROBO") || incident.getType().equals("INCENDIO"));
    }

    @Test
    public void testCheckAndGenerateAccidentTriggersOnCongestion() {
        Road highCongestionRoad = new Road("R1", 0.9, false); // 90% congestión

        Incident accident = generator.checkAndGenerateAccident(highCongestionRoad, Collections.emptyList(), "Nodo_B");

        assertNotNull(accident);
        assertEquals("ACCIDENTE", accident.getType());
        assertEquals(1, model.getDispatchQueueSize());
    }

    @Test
    public void testCheckAndGenerateAccidentTriggersOnVehicleIntersection() {
        Road normalRoad = new Road("R2", 0.2, false);
        Vehicle v1 = new Vehicle("V1", "Nodo_C");
        Vehicle v2 = new Vehicle("V2", "Nodo_C");

        Incident accident = generator.checkAndGenerateAccident(normalRoad, Arrays.asList(v1, v2), "Nodo_C");

        assertNotNull(accident);
        assertEquals("ACCIDENTE", accident.getType());
    }
}