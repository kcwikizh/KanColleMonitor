/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.types;


/**
 *
 * @author iTeam_VEP
 */
public enum ServiceTypes implements EnumBase{
    KanColleScanner(100),
    KanColleServer(200),
    HttpClient(300)
    ;
    
    ServiceTypes(int code) {
        this.code = code;
    }
        
    private int code;
        
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
