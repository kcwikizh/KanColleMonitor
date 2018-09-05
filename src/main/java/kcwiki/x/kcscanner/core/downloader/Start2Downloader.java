/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.downloader;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.core.KeyArrayValue;
import kcwiki.x.kcscanner.core.downloader.entity.DownloadStatus;
import kcwiki.x.kcscanner.core.entity.CombinedFurnitureEntity;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.core.entity.Start2PatchEntity;
import kcwiki.x.kcscanner.core.pathutils.BaseUrl;
import kcwiki.x.kcscanner.core.pathutils.ShipUtils;
import kcwiki.x.kcscanner.core.pathutils.SlotUtils;
import kcwiki.x.kcscanner.core.start2.types.BaseStart2Enum;
import kcwiki.x.kcscanner.core.start2.types.ShipTypes;
import kcwiki.x.kcscanner.core.start2.types.SlotTypes;
import kcwiki.x.kcscanner.core.start2.types.Start2Types;
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
    Map<FileTypes, List<DownloadStatus>> downloadResult = new ConcurrentHashMap();
    
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
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        List<Integer> list = new ArrayList<>();
        List<CompletableFuture[]> asyncResults = new ArrayList();
        
        if(!start2PatchEntity.getNewShip().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Ship)){
                downloadResult.put(FileTypes.Ship, new ArrayList());
            }
            List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getNewShip(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadShip(item, true), executorService)
