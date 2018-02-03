/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shorts;

/**
 *
 * @author DEli
 */
public class ShortThread extends Thread {
    
    public ShortThread(Runnable target){
        super(target);
    }
    
    public ShortThread asDaemon(boolean value){
        
        this.setDaemon(value);
        
        return this;
    }
    
    public ShortThread startIt(){
        this.start();
        return this;
    }
    
    public static ShortThread doTask(Runnable target){
        
        return new ShortThread(target);
    }        
}
