package EventLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BasicEventOfferer extends Injector implements EventOfferer {
    
    private HashMap<Enum, List<EventOffererHandler>> markerListners;
    
    private List<EventOffererHandler> getListOfListeners(Enum eventMarker){
        
        List<EventOffererHandler> listeners = this.markerListners.get(eventMarker);
        
        if(listeners == null){
            listeners = new ArrayList<>();
            this.markerListners.put(eventMarker, listeners);
        }
        
        return listeners;
    }
    
    
    public BasicEventOfferer(){        
        this.markerListners = new HashMap<>();
    }
    
    @Override
    public void addListener(Enum eventMarker, EventOffererHandler listener) {
        getListOfListeners(eventMarker).add(listener);                
    }
    
    @Override
    public void removeListener(Enum eventMarker, EventOffererHandler listener) {
        getListOfListeners(eventMarker).remove(listener);
    }
    
    protected void fireEvent(Enum eventMarker, EventData data){
        getListOfListeners(eventMarker).stream().forEach((listener) -> ((EventOffererHandler)listener).eventFired(data));
    }
    
    protected void removeListeners(Enum eventMarker){
        getListOfListeners(eventMarker).clear();
    }
    
    protected void removeListeners(){
        this.markerListners.clear();
    }
    
    /* *********************** */
    /* STATIC MEMBERS          */
    /* *********************** */
    
    public static void injectTo(Object obj){
        injection(new BasicEventOfferer(){ }, obj);
    }
    
    public static EventBundle createBundle(){        
        return createEventTriggerBundle(new BasicEventOfferer() { });
    }
}
