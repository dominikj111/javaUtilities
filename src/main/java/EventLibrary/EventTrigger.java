package EventLibrary;

public interface EventTrigger {
    public void fireEvent(Enum eventMarker, EventData data);
}
