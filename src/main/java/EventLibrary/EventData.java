package EventLibrary;

final public class EventData {
    
    private Object data, source;
    
    public EventData(Object data, Object source){
        this.data = data;
        this.source = source;
    }
    
    public <T> T getData(){
        return (T)this.data;
    }
    
    public <S> S getSource(){
        return (S)this.source;
    }
}
