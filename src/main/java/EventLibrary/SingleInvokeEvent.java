package EventLibrary;

public abstract class SingleInvokeEvent extends BasicEventOfferer {

    private boolean removeListeners = false;
    private boolean eventFired = false;
    
    public void removeListenersAfter(){
        this.removeListeners = true;
    }
    
    public void keepListenersAfter(){
        this.removeListeners = false;
    }
    
    public void alowAnotherFire(){
        this.eventFired = false;
    }
    
    @Override
    protected void fireEvent(Enum eventMarker, EventData data) {
        
        if(!eventFired){
            super.fireEvent(eventMarker, data);
            eventFired = true;
        }
        
        if(removeListeners){
            super.removeListeners(eventMarker);
        }
    }
    
    /* *********************** */
    /* STATIC MEMBERS          */
    /* *********************** */
    
    public static void injectTo(Object obj){
        injection(new SingleInvokeEvent(){ }, obj);
    }
    
    public static EventBundle createBundle(){
        return createEventTriggerBundle(new SingleInvokeEvent() { });
    }
}
