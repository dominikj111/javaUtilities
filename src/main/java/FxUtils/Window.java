package FxUtils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class Window<T extends Region, S extends UniversalController> {
    
    private T pane;
    private S controller;
    
    private Window(T pane, S controller){
        this.pane = pane;
        this.controller = controller;
    }
    
    public T getPane(){
        return this.pane;
    }
    
    public S getController(){
        return this.controller;
    }
    
    public void toFront(){
        getActiveWindow().getController().hideAnimation(
                () -> setActiveWindow(this).getController().showAnimation(()->{})
        );
    }    
                
    
    /* ******************************* */
    /* LOADER                          */
    /* ******************************* */
    
    private static StackPane _mainPane = new StackPane();    
    private static Window _activeWidow = null;
    
    public static Pane getMainPane(){
        return _mainPane;
    }
    
    private static Window setActiveWindow(Window window){
        _activeWidow = window;
        return window;
    }
    
    private static Window getActiveWindow(){
        return _activeWidow;
    }
    
    private static boolean activeWindowIsAssigned(){
        return _activeWidow != null;
    }
    
    public static Window loadWindow(String viewPath, boolean setToFront) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(Window.class.getResource(viewPath));        
        
        Region pane = loader.load();
        UniversalController controller = loader.getController();        
        getMainPane().getChildren().add(pane);
        
        Window windowToReturn = new Window(pane, controller);
               
        if(setToFront && activeWindowIsAssigned()){
            getMainPane().getChildren().stream().forEach((children) -> children.setDisable(true));
        }
        
        if(setToFront){
            setActiveWindow(windowToReturn);
            windowToReturn.getController().setToFront();
        } else {
            windowToReturn.getController().setToBack();
        }
                
        return windowToReturn;
    }
    
    public static Window loadWindow(String viewPath, boolean setToFront, boolean setMinSizeProperties) throws IOException {
        
        Window win = loadWindow(viewPath, setToFront);
        
        if(setMinSizeProperties){
            getMainPane().minHeightProperty().set(win.getPane().minHeightProperty().get());
            getMainPane().minWidthProperty().set(win.getPane().minWidthProperty().get());
        }
        
        return win;
    }
    
    public static Window loadWindow(String viewPath) throws IOException {
        return loadWindow(viewPath, false);
    }
}
