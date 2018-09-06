/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.types;

/**
 *
 * @author x5171
 */
public enum HttpRepStatus implements BaseEnum {
    UNKNOWNS(0),
    ERROR(-1),
    FAILURE(1),
    SUCCESS(2),
    
    OK(200),
    Created(201),
    Accepted(202),
    Not_Modified(304),
    Bad_Request(400),
    Unauthorized(401),
    Payment_Required(402),
    Forbidden(403),
    Not_Acceptable(406),
    Conflict(409),
    Unavailable_For_Legal_Reasons (451),
    Internal_Server_Error(500),
    Service_Unavailable(503),
    Network_Authentication_Required(511)
    ;
    
    private int code;
    
    HttpRepStatus(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "未知";
            case 0:
                return "UNKNOWNS";
            case -1:
                return "ERROR";
            case 1:
                return "FAILURE";
            case 2:
                return "SUCCESS"; 
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
