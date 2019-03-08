/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.types;

import org.springframework.http.HttpStatus;

/**
 *
 * @author iHaru
 */
public enum BaseHttpStatus{
    UNKNOWNS("服务器无法处理当前请求", HttpStatus.OK),
    ERROR("服务器发生错误", HttpStatus.OK),
    FAILURE("失败", HttpStatus.OK),
    SUCCESS("成功", HttpStatus.OK),
    
    OK("", HttpStatus.OK),
    Created("", HttpStatus.CREATED),
    Accepted("", HttpStatus.ACCEPTED),
    Not_Modified("", HttpStatus.NOT_MODIFIED),
    Bad_Request("", HttpStatus.BAD_REQUEST),
    Unauthorized("", HttpStatus.UNAUTHORIZED),
    Payment_Required("", HttpStatus.PAYMENT_REQUIRED),
    Forbidden("", HttpStatus.FORBIDDEN),
    Not_Acceptable("", HttpStatus.NOT_ACCEPTABLE),
    Conflict("", HttpStatus.CONFLICT),
    Unavailable_For_Legal_Reasons ("", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS),
    Internal_Server_Error("", HttpStatus.INTERNAL_SERVER_ERROR),
    Not_Implemented("", HttpStatus.NOT_IMPLEMENTED),
    Service_Unavailable("", HttpStatus.SERVICE_UNAVAILABLE),
    Network_Authentication_Required("", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
    ;
    
    private String msg;
    private HttpStatus httpStatus;
    
    BaseHttpStatus(String msg, HttpStatus httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    public String getMsg() {
        return this.msg;
    }
    
}
