/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.initializer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author iTeam_VEP
 */
public class TimedTasks {
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10); 
    
    public boolean initTasks(){
        scheduledExecutorService.scheduleWithFixedDelay(new test(), 5, 2, TimeUnit.SECONDS);
        return true;
    }
}

class test implements Runnable  {

    test() {
    }
    
    test(String name) {
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
