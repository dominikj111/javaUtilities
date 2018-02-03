package EventLibrary;

final public class EventBundle {
    
    public EventTrigger trigger;
    public EventOfferer offerer;
            
    public EventBundle(EventOfferer offerer, EventTrigger trigger){
        this.offerer = offerer;
        this.trigger = trigger;
    }
}
