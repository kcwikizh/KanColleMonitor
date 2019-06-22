/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.cache.inmem;

import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.initializer.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class RuntimeValue {
    
    @Autowired
    AppConfig appConfig;
    
    public String APPROOT;
    public String PRIVATEDATA_FOLDER;
    public String DOWNLOAD_FOLDER;
    public String STORAGE_FOLDER;
    public String TEMPLATE_FOLDER;
    public String PUBLISH_FOLDER;
    public String WORKSPACE_FOLDER;
    public String FILE_SCANCORE;
    public String WEBROOT_FOLDER;
    
    @PostConstruct
    public void initMethod() {
        WEBROOT_FOLDER = appConfig.getFolder_webroot();
        APPROOT = appConfig.getSystem_user_dir();
        
        PRIVATEDATA_FOLDER =
            String.format("%s/%s", APPROOT, appConfig.getFolder_privatedata());
        DOWNLOAD_FOLDER =
            String.format("%s/%s", APPROOT, appConfig.getFolder_download());
        TEMPLATE_FOLDER =
            String.format("%s/%s", APPROOT, appConfig.getFolder_template());
        STORAGE_FOLDER =
            String.format("%s/%s", APPROOT, appConfig.getFolder_storage());
    
        PUBLISH_FOLDER =
            String.format("%s/%s", WEBROOT_FOLDER, appConfig.getFolder_publish());
        WORKSPACE_FOLDER =
            String.format("%s/%s", WEBROOT_FOLDER, appConfig.getFolder_workspace());
    
        FILE_SCANCORE =
            String.format("%s/%s", APPROOT, appConfig.getFile_filelist());
    }
    
}
