package mapleglory.server.event;

public enum EventType {
    CM_VICTORIA,
    CM_LUDIBRIUM,
    CM_LEAFRE,
    CM_ARIANT,
    CM_ELEVATOR,
    CM_SUBWAY,
    CM_AIRPORT,
    PQ_MU_LUNG_DOJO,
    PQ_BALROG,
    PQ_N_BALROG;

    public static EventType getByName(String name) {
        for (EventType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
