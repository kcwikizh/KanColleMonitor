/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.cache.inmem;

import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import static kcwiki.x.kcscanner.tools.ConstantValue.CLASSPATH;
import static kcwiki.x.kcscanner.tools.ConstantValue.WEBROOT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class RuntimeValue {
    
    @Autowired
    AppConfigs appConfigs;
    
    public String PRIVATEDATA_FOLDER;
    public String DOWNLOAD_FOLDER;
    public String TEMPLATE_FOLDER;
    public String PUBLISH_FOLDER;
    public String WORKSPACE_FOLDER;
    public String FILE_SCANCORE;
    public String FILE_FFDEC;
    
    @PostConstruct
    public void initMethod() {
        PRIVATEDATA_FOLDER =
            String.format("%s%s", CLASSPATH, appConfigs.getFolder_privatedata());
        DOWNLOAD_FOLDER =
            String.format("%s%s", CLASSPATH, appConfigs.getFolder_download());
        TEMPLATE_FOLDER =
            String.format("%s%s", CLASSPATH, appConfigs.getFolder_template());
    
        PUBLISH_FOLDER =
            String.format("%s%s", WEBROOT, appConfigs.getFolder_template());
        WORKSPACE_FOLDER =
            String.format("%s%s", WEBROOT, appConfigs.getFolder_template());
    
        FILE_SCANCORE =
            String.format("%s%s", CLASSPATH, appConfigs.getFile_filelist());
        FILE_FFDEC =
            String.format("%s%s", CLASSPATH, appConfigs.getFile_ffdec());
    }
    
}
