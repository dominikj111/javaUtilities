package DeferedLibrary;

import EventLibrary.EventBundle;
import EventLibrary.EventOffererHandler;
import EventLibrary.SingleInvokeEvent;
import Shorts.ShortThread;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleDefered {

    private enum ResultState  { Resolved }
    public  enum ObjectStates { Resolved, Rejected, Waiting }
    
    private EventBundle eventBundle;
    private ObjectStates objectState;
    private Object preserved;
    private ShortThread deferedThread;
                
    public SimpleDefered() {
        this.eventBundle = SingleInvokeEvent.createBundle();
        this.objectState = ObjectStates.Waiting;
        this.preserved = null;
        this.deferedThread = null;
    }    
    
    public void resolveIt(){
        
        if(this.objectState != ObjectStates.Waiting) { return; }
        
        this.objectState = ObjectStates.Resolved;
        this.eventBundle.trigger.fireEvent(ResultState.Resolved, null);
    }
    
    public void rejectIt(){
        
        if(this.objectState != ObjectStates.Waiting) { return; }
        
        this.objectState = ObjectStates.Rejected;
        this.eventBundle.trigger.fireEvent(ResultState.Resolved, null);
    }        
    
    public <T> T getPreserved(){
        return (T)this.preserved;
    }
    
    public <T> void preserve(T object){
        this.preserved = object;
    }
    
    public ObjectStates getObjectState(){
        return this.objectState;
    }
    
    public void join(){
        try {
            this.deferedThread.join();
        } catch (InterruptedException ex) {
            System.out.println("Interrupted exception in defered thread when join call");
        }
    }
    
    public SimpleDefered then(DeferedTask task){
        
        SimpleDefered defer = new SimpleDefered();        
        defer.preserve(this.getPreserved());
        defer.deferedThread = ShortThread.doTask(() -> task.run(defer)).asDaemon(true);
        
        EventOffererHandler handler = (eventData) -> defer.deferedThread.startIt();
        
        if( this.objectState != ObjectStates.Waiting )
            { handler.eventFired(null); }
        
        if( this.objectState == ObjectStates.Waiting ) 
            { this.eventBundle.offerer.addListener(ResultState.Resolved, handler); }
        
        return defer;
    }    
    
    public static SimpleDefered defer(){
        return new SimpleDefered();
    }
}
