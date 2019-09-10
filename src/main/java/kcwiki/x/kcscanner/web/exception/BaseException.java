/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.exception;

import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.types.ServiceTypes;

/**
 *
 * @author iHaru
 */

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误严重类型
     */
    private MessageLevel msgType;
    
    /**
     * 错误服务类型
     */
    private ServiceTypes serviceType;
    
    /**
     * 错误子编号
     */
    private Integer code;
    
    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     */
    public BaseException(String message)
    {
        super(message);
    }
    
    public BaseException(Throwable cause)
    {
        super(cause);
    }
    
    public BaseException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public BaseException(ServiceTypes serviceType, MessageLevel msgType)
    {
        this.setMsgType(msgType);
        this.setServiceType(serviceType);
    }
    
    public BaseException(ServiceTypes serviceType, int code)
    {
        this.setCode(code);
        this.setServiceType(serviceType);
    }
    
    public BaseException(ServiceTypes serviceType, String message)
    {
        super(message);
        this.setServiceType(serviceType);
    }
    
    public BaseException(ServiceTypes serviceType, MessageLevel msgType, String message)
    {
        super(message);
        this.setMsgType(msgType);
        this.setServiceType(serviceType);
    }
    
    public BaseException(ServiceTypes serviceType, MessageLevel msgType, int code, String message)
    {
        super(message);
        this.setMsgType(msgType);
        this.setServiceType(serviceType);
        this.setCode(code);
    }
    
    public BaseException(ServiceTypes serviceType, int code, String message)
    {
        super(message);
        this.setCode(code);
        this.setServiceType(serviceType);
    }


    /**
     * @return the msgType
     */
    public MessageLevel getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(MessageLevel msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the serviceType
     */
    public ServiceTypes getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(ServiceTypes serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }
    
}
