package EventLibrary;

public interface EventOfferer {
    public void addListener(Enum eventMarker, EventOffererHandler listener);
    public void removeListener(Enum eventMarker, EventOffererHandler listener);
}
