/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.aspect;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import kcwiki.x.kcscanner.database.service.LogService;
import kcwiki.x.kcscanner.types.MessageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 * https://www.cnblogs.com/liuruowang/p/5711563.html
 * 
 */
@Component
@Aspect
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    
    @Autowired //自动注入  
    private LogService logService; 
        
//     声明ex时指定的类型会限制目标方法必须抛出指定类型的异常  
//     此处将ex的类型声明为Throwable，意味着对目标方法抛出的异常不加限制  
//    @AfterThrowing(pointcut="execution(* ru.com.senka.test.*.*(..))", throwing = "ex")  
//    public void doLogActions(JoinPoint jp, Throwable ex)  
//    {  
//        LOG.error("[doLogActions] Method Signature:{}", jp.getSignature());
//        LOG.error("[doLogActions] Exception:{}", String.valueOf(ex));
//        logService.addLog(LogStates.ERROR, "目标方法中抛出的异常:" + ex);
//    }  
    
    // 匹配com.owenapp.service.impl包下所有类的、  
    // 所有方法的执行作为切入点  
//    @Pointcut("execution(* ru.com.senka.test.Chinese.*(..))")
//    public void pointCut(){}

    
    @Pointcut("execution(* kcwiki.x.kcscanner.test..*.*(..))"
            + "or execution(* kcwiki.x.kcscanner.initializer.*.*(..))"
            + "or execution(* kcwiki.x.kcscanner.core..*.*(..))"
            + "or execution(* kcwiki.x.kcscanner.database..*.*(..))"
            + "or execution(* kcwiki.x.kcscanner.httpclient..*.*(..))"
            + "or execution(* kcwiki.x.kcscanner.web..*.*(..))")
    public void pointCut(){}
    
//    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("AOP Before Advice...");
    }
    
//    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("AOP After Advice...");
    }
    
//    @AfterReturning(pointcut="pointCut()",returning="returnVal")
    public void afterReturn(JoinPoint joinPoint,Object returnVal){
        System.out.println("AOP AfterReturning Advice:" + returnVal);
    }
    
    @AfterThrowing(pointcut="pointCut()", throwing="error")
    public void afterThrowing(JoinPoint joinPoint,Throwable error){
        LOG.error("[Aspect-LogActions] Method Signature:{}", joinPoint.getSignature());
        LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(error));
        logService.addLog(MessageLevel.ERROR, joinPoint.getSignature().toLongString(), error);
    }
    
//    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp){
        System.out.println("AOP Aronud before...");
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            LOG.error("[Aspect-LogActions] Method Signature:{}", pjp.getSignature());
            LOG.error("[Aspect-LogActions] Exception:{}", ExceptionUtils.getStackTrace(e));
        }
        System.out.println("AOP Aronud after...");
        return result;
    }
}
