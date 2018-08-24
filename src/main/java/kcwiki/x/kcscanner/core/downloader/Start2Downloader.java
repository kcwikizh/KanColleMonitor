/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.downloader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.core.KeyArrayValue;
import kcwiki.x.kcscanner.core.entity.CombinedFurnitureEntity;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.core.entity.Start2PatchEntity;
import kcwiki.x.kcscanner.core.files.processor.image.PhashController;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.database.service.FileDataService;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_bgm;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mapbgm;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mapinfo;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_payitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_slotitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_useitem;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.tools.CommontUtils;
import kcwiki.x.kcscanner.tools.ConstantValue;
import kcwiki.x.kcscanner.types.FileTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class Start2Downloader {
    private static final Logger LOG = LoggerFactory.getLogger(Start2Downloader.class);
    
    @Autowired
    AppConfigs appConfigs;  
    @Autowired
    RuntimeValue runtimeValue;
    @Autowired
    FileDataService fileDataService;
    @Autowired
    HttpClientConfig httpClientConfig;
    RequestConfig requestConfig;
    String[] bgmsuffix = {};
    
    Date date = new Date();
    String host;
    
    @PostConstruct
    public void initMethod() {
        host = appConfigs.getKcserver_host();
        if(appConfigs.isDebug()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
    }
    
    
    public void download(Start2PatchEntity start2PatchEntity, boolean isDownloadVoice) {
        
        if(!start2PatchEntity.getNewShip().isEmpty())
            downloadShip(start2PatchEntity.getNewShip(), true);
        if(!start2PatchEntity.getModifiedShip().isEmpty())
            downloadShip(start2PatchEntity.getModifiedShip(), false);
        
        if(!start2PatchEntity.getNewSlotitem().isEmpty())
            downloadSlotitem(start2PatchEntity.getNewSlotitem(), true);
        if(!start2PatchEntity.getModifiedSlotitem().isEmpty())
            downloadSlotitem(start2PatchEntity.getModifiedSlotitem(), false);
        
        if(!start2PatchEntity.getNewFurniture().isEmpty())
            downloadFurniture(start2PatchEntity.getNewFurniture(), true);
        if(!start2PatchEntity.getModifiedFurniture().isEmpty())
            downloadFurniture(start2PatchEntity.getModifiedFurniture(), false);
        
        if(!start2PatchEntity.getNewUseitem().isEmpty())
            downloadUseitem(start2PatchEntity.getNewUseitem(), true);
        if(!start2PatchEntity.getModifiedUseitem().isEmpty())
            downloadUseitem(start2PatchEntity.getModifiedUseitem(), false);
        
        if(!start2PatchEntity.getNewPayitem().isEmpty())
            downloadPayitem(start2PatchEntity.getNewPayitem(), true);
        if(!start2PatchEntity.getModifiedPayitem().isEmpty())
            downloadPayitem(start2PatchEntity.getModifiedPayitem(), false);
        
        if(!start2PatchEntity.getNewMapinfo().isEmpty())
            downloadMapinfo(start2PatchEntity.getNewMapinfo(), start2PatchEntity.getNewMapbgm());
        if(!start2PatchEntity.getModifiedMapinfo().isEmpty())
            downloadMapinfo(start2PatchEntity.getModifiedMapinfo(), null);
        
        if(!start2PatchEntity.getNewMapbgm().isEmpty())
            downloadNewMapbgm(start2PatchEntity.getNewMapbgm());
        if(!start2PatchEntity.getModifiedMapbgm().isEmpty())
            downloadModifiedMapbgm(start2PatchEntity.getModifiedMapbgm());
        
        if(!start2PatchEntity.getNewBgm().isEmpty())
            downloadBgm(start2PatchEntity.getNewBgm(), true);
        if(!start2PatchEntity.getModifiedBgm().isEmpty())
            downloadBgm(start2PatchEntity.getModifiedBgm(), false);
        
        if(isDownloadVoice){
            if(!start2PatchEntity.getNewShip().isEmpty())
                downloadVoice(start2PatchEntity.getNewShip(), true);
            if(!start2PatchEntity.getModifiedShip().isEmpty())
                downloadVoice(start2PatchEntity.getModifiedShip(), false);
        }
    }
    
    private void downloadShip(List<CombinedShipEntity> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs/resources/swf/ships";
        list.forEach(item -> {
            String url = String.format("%s/%s/%s.swf", host, prePath, item.getApi_filename());
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.swf", folder, item.getApi_filename());
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, item.getApi_filename()+".swf", requestConfig);
            if(fileDataEntity != null){
                fileDataEntity.setFilename(item.getApi_filename()+".swf");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.swf", prePath, item.getApi_filename()));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Ship);
                dblist.add(fileDataEntity);
            }
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadSlotitem(List<Api_mst_slotitem> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            IntStream.rangeClosed(1, 4).forEach(index -> {
                String prePath = null;
                String filename = String.format("%03d", item.getApi_id());
                if(index == 1) 
                    prePath = "kcs/resources/image/slotitem/card";
                if(index == 2) 
                    prePath = "kcs/resources/image/slotitem/item_character";
                if(index == 3) 
                    prePath = "kcs/resources/image/slotitem/item_on";
                if(index == 4) 
                    prePath = "kcs/resources/image/slotitem/item_up";
                
                String url = String.format("%s/%s/%s.png", host, prePath, filename);
                String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                String path = String.format("%s/%s.png", folder, filename);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
                if(fileDataEntity != null){
                    fileDataEntity.setFilename(filename+".png");
                    fileDataEntity.setHash(CommontUtils.getFileHex(path));
                    fileDataEntity.setPath(String.format("%s/%s.png", prePath, filename));
                    fileDataEntity.setTimestamp(date);
                    fileDataEntity.setType(FileTypes.Slotitem);
                    dblist.add(fileDataEntity);
                }
            });
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadFurniture(List<CombinedFurnitureEntity> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            String prePath = null;
            String sufPath = ".png";
            String filename = String.format("%03d", item.getApi_id());
            
            if(item.getApi_type() ==0){
                prePath="kcs/resources/image/furniture/floor";
            }
            if(item.getApi_type() ==1){
                prePath="kcs/resources/image/furniture/wall";
            }
            if(item.getApi_type() ==2){
                prePath="kcs/resources/image/furniture/window";
            }
            if(item.getApi_type() ==3){
                prePath="kcs/resources/image/furniture/object";
            }
            if(item.getApi_type() ==4){
                prePath="kcs/resources/image/furniture/chest";
            }
            if(item.getApi_type() ==5){
                prePath="kcs/resources/image/furniture/desk";
            }   
            if(!StringUtils.isBlank(item.getApi_filename())){
                filename = item.getApi_filename();
                sufPath = ".swf";
            }
                
            String url = String.format("%s/%s/%s%s", host, prePath, filename, sufPath);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s%s", folder, filename, sufPath);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+sufPath, requestConfig);
            if(fileDataEntity != null){
                fileDataEntity.setFilename(filename+sufPath);
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s%s", prePath, filename, sufPath));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Furniture);
                dblist.add(fileDataEntity);
            }
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadUseitem(List<Api_mst_useitem> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs/resources/image/useitem/card";
        list.forEach(item -> {
            String filename = String.format("%03d", item.getApi_id());
            String url = String.format("%s/%s/%s.png", host, prePath, filename);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.png", folder, filename);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
            if(fileDataEntity != null){
                fileDataEntity.setFilename(filename+".png");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.png", prePath, filename));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Useitem);
                dblist.add(fileDataEntity);
            }
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadPayitem(List<Api_mst_payitem> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs/resources/image/payitem/card";
        list.forEach(item -> {
            String filename = String.format("%03d", item.getApi_id());
            String url = String.format("%s/%s/%s.png", host, prePath, filename);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.png", folder, filename);
//            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadMapinfo(List<Api_mst_mapinfo> list, Map<Integer, Api_mst_mapbgm> hashmap){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs/resources/swf/map";
        list.forEach(item -> {
            String filename = String.format("%02d_0%d", item.getApi_maparea_id(), item.getApi_no());
            String url = String.format("%s/%s/%s.swf", host, prePath, filename);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.swf", folder, filename);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".swf", requestConfig);
            
            Api_mst_mapbgm api_mst_mapbgm = hashmap.get(item.getApi_id());
            
            if(fileDataEntity != null){
                fileDataEntity.setFilename(filename+".swf");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.swf", prePath, filename));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Mapinfo);
                dblist.add(fileDataEntity);
            }
            
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadNewMapbgm(Map<Integer, Api_mst_mapbgm> hashmap){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs/resources/swf";
        hashmap.forEach((k, item) -> {
            String filename;
            String url;
            String folder;
            String path;
            if(item.getApi_map_bgm().get(0).equals(item.getApi_map_bgm().get(1))){
                filename = String.format("sound_b_bgm_%d", item.getApi_map_bgm().get(0));
                url = String.format("%s/%s/%s.swf", host, prePath, filename);
                folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                path = String.format("%s/%s.swf", folder, filename);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".swf", requestConfig);
                if(fileDataEntity != null){
                    fileDataEntity.setFilename(filename+".swf");
                    fileDataEntity.setHash(CommontUtils.getFileHex(path));
                    fileDataEntity.setPath(String.format("%s/%s.swf", prePath, filename));
                    fileDataEntity.setTimestamp(date);
                    fileDataEntity.setType(FileTypes.Mapbgm);
                    dblist.add(fileDataEntity);
                }
            } else {
                IntStream.rangeClosed(0, 1).forEach(index -> {
                    String _filename = String.format("sound_b_bgm_%d", item.getApi_map_bgm().get(index));
                    String _url = String.format("%s/%s/%s.swf", host, prePath, _filename);
                    String _folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                    String _path = String.format("%s/%s.swf", _folder, _filename);
                    FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(_url, _folder, _filename+".swf", requestConfig);
                    if(fileDataEntity != null){
                        fileDataEntity.setFilename(_filename+".swf");
                        fileDataEntity.setHash(CommontUtils.getFileHex(_path));
                        fileDataEntity.setPath(String.format("%s/%s.swf", prePath, _filename));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.Mapbgm);
                        dblist.add(fileDataEntity);
                    }
                });
            }
            
            if(item.getApi_boss_bgm().get(0).equals(item.getApi_boss_bgm().get(1))){
                filename = String.format("sound_b_bgm_%d", item.getApi_boss_bgm().get(0));
                url = String.format("%s/%s/%s.swf", host, prePath, filename);
                folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                path = String.format("%s/%s.swf", folder, filename);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".swf", requestConfig);
                if(fileDataEntity != null){
                        fileDataEntity.setFilename(filename+".swf");
                        fileDataEntity.setHash(CommontUtils.getFileHex(path));
                        fileDataEntity.setPath(String.format("%s/%s.swf", prePath, filename));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.Mapbgm);
                        dblist.add(fileDataEntity);
                }
            } else {
                IntStream.rangeClosed(0, 1).forEach(index -> {
                    String _filename = String.format("sound_b_bgm_%d", item.getApi_boss_bgm().get(index));
                    String _url = String.format("%s/%s/%s.swf", host, prePath, _filename);
                    String _folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                    String _path = String.format("%s/%s.swf", _folder, _filename);
                    FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(_url, _folder, _filename+".swf", requestConfig);
                    if(fileDataEntity != null){
                        fileDataEntity.setFilename(_filename+".swf");
                        fileDataEntity.setHash(CommontUtils.getFileHex(_path));
                        fileDataEntity.setPath(String.format("%s/%s.swf", prePath, _filename));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.Mapbgm);
                        dblist.add(fileDataEntity);
                    }
                });
            }
            
            if(item.getApi_moving_bgm() > 0){
                filename = String.format("sound_b_bgm_%d", item.getApi_boss_bgm().get(0));
                url = String.format("%s/%s/%s.swf", host, prePath, filename);
                folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                path = String.format("%s/%s.swf", folder, filename);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".swf", requestConfig);
                if(fileDataEntity != null){
                        fileDataEntity.setFilename(filename+".swf");
                        fileDataEntity.setHash(CommontUtils.getFileHex(path));
                        fileDataEntity.setPath(String.format("%s/%s.swf", prePath, filename));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.Mapbgm);
                        dblist.add(fileDataEntity);
                }
            }
            
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadModifiedMapbgm(Map<Integer, List<Integer>> hashmap){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs/resources/swf";
        hashmap.forEach((k, list) -> {
            IntStream.rangeClosed(0, 4).forEach(index -> {
                if(list.get(index).equals(-1)){
                    return;
                }
                String filename = String.format("sound_b_bgm_%d", list.get(index));
                String url = String.format("%s/%s/%s.swf", host, prePath, filename);
                String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                String path = String.format("%s/%s.swf", folder, filename);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".swf", requestConfig);
                if(index == 4){ //api_moving_bgm
                    
                }
                if(fileDataEntity != null){
                        fileDataEntity.setFilename(filename+".swf");
                        fileDataEntity.setHash(CommontUtils.getFileHex(path));
                        fileDataEntity.setPath(String.format("%s/%s.swf", prePath, filename));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.Mapbgm);
                        dblist.add(fileDataEntity);
                }
            });
        });
        fileDataService.insertSelected(dblist);
    }
    
    private void downloadBgm(List<Api_mst_bgm> list, boolean isNew){
        
    }
    
    public void downloadVoice(List<CombinedShipEntity> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            int shipID = item.getApi_id();
            if(shipID >= 1500){return;}
            List<String>  memberList = new ArrayList<>();
            String voiceName;
            RequestConfig rc = requestConfig;
            String prePath = String.format("kcs/sound/kc%s", item.getApi_filename());
            for (int i=1; i<KeyArrayValue.vcKey.length; i++){
                    voiceName=String.valueOf((shipID + 7) * 17 * (KeyArrayValue.vcKey[i] - KeyArrayValue.vcKey[i - 1]) % 99173 + 100000);
                    memberList.add(voiceName);
            }
            memberList.forEach(_voiceName -> {
                String url = String.format("%s/%s/%s.mp3", host, prePath, _voiceName);
                String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                String path = String.format("%s/%s.mp3", folder, _voiceName);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, _voiceName+".mp3", rc);
                if(fileDataEntity != null){
                        fileDataEntity.setFilename(_voiceName+".mp3");
                        fileDataEntity.setHash(CommontUtils.getFileHex(path));
                        fileDataEntity.setPath(String.format("%s/%s.mp3", prePath, _voiceName));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.ShipVoice);
                        dblist.add(fileDataEntity);
                }
            });
        });
        fileDataService.insertSelected(dblist);
    }
    
    public boolean downloadCore(){
        String prePath = "kcs";
        String filename = "Core";
        String url = String.format("%s/%s/%s.swf", host, prePath, filename);
        String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
        String path = String.format("%s/%s.swf", folder, filename);
        FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".swf", requestConfig);
        
        
        return false;
    }
    
    
}
