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
public enum FileTypes implements EnumBase {
    Core(0),
    Ship(1),
    Slotitem(2),
    Furniture(3),
    Useitem(2),
    Payitem(3),
    Mapbgm(2)
    ;
    
    private int code;
    
    FileTypes(int code) {
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
