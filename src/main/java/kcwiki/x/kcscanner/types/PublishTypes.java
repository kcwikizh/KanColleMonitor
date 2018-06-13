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
public enum PublishTypes implements EnumBase{
    Admin(0),
    Guest(1),
    All(2)
    ;
    
    private int code;
    
    PublishTypes(int code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        switch(code) {
            default:
                return "未知";
            case 0:
                return "管理员";
            case 1:
                return "游客";
            case 2:
                return "全部";
        }
    }

    @Override
    public int getCode() {
        return code;
    }
}
