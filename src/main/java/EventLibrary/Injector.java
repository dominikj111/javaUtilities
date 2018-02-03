package EventLibrary;

import java.lang.reflect.Field;

abstract class Injector {
    
    protected static void injection(BasicEventOfferer eventOffering, Object intoObject){
        
        EventTrigger trigger = (Enum eventMarker, EventData data) -> {
            eventOffering.fireEvent(eventMarker, data);
        };
        
        Field[] fields = intoObject.getClass().getDeclaredFields();        
        boolean accessibleBackup;
        
        for (Field field : fields) {            
            if(field.isAnnotationPresent(EventOffererController.class)){
                try {
                    accessibleBackup = field.isAccessible();
                    field.setAccessible(true);
                    field.set(intoObject, eventOffering);
                    field.setAccessible(accessibleBackup);
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) { } 
            }
            if(field.isAnnotationPresent(EventTriggerController.class)){
                try {
                    accessibleBackup = field.isAccessible();
                    field.setAccessible(true);
                    field.set(intoObject, trigger);
                    field.setAccessible(accessibleBackup);
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) { } 
            }
        }
    }
    
    protected static EventBundle createEventTriggerBundle(BasicEventOfferer eventOffering){
        
        EventTrigger eTrigger = (Enum eventMarker, EventData data) -> {
            eventOffering.fireEvent(eventMarker, data);
        };
        
        return new EventBundle(eventOffering, eTrigger);
    }
}
