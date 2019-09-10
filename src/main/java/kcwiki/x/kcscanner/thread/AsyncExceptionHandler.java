/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.thread;

import java.lang.reflect.Method;
import kcwiki.x.kcscanner.initializer.AppInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 *
 * @author iHaru
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {

        LOG.error("AsyncException Cause - " + throwable.getMessage());
        LOG.error("Method name - " + method.getName());
        for (Object param : obj) {
            LOG.error("Parameter value - " + param);
        }
    }
}
