/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.downloader;

import java.util.List;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.core.entity.Start2PatchEntity;
import static kcwiki.x.kcscanner.tools.ConstantValue.DOWNLOAD_FOLDER;

/**
 *
 * @author x5171
 */
public class Start2Downloader {
    public void download(Start2PatchEntity start2PatchEntity) {
        if(!start2PatchEntity.getNewShip().isEmpty())
            downloadShip(start2PatchEntity.getNewShip(), true);
        if(start2PatchEntity.getModifiedShip().isEmpty())
            downloadShip(start2PatchEntity.getNewShip(), false);
    }
    
    private void downloadShip(List<CombinedShipEntity> list, boolean isNew){
        String host = AppDataCache.appConfigs.getKcserver_host();
        String prePath ;
        if(isNew){
            prePath = "kcs/resources/swf/ships/new";
        } else {
            prePath = "kcs/resources/swf/ships/modified";
        }
        list.forEach(ship -> {
            String url = String.format("%s/kcs/resources/swf/ships/%s.swf", host, ship.getApi_filename());
            String folder = String.format("%s/%s.swf", prePath, ship.getApi_filename());
            String path = String.format("%s/%s/%s.swf", DOWNLOAD_FOLDER, prePath, ship.getApi_filename());
        });
    }
}
