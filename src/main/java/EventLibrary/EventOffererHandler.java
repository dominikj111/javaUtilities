package EventLibrary;

import java.util.EventListener;

@FunctionalInterface
public interface EventOffererHandler extends EventListener {
    public void eventFired(EventData data);
}
