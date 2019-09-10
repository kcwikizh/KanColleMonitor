/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.types;

/**
 *
 * @author iHaru
 */
public enum MessageLevel implements BaseEnum {
    UNKNOWN(0),
    TRACE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5)
    ;
    
    private int code;
    
    MessageLevel(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "空";
            case 0:
                return "未知";
            case 1:
                return "示踪";
            case 2:
                return "调试";
            case 3:
                return "通知"; 
            case 4:
                return "警告"; 
            case 5:
                return "错误"; 
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
