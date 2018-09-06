/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.exception;

import kcwiki.x.kcscanner.types.KcServerStatus;
import kcwiki.x.kcscanner.types.MsgTypes;
import kcwiki.x.kcscanner.types.ServiceTypes;

/**
 *
 * @author x5171
 */

public class ExceptionBase extends RuntimeException {

    /**
     * @return the kcServerStatus
     */
    public KcServerStatus getKcServerStatus() {
        return kcServerStatus;
    }

    /**
     * @param kcServerStatus the kcServerStatus to set
     */
    public void setKcServerStatus(KcServerStatus kcServerStatus) {
        this.kcServerStatus = kcServerStatus;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 错误严重类型
     */
    private MsgTypes msgType;
    
    /**
     * 错误服务类型
     */
    private ServiceTypes serviceType;
    private KcServerStatus kcServerStatus;
    
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
    public ExceptionBase(String message)
    {
        super(message);
    }
    
    public ExceptionBase(Throwable cause)
    {
        super(cause);
    }
    
    public ExceptionBase(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public ExceptionBase(ServiceTypes serviceType, MsgTypes msgType)
    {
        this.msgType = msgType;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, int code)
    {
        this.code = code;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, String message)
    {
        super(message);
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, KcServerStatus kcServerStatus)
    {
        this.kcServerStatus = kcServerStatus;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, KcServerStatus kcServerStatus, String message)
    {
        super(message);
        this.kcServerStatus = kcServerStatus;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, MsgTypes msgType, String message)
    {
        super(message);
        this.msgType = msgType;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, MsgTypes msgType, int code)
    {
        this.code = code;
        this.msgType = msgType;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, MsgTypes msgType, int code, String message)
    {
        super(message);
        this.code = code;
        this.msgType = msgType;
        this.serviceType = serviceType;
    }
    
    public ExceptionBase(ServiceTypes serviceType, int code, String message)
    {
        super(message);
        this.code = code;
        this.serviceType = serviceType;
    }


    /**
     * @return the msgType
     */
    public MsgTypes getMsgType() {
        return msgType;
    }

    /**
     * @param msgType the msgType to set
     */
    public void setMsgType(MsgTypes msgType) {
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
