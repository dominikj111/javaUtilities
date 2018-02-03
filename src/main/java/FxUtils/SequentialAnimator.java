package FxUtils;

import EventLibrary.EventData;
import java.util.ArrayList;

public class SequentialAnimator<T extends Animation> extends EventLibrary.BasicEventOfferer {

    public double getRenderedTiles() {
        return this.currentAnimation + 1;
    }

    private enum InnerState { Running, WaitingForTiles, WantOneStep, WantToRun, WantToStop }
    
    public enum Events { NodeRendered }
    
    private final ArrayList<Animation> animationList;
    private int currentAnimation;
    private InnerState currentState;
    
    public SequentialAnimator(){
        this.animationList = new ArrayList<>();
        this.currentAnimation = -1;
        this.currentState = InnerState.WantToStop;
    }
        
    public void go(){
        this.currentState = InnerState.WantToRun;
        runAnimations();
    }
    
    public void oneStep(){
        this.currentState = InnerState.WantOneStep;
        runAnimations();
    }
    
    public void stop(){
        this.currentState = InnerState.WantToStop;
    }
        
    public T addAnimation(Animation tileAnimation){
        
        this.animationList.add(tileAnimation);
        
        if(this.currentState == InnerState.WaitingForTiles){
            runAnimations();
        }
        
        return (T)tileAnimation;
    }
    
    // ------------------------------
    // ROUTINES
    // ------------------------------
   
    private void runAnimations(){
        
        if ( this.currentState == InnerState.Running ) { return; }
        
        animationStep();   
    }
    
    private void animationStep(){
                
        if( this.currentState == InnerState.WantToStop ){
            return;
        }
        
        if( this.currentState == InnerState.WantToRun ){
            this.currentState = InnerState.Running;
        }
        
        if( this.currentState == InnerState.WantOneStep ){
            this.currentState = InnerState.WantToStop;
        }
        
        if( this.animationList.isEmpty() ||
            this.currentAnimation + 1 == this.animationList.size()){
            
            this.currentState = InnerState.WaitingForTiles;
            return;
        }
        
        if( this.currentState == InnerState.WaitingForTiles ){
            this.currentState = InnerState.Running;
        }
        
        animationList.get(++currentAnimation).run().then(
            (defer) -> {
                fireEvent(Events.NodeRendered, new EventData(defer.getPreserved(), this));
                animationStep();
            }
        );
    }
}
