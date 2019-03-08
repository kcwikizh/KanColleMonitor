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
public enum KcServerStatus implements BaseEnum {
    Unknown(0),
    Maintenance(1),
    WARN(2),
    ERROR(3)
    ;
    
    private int code;
    
    KcServerStatus(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "未知";
            case 0:
                return "测试";
            case 1:
                return "普通";
            case 2:
                return "警告";
            case 3:
                return "错误"; 
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