//                                .thenApply(h->h)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
//            CompletableFuture[] cfs =  {CompletableFuture.supplyAsync(() -> downloadShip(_list.get(0), true), executorService)};
//            CompletableFuture.allOf(cfs).join();
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedShip().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Ship)){
                downloadResult.put(FileTypes.Ship, new ArrayList());
            }
            List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getModifiedShip(), 10);
            CompletableFuture[] cfs = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadShip(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!start2PatchEntity.getNewSlotitem().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Slotitem)){
                downloadResult.put(FileTypes.Slotitem, new ArrayList());
            }
            List<List<Api_mst_slotitem>> _list = Lists.partition(start2PatchEntity.getNewSlotitem(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadSlotitem(item, true), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedSlotitem().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Slotitem)){
                downloadResult.put(FileTypes.Slotitem, new ArrayList());
            }
            List<List<Api_mst_slotitem>> _list = Lists.partition(start2PatchEntity.getModifiedSlotitem(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadSlotitem(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!start2PatchEntity.getNewFurniture().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Furniture)){
                downloadResult.put(FileTypes.Furniture, new ArrayList());
            }
            List<List<CombinedFurnitureEntity>> _list = Lists.partition(start2PatchEntity.getNewFurniture(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadFurniture(item, true), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedFurniture().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Furniture)){
                downloadResult.put(FileTypes.Furniture, new ArrayList());
            }
            List<List<CombinedFurnitureEntity>> _list = Lists.partition(start2PatchEntity.getModifiedFurniture(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadFurniture(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!start2PatchEntity.getNewUseitem().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Useitem)){
                downloadResult.put(FileTypes.Useitem, new ArrayList());
            }
            downloadUseitem(start2PatchEntity.getNewUseitem(), true);
        }
        if(!start2PatchEntity.getModifiedUseitem().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Useitem)){
                downloadResult.put(FileTypes.Useitem, new ArrayList());
            }
            downloadUseitem(start2PatchEntity.getModifiedUseitem(), false);
        }
        
        if(!start2PatchEntity.getNewPayitem().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Payitem)){
                downloadResult.put(FileTypes.Payitem, new ArrayList());
            }
            downloadPayitem(start2PatchEntity.getNewPayitem(), true);
        }
        if(!start2PatchEntity.getModifiedPayitem().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Payitem)){
                downloadResult.put(FileTypes.Payitem, new ArrayList());
            }
            downloadPayitem(start2PatchEntity.getModifiedPayitem(), false);
        }
        
        if(!start2PatchEntity.getNewMapinfo().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Mapinfo)){
                downloadResult.put(FileTypes.Mapinfo, new ArrayList());
            }
            downloadMapinfo(start2PatchEntity.getNewMapinfo(), start2PatchEntity.getAllMapbgm(), true);
        }
        if(!start2PatchEntity.getModifiedMapinfo().isEmpty()) {
            if(!downloadResult.containsKey(FileTypes.Mapinfo)){
                downloadResult.put(FileTypes.Mapinfo, new ArrayList());
            }
            downloadMapinfo(start2PatchEntity.getModifiedMapinfo(), start2PatchEntity.getAllMapbgm(), false);
        }
        
        if(!start2PatchEntity.getNewMapbgm().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Mapbgm)){
                downloadResult.put(FileTypes.Mapbgm, new ArrayList());
            }
            List<Api_mst_mapbgm> temp = new ArrayList();
            start2PatchEntity.getNewMapbgm().forEach((k, v) -> temp.add(v));
            
            List<List<Api_mst_mapbgm>> _list = Lists.partition(temp, 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadNewMapbgm(item), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedMapbgm().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Mapbgm)){
                downloadResult.put(FileTypes.Mapbgm, new ArrayList());
            }
            downloadModifiedMapbgm(start2PatchEntity.getModifiedMapbgm());
        }
        
        if(!start2PatchEntity.getNewBgm().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Bgm)){
                downloadResult.put(FileTypes.Bgm, new ArrayList());
            }
            List<List<Api_mst_bgm>> _list = Lists.partition(start2PatchEntity.getNewBgm(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadBgm(item, true), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedBgm().isEmpty()){
            if(!downloadResult.containsKey(FileTypes.Bgm)){
                downloadResult.put(FileTypes.Bgm, new ArrayList());
            }
            List<List<Api_mst_bgm>> _list = Lists.partition(start2PatchEntity.getModifiedBgm(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadBgm(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!asyncResults.isEmpty()){
            asyncResults.forEach(future -> {
                CompletableFuture.allOf(future).join();
            });
            asyncResults.clear();
        }
        
        if(isDownloadVoice){
            if(!start2PatchEntity.getNewShip().isEmpty()) {
//                downloadResult.put(FileTypes.ShipVoice, (Map<Boolean, List<DownloadStatus>>) (new ConcurrentHashMap()).put(true, (new ArrayList())));
                List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getNewShip(), 10);
                CompletableFuture[] cfs = _list.stream()
                    .map(item -> CompletableFuture.supplyAsync(() -> downloadVoice(item, true), executorService)
                                    .whenComplete((s, e) -> {
                                        System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                        list.add(s);
                                    })
                ).toArray(CompletableFuture[]::new);
                asyncResults.add(cfs);
            }
            if(!start2PatchEntity.getModifiedShip().isEmpty()) {
//                downloadResult.put(FileTypes.ShipVoice, (Map<Boolean, List<DownloadStatus>>) (new ConcurrentHashMap()).put(false, (new ArrayList())));
                List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getModifiedShip(), 10);
                CompletableFuture[] cfs = _list.stream()
                    .map(item -> CompletableFuture.supplyAsync(() -> downloadVoice(item, false), executorService)
                                    .whenComplete((s, e) -> {
                                        System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                        list.add(s);
                                    })
                ).toArray(CompletableFuture[]::new);
                asyncResults.add(cfs);
            }
            if(!asyncResults.isEmpty()){
                if(!asyncResults.isEmpty()){
                asyncResults.forEach(future -> {
                    CompletableFuture.allOf(future).join();
                });
                asyncResults.clear();
            }
            }
        }
    }
    
    private void downloadGeneralItem(Start2PatchEntity start2PatchEntity){
        Start2Types[] generalTypes = Start2Types.values();
        String prePath = "kcs2/resources";
        for(Start2Types type:generalTypes){
            BaseStart2Enum[] subTypes = type.getSubTypes();
            for(BaseStart2Enum subtype:subTypes){
                
            }
        }
    }
    
    private int getGeneralItemUrl(Start2Types start2Types, BaseStart2Enum baseStart2Enum, int id){
        
        return 0;
    }
    
    private int getGeneralStartNum(){
        
        return 0;
    }
    
    private int downloadShip(List<CombinedShipEntity> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            for(BaseStart2Enum type:ShipTypes.values()){
                if(type.getTypeName().contains("card_round") || type.getTypeName().contains("icon_box"))
                    continue;
                String urlPath = "kcs2/resources/ship/" + type.getTypeName();
                String filePath = "kcs2/resources/ship/" + item.getApi_name();
                String obfsname = ShipUtils.getPath(item.getApi_id(), type.getTypeName());
                if(obfsname == null){
                    continue;
                }
//                String realname = ShipUtils.getWikiFileName(String.format("%03d", item.getApi_sortno()), type.getTypeName());
                String realname = ShipUtils.getWikiFileName(String.valueOf(item.getApi_id()), type.getTypeName());
                if(realname == null) {
                    realname = obfsname;
                    filePath += "/others";
                }
                String url = String.format("%s/%s/%s.png?version=%s", host, urlPath, obfsname, Integer.valueOf(item.getApi_version().get(0)));
                String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, filePath);
                String path = String.format("%s/%s.png", folder, realname);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, realname+".png", requestConfig);
                DownloadStatus downloadStatus = new DownloadStatus();
                downloadStatus.setFilename(realname);
                downloadStatus.setId(item.getApi_id());
                downloadStatus.setName(item.getApi_name());
                downloadStatus.setUrl(url);
                downloadStatus.setPath(path);
                downloadStatus.setIsNew(isNew);
                if(fileDataEntity != null){
                    fileDataEntity.setItemid(item.getApi_id());
                    fileDataEntity.setFilename(obfsname+".png");
                    fileDataEntity.setHash(CommontUtils.getFileHex(path));
                    fileDataEntity.setPath(String.format("%s/%s.png", urlPath, obfsname));
                    fileDataEntity.setTimestamp(date);
                    fileDataEntity.setType(FileTypes.Ship);
                    dblist.add(fileDataEntity);
                    downloadStatus.setHash(fileDataEntity.getHash());
                    downloadStatus.setIsSuccess(true);
                }
                downloadResult.get(FileTypes.Ship).add(downloadStatus);
            }
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    private int downloadSlotitem(List<Api_mst_slotitem> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            for(BaseStart2Enum type:SlotTypes.values()){
                String urlPath = "kcs2/resources/slot/" + type.getTypeName();
                String filePath = "kcs2/resources/slot/" + item.getApi_name();
                String obfsname = SlotUtils.getPath(item.getApi_id(), type.getTypeName());
                if(obfsname == null)
                    return;
                String realname = SlotUtils.getWikiFileName(String.format("%03d", item.getApi_sortno()), type.getTypeName());
                if(realname == null) {
                    realname = obfsname;
                    filePath += "/others";
                }
                String url = String.format("%s/%s/%s.png?version=%s", host, urlPath, obfsname, item.getApi_version());
                String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, filePath);
                String path = String.format("%s/%s.png", folder, realname);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, realname+".png", requestConfig);
                DownloadStatus downloadStatus = new DownloadStatus();
                downloadStatus.setFilename(realname);
                downloadStatus.setId(item.getApi_id());
                downloadStatus.setName(item.getApi_name());
                downloadStatus.setUrl(url);
                downloadStatus.setPath(path);
                downloadStatus.setIsNew(isNew);
                if(fileDataEntity != null){
                    fileDataEntity.setItemid(item.getApi_id());
                    fileDataEntity.setFilename(obfsname+".png");
                    fileDataEntity.setHash(CommontUtils.getFileHex(path));
                    fileDataEntity.setPath(String.format("%s/%s.png", urlPath, obfsname));
                    fileDataEntity.setTimestamp(date);
                    fileDataEntity.setType(FileTypes.Slotitem);
                    dblist.add(fileDataEntity);
                    downloadStatus.setHash(fileDataEntity.getHash());
                    downloadStatus.setIsSuccess(true);
                }
                downloadResult.get(FileTypes.Slotitem).add(downloadStatus);
            }
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    private int downloadFurniture(List<CombinedFurnitureEntity> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            String prePath = null;
            String obfsname = null;
//            if(item.getApi_type() ==0){
//                prePath="kcs2/resources/furniture/normal";
//            }
//            if(item.getApi_type() ==1){
//                prePath="kcs2/resources/furniture/wall";
//            }
//            if(item.getApi_type() ==2){
//                prePath="kcs2/resources/furniture/window";
//            }
//            if(item.getApi_type() ==3){
//                prePath="kcs2/resources/furniture/object";
//            }
//            if(item.getApi_type() ==4){
//                prePath="kcs2/resources/furniture/chest";
//            }
//            if(item.getApi_type() ==5){
//                prePath="kcs2/resources/furniture/desk";
//            }   
            
            if(item.getApi_active_flag()==0){
                prePath="kcs2/resources/furniture/normal";
                obfsname = BaseUrl.getItemUrl("furniture", item.getApi_id(), "normal");
            } else {
                prePath="kcs2/resources/furniture/movable";
                obfsname = BaseUrl.getItemUrl("furniture", item.getApi_id(), "movable");
            }
            
            String url = String.format("%s/%s/%s.png?version=%s", host, prePath, obfsname, item.getApi_version());
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.png", folder, obfsname);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, obfsname+".png", requestConfig);
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setFilename(obfsname);
            downloadStatus.setId(item.getApi_id());
            downloadStatus.setName(item.getApi_title());
            downloadStatus.setUrl(url);
            downloadStatus.setPath(path);
            downloadStatus.setIsNew(isNew);
            if(fileDataEntity != null){
                fileDataEntity.setItemid(item.getApi_id());
                fileDataEntity.setFilename(obfsname+".png");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.png", prePath, obfsname));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Furniture);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            downloadResult.get(FileTypes.Furniture).add(downloadStatus);
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    private void downloadUseitem(List<Api_mst_useitem> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            String prePath = "kcs2/resources/useitem/card_";
            if(StringUtils.isBlank(item.getApi_name()))
                return;
            String filename = String.format("%03d", item.getApi_id());
            String url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.png", folder, filename);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
            if(fileDataEntity == null) {
                prePath = "kcs2/resources/useitem/card";
                url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
                folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                path = String.format("%s/%s.png", folder, filename);
                fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
            }
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setFilename(filename);
            downloadStatus.setId(item.getApi_id());
            downloadStatus.setName(item.getApi_name());
            downloadStatus.setUrl(url);
            downloadStatus.setPath(path);
            downloadStatus.setIsNew(isNew);
            if(fileDataEntity != null){
                fileDataEntity.setItemid(item.getApi_id());
                fileDataEntity.setFilename(filename+".png");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.png", prePath, filename));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Useitem);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            downloadResult.get(FileTypes.Useitem).add(downloadStatus);
        });
        if(!dblist.isEmpty())            
            fileDataService.insertSelected(dblist);
    }
    
    private void downloadPayitem(List<Api_mst_payitem> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/img/item";
        int version = (int) (Math.random()*90 + 10);
        String filename = "item_payitemicon";
        String url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
        String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
        String path = String.format("%s/%s.png", folder, filename);
        FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
        DownloadStatus downloadStatus = new DownloadStatus();
        downloadStatus.setFilename(filename);
        downloadStatus.setId(-1);
        downloadStatus.setName(filename);
        downloadStatus.setUrl(url);
        downloadStatus.setPath(path);
        downloadStatus.setIsNew(isNew);
        if(fileDataEntity != null){
            fileDataEntity.setItemid(-1);
            fileDataEntity.setFilename(filename+".png");
            fileDataEntity.setHash(CommontUtils.getFileHex(path));
            fileDataEntity.setPath(String.format("%s/%s.png", prePath, filename));
            fileDataEntity.setTimestamp(date);
            fileDataEntity.setType(FileTypes.Payitem);
            dblist.add(fileDataEntity);
            downloadStatus.setHash(fileDataEntity.getHash());
            downloadStatus.setIsSuccess(true);
        }
        downloadResult.get(FileTypes.Payitem).add(downloadStatus);
        if(!dblist.isEmpty())            
            fileDataService.insertSelected(dblist);
    }
    
    private void downloadMapinfo(List<Api_mst_mapinfo> list, Map<Integer, Api_mst_mapbgm> hashmap, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            String prePath = "kcs2/resources/map/" + String.format("%03d", item.getApi_maparea_id());
            String filename = String.format("%02d_image", item.getApi_no());
            String url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.png", folder, filename);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
            
            Api_mst_mapbgm api_mst_mapbgm = hashmap.get(item.getApi_id());
            
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setFilename(filename);
            downloadStatus.setId(item.getApi_id());
            downloadStatus.setName(item.getApi_name());
            downloadStatus.setUrl(url);
            downloadStatus.setPath(path);
            downloadStatus.setIsNew(isNew);
            if(fileDataEntity != null){
                fileDataEntity.setItemid(item.getApi_id());
                fileDataEntity.setFilename(filename+".png");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.png", prePath, filename));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Mapinfo);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            downloadResult.get(FileTypes.Mapinfo).add(downloadStatus);
        });
        if(!dblist.isEmpty())            
            fileDataService.insertSelected(dblist);
    }
    
    private int downloadNewMapbgm(List<Api_mst_mapbgm> list){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/resources/bgm/battle";
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            if(item.getApi_map_bgm().get(0).equals(item.getApi_map_bgm().get(1))){
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_map_bgm().get(0), version, true);
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            } else {
                IntStream.range(0, 1).forEach(index -> {
                    FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_map_bgm().get(index), version, true);
                    if(fileDataEntity != null){
                        dblist.add(fileDataEntity);
                    }
                });
            }
            
            if(item.getApi_boss_bgm().get(0).equals(item.getApi_boss_bgm().get(1))){
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_boss_bgm().get(0), version, true);
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            } else {
                IntStream.range(0, 1).forEach(index -> {
                    FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_boss_bgm().get(index), version, true);
                    if(fileDataEntity != null){
                        dblist.add(fileDataEntity);
                    }
                });
            }
            
            if(item.getApi_moving_bgm() > 0){
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_moving_bgm(), version, true);
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            }
            
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    private int downloadModifiedMapbgm(Map<Integer, List<Integer>> map){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/resources/bgm/battle";
        int version = (int) (Math.random()*90 + 10);
        map.forEach((k, list) -> {
            IntStream.range(0, 4).forEach(index -> {
                if(list.get(index).equals(-1)){
                    return;
                }
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(null, prePath, list.get(index), version, false);
                if(index == 4){ //api_moving_bgm
                    
                }
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            });
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    private FileDataEntity getMapbgmFileDataEntity(Api_mst_mapbgm item, String prePath, int id, int version, boolean isNew){
        String obfsname = BaseUrl.getItemUrl("bgm", id, "battle");
        String url = String.format("%s/%s/%s.mp3?version=%s", host, prePath, obfsname, version);
        String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
        String path = String.format("%s/%s.mp3", folder, obfsname);
        FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, obfsname+".mp3", requestConfig);
        int itemid = (item != null)? item.getApi_id(): -1;
        DownloadStatus downloadStatus = new DownloadStatus();
        downloadStatus.setFilename(obfsname);
        downloadStatus.setId(itemid);
        downloadStatus.setName(obfsname);
        downloadStatus.setUrl(url);
        downloadStatus.setPath(path);
        downloadStatus.setIsNew(isNew);
        if(fileDataEntity != null){
            fileDataEntity.setItemid(itemid);
            fileDataEntity.setFilename(obfsname+".mp3");
            fileDataEntity.setHash(CommontUtils.getFileHex(path));
            fileDataEntity.setPath(String.format("%s/%s.mp3", prePath, obfsname));
            fileDataEntity.setTimestamp(date);
            fileDataEntity.setType(FileTypes.Mapbgm);
            downloadStatus.setHash(fileDataEntity.getHash());
            downloadStatus.setIsSuccess(true);
        }
        downloadResult.get(FileTypes.Mapbgm).add(downloadStatus);
        return fileDataEntity;
    }
    
    private int downloadBgm(List<Api_mst_bgm> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/resources/bgm/port";
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            String obfsname = BaseUrl.getItemUrl("bgm", item.getApi_id(), "port");
            String url = String.format("%s/%s/%s.mp3?version=%s", host, prePath, obfsname, version);
            String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
            String path = String.format("%s/%s.mp3", folder, obfsname);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, obfsname+".mp3", requestConfig);
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setFilename(obfsname);
            downloadStatus.setId(item.getApi_id());
            downloadStatus.setName(item.getApi_name());
            downloadStatus.setUrl(url);
            downloadStatus.setPath(path);
            downloadStatus.setIsNew(isNew);
            if(fileDataEntity != null){
                fileDataEntity.setItemid(item.getApi_id());
                fileDataEntity.setFilename(obfsname+".mp3");
                fileDataEntity.setHash(CommontUtils.getFileHex(path));
                fileDataEntity.setPath(String.format("%s/%s.mp3", prePath, obfsname));
                fileDataEntity.setTimestamp(date);
                fileDataEntity.setType(FileTypes.Bgm);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            downloadResult.get(FileTypes.Bgm).add(downloadStatus);
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    public int downloadVoice(List<CombinedShipEntity> list, boolean isNew){
        List<FileDataEntity> dblist = new ArrayList<>();
        list.forEach(item -> {
            int shipID = item.getApi_id();
            if(shipID >= 1500){return;}
            List<String>  memberList = new ArrayList<>();
            String voiceName;
            RequestConfig rc = requestConfig;
            String prePath = String.format("kcs/sound/kc%s", item.getApi_filename());
            for (int i=1; i<KeyArrayValue.vcKey.length; i++){
//                    voiceName=String.valueOf((shipID + 7) * 17 * (KeyArrayValue.vcKey[i] - KeyArrayValue.vcKey[i - 1]) % 99173 + 100000);
                    voiceName=String.valueOf(17 * (shipID + 7) * KeyArrayValue.vcKey[i - 1] % 99173 + 100000);
                    memberList.add(voiceName);
            }
            memberList.forEach(_voiceName -> {
                String url = String.format("%s/%s/%s.mp3", host, prePath, _voiceName);
                String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
                String path = String.format("%s/%s.mp3", folder, _voiceName);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, _voiceName+".mp3", rc);
                if(fileDataEntity != null){
                    fileDataEntity.setItemid(shipID);
                        fileDataEntity.setFilename(_voiceName+".mp3");
                        fileDataEntity.setHash(CommontUtils.getFileHex(path));
                        fileDataEntity.setPath(String.format("%s/%s.mp3", prePath, _voiceName));
                        fileDataEntity.setTimestamp(date);
                        fileDataEntity.setType(FileTypes.ShipVoice);
                        dblist.add(fileDataEntity);
                }
            });
        });
        int rs = 0;
        if(!dblist.isEmpty())            
            rs = fileDataService.insertSelected(dblist);
        return rs;
    }
    
    public boolean downloadCore(){
        String prePath = "kcs";
        String filename = "Core";
        String url = String.format("%s/%s/%s.png", host, prePath, filename);
        String folder = String.format("%s/%s", runtimeValue.DOWNLOAD_FOLDER, prePath);
        String path = String.format("%s/%s.png", folder, filename);
        FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig);
        
        
        return false;
    }
    
    
}
