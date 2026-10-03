package model.domain;

public class SimulationEvent {

    public enum EventType {
        INCIDENT_GENERATED,
        VEHICLE_ASSIGNED,
        VEHICLE_DISPATCHED,
        INCIDENT_RESOLVED,
        ROAD_BLOCKED,
        ROAD_UNBLOCKED
    }

    private final EventType type;
    private final Object target;
    private final long timestamp;

    public SimulationEvent(EventType type, Object target) {
        this.type = type;
        this.target = target;
        this.timestamp = System.currentTimeMillis();
    }

    public EventType getType() {
        return type;
    }

    public Object getTarget() {
        return target;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SimulationEvent{" +
                "type=" + type +
                ", target=" + target +
                ", timestamp=" + timestamp +
                '}';
    }
}