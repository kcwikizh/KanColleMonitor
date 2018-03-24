/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.exception;

/**
 *
 * @author iTeam_VEP
 */
public class CustomedException  extends RuntimeException {
    private CodeDict errorCode ;  //异常对应的返回码  
    private String errorMsg;  //异常对应的描述信息 
    private StackTraceElement[] errorStack;   //异常对应的方法栈 
    
    public CustomedException() {  
        super();  
    }  
  
    public CustomedException(String message) {  
        super(message);  
        this.errorMsg = message;  
    }  
    
    public CustomedException(String message, StackTraceElement[] stack) {  
        super(message);  
        this.errorMsg = message;
        this.errorStack = stack;  
    } 
  
    public CustomedException(CodeDict code, String message, StackTraceElement[] stack) {  
        super(message);  
        this.errorCode = code;  
        this.errorMsg = message; 
        this.errorStack = stack;  
    }  

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @return the errorStack
     */
    public StackTraceElement[] getErrorStack() {
        return errorStack;
    }

    /**
     * @return the errorCode
     */
    public CodeDict getErrorCode() {
        return errorCode;
    }

}
