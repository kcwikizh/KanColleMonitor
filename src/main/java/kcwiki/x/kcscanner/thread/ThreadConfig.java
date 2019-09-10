/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.thread;

import java.util.concurrent.Executors;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author iHaru
 */
//@Configuration
public class ThreadConfig {
    
//    @Bean(name = "threadPoolExecutor")
    public TaskExecutor getAsyncExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(7);
            executor.setMaxPoolSize(42);
            executor.setQueueCapacity(11);
            executor.setThreadNamePrefix("threadPoolExecutor-");
            executor.initialize();
            return executor;
    }
    
//    @Bean(name = "ConcurrentTaskExecutor")
    public TaskExecutor taskExecutor2 () {
            return new ConcurrentTaskExecutor(
                            Executors.newFixedThreadPool(3));
    }
}
