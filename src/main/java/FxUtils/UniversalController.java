package FxUtils;

import EventLibrary.BasicEventOfferer;
import EventLibrary.SingleInvokeEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import EventLibrary.EventData;
import EventLibrary.EventOffererHandler;

public abstract class UniversalController extends BasicEventOfferer implements Initializable {
               
    
    private SingleEvents singleEvents;
    
    protected UniversalController(){
        this.singleEvents = new SingleEvents(this);
    }
    
    
    public void setToFront(){
        getMainPane().setDisable(false);
        getMainPane().toFront();
    }
    
    public void setToBack(){
        getMainPane().setDisable(true);
        getMainPane().toBack();
    }
    
    public abstract void showAnimation(Runnable callbackAfter);
 
    public abstract void hideAnimation(Runnable callbackAfter);
    
    
    protected SingleEvents getSingleEvents(){
        return this.singleEvents;
    }
    
    protected static final class SingleEvents extends SingleInvokeEvent {
        
        private enum events { showHideAnimationFinished }
        
        private UniversalController controller;
        
        private SingleEvents(UniversalController controller){
            this.controller = controller;
        }
        
        public void fireShowHideAnimationFinished(){
            fireEvent(events.showHideAnimationFinished, new EventData(this, controller));           
        }
        
        public void registerShowHideAnimationFinished(EventOffererHandler listener){
            addListener(events.showHideAnimationFinished, listener);
        }
    }
    
    protected abstract Node getMainPane();
}
