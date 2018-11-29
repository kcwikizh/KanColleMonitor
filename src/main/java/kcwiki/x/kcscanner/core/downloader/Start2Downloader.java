/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.downloader;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
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
import kcwiki.x.kcscanner.types.FileType;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
@Scope("prototype")
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
    private String downloadFolder;
    private boolean seasonal = false;
    private Map<Integer, String> gameid2wikiid = null;
    
    Date date = new Date();
    String host;
    private Map<FileType, List<DownloadStatus>> downloadResult = new ConcurrentHashMap();
    private Map<FileType, List<FileDataEntity>> fileResult = new ConcurrentHashMap();
    
    @PostConstruct
    public void initMethod() {
        host = AppDataCache.kcHost;
        downloadFolder = runtimeValue.DOWNLOAD_FOLDER;
        if(StringUtils.isBlank(host))
            LOG.error("KcServer地址为空");
        if(appConfigs.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
    }
    
    
    public void download(Start2PatchEntity start2PatchEntity, boolean isDownloadVoice) {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        List<CompletableFuture[]> asyncResults = new ArrayList();
        if(start2PatchEntity == null)
            return;
        if(!start2PatchEntity.getNewShip().isEmpty()){
            if(!downloadResult.containsKey(FileType.Ship)){
                getDownloadResult().put(FileType.Ship, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Ship)){
                getFileResult().put(FileType.Ship, new ArrayList());
            }
            List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getNewShip(), 5);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadShip(item, true), executorService)
//                                .thenApply(h->h)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Ship).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
//            CompletableFuture[] cfs =  {CompletableFuture.supplyAsync(() -> downloadShip(_list.get(0), true), executorService)};
//            CompletableFuture.allOf(cfs).join();
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedShip().isEmpty()){
            if(!downloadResult.containsKey(FileType.Ship)){
                getDownloadResult().put(FileType.Ship, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Ship)){
                getFileResult().put(FileType.Ship, new ArrayList());
            }
            List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getModifiedShip(), 5);
            CompletableFuture[] cfs = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadShip(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Ship).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!start2PatchEntity.getNewSlotitem().isEmpty()){
            if(!downloadResult.containsKey(FileType.Slotitem)){
                getDownloadResult().put(FileType.Slotitem, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Slotitem)){
                getFileResult().put(FileType.Slotitem, new ArrayList());
            }
            List<List<Api_mst_slotitem>> _list = Lists.partition(start2PatchEntity.getNewSlotitem(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadSlotitem(item, true), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Slotitem).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedSlotitem().isEmpty()){
            if(!downloadResult.containsKey(FileType.Slotitem)){
                getDownloadResult().put(FileType.Slotitem, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Slotitem)){
                getFileResult().put(FileType.Slotitem, new ArrayList());
            }
            List<List<Api_mst_slotitem>> _list = Lists.partition(start2PatchEntity.getModifiedSlotitem(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadSlotitem(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Slotitem).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!start2PatchEntity.getNewFurniture().isEmpty()){
            if(!downloadResult.containsKey(FileType.Furniture)){
                getDownloadResult().put(FileType.Furniture, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Furniture)){
                getFileResult().put(FileType.Furniture, new ArrayList());
            }
            List<List<CombinedFurnitureEntity>> _list = Lists.partition(start2PatchEntity.getNewFurniture(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadFurniture(item, true), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Furniture).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedFurniture().isEmpty()){
            if(!downloadResult.containsKey(FileType.Furniture)){
                getDownloadResult().put(FileType.Furniture, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Furniture)){
                getFileResult().put(FileType.Furniture, new ArrayList());
            }
            List<List<CombinedFurnitureEntity>> _list = Lists.partition(start2PatchEntity.getModifiedFurniture(), 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadFurniture(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Furniture).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        
        if(!start2PatchEntity.getNewUseitem().isEmpty()){
            if(!downloadResult.containsKey(FileType.Useitem)){
                getDownloadResult().put(FileType.Useitem, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Useitem)){
                getFileResult().put(FileType.Useitem, new ArrayList());
            }
            getFileResult().get(FileType.Useitem).addAll(downloadUseitem(start2PatchEntity.getNewUseitem(), true));
        }
        if(!start2PatchEntity.getModifiedUseitem().isEmpty()){
            if(!downloadResult.containsKey(FileType.Useitem)){
                getDownloadResult().put(FileType.Useitem, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Useitem)){
                getFileResult().put(FileType.Useitem, new ArrayList());
            }
            getFileResult().get(FileType.Useitem).addAll(downloadUseitem(start2PatchEntity.getModifiedUseitem(), false));
        }
        
        if(!start2PatchEntity.getNewPayitem().isEmpty()){
            if(!downloadResult.containsKey(FileType.Payitem)){
                getDownloadResult().put(FileType.Payitem, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Payitem)){
                getFileResult().put(FileType.Payitem, new ArrayList());
            }
            getFileResult().get(FileType.Payitem).addAll(downloadPayitem(start2PatchEntity.getNewPayitem(), true));
        }
        if(!start2PatchEntity.getModifiedPayitem().isEmpty()){
            if(!downloadResult.containsKey(FileType.Payitem)){
                getDownloadResult().put(FileType.Payitem, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Payitem)){
                getFileResult().put(FileType.Payitem, new ArrayList());
            }
            getFileResult().get(FileType.Payitem).addAll(downloadPayitem(start2PatchEntity.getModifiedPayitem(), false));
        }
        
        if(!start2PatchEntity.getNewMapinfo().isEmpty()){
            if(!downloadResult.containsKey(FileType.Mapinfo)){
                getDownloadResult().put(FileType.Mapinfo, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Mapinfo)){
                getFileResult().put(FileType.Mapinfo, new ArrayList());
            }
            getFileResult().get(FileType.Mapinfo).addAll(downloadMapinfo(start2PatchEntity.getNewMapinfo(), start2PatchEntity.getAllMapbgm(), true));
        }
        if(!start2PatchEntity.getModifiedMapinfo().isEmpty()) {
            if(!downloadResult.containsKey(FileType.Mapinfo)){
                getDownloadResult().put(FileType.Mapinfo, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Mapinfo)){
                getFileResult().put(FileType.Mapinfo, new ArrayList());
            }
            getFileResult().get(FileType.Mapinfo).addAll(downloadMapinfo(start2PatchEntity.getModifiedMapinfo(), start2PatchEntity.getAllMapbgm(), false));
        }
        
        if(!start2PatchEntity.getNewMapbgm().isEmpty()){
            if(!downloadResult.containsKey(FileType.Mapbgm)){
                getDownloadResult().put(FileType.Mapbgm, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Mapbgm)){
                getFileResult().put(FileType.Mapbgm, new ArrayList());
            }
            List<Api_mst_mapbgm> temp = new ArrayList();
            start2PatchEntity.getNewMapbgm().forEach((k, v) -> temp.add(v));
            
            List<List<Api_mst_mapbgm>> _list = Lists.partition(temp, 5);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadNewMapbgm(item), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Mapbgm).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedMapbgm().isEmpty()){
            if(!downloadResult.containsKey(FileType.Mapbgm)){
                getDownloadResult().put(FileType.Mapbgm, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Mapbgm)){
                getFileResult().put(FileType.Mapbgm, new ArrayList());
            }
            getFileResult().get(FileType.Mapbgm).addAll(downloadModifiedMapbgm(start2PatchEntity.getModifiedMapbgm()));
        }
        
        if(!start2PatchEntity.getNewBgm().isEmpty()){
            if(!downloadResult.containsKey(FileType.Bgm)){
                getDownloadResult().put(FileType.Bgm, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Bgm)){
                getFileResult().put(FileType.Bgm, new ArrayList());
            }
            List<List<Api_mst_bgm>> _list = Lists.partition(start2PatchEntity.getNewBgm(), 5);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadBgm(item, true), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Bgm).addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
            asyncResults.add(cfs);
        }
        if(!start2PatchEntity.getModifiedBgm().isEmpty()){
            if(!downloadResult.containsKey(FileType.Bgm)){
                getDownloadResult().put(FileType.Bgm, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.Bgm)){
                getFileResult().put(FileType.Bgm, new ArrayList());
            }
            List<List<Api_mst_bgm>> _list = Lists.partition(start2PatchEntity.getModifiedBgm(), 5);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadBgm(item, false), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    getFileResult().get(FileType.Bgm).addAll(s);
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
        
        if(AppDataCache.isDownloadShipVoice){
            if(!downloadResult.containsKey(FileType.ShipVoice)){
                getDownloadResult().put(FileType.ShipVoice, new ArrayList());
            }
            if(!fileResult.containsKey(FileType.ShipVoice)){
                getFileResult().put(FileType.ShipVoice, new ArrayList());
            }
            if(!start2PatchEntity.getNewShip().isEmpty()) {
//                downloadResult.put(FileTypes.ShipVoice, (Map<Boolean, List<DownloadStatus>>) (new ConcurrentHashMap()).put(true, (new ArrayList())));
                List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getNewShip(), 5);
                CompletableFuture[] cfs = _list.stream()
                    .map(item -> CompletableFuture.supplyAsync(() -> downloadVoice(item, true), executorService)
                                    .whenComplete((s, e) -> {
                                        System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                        getFileResult().get(FileType.ShipVoice).addAll(s);
                                    })
                ).toArray(CompletableFuture[]::new);
                asyncResults.add(cfs);
            }
            if(!start2PatchEntity.getModifiedShip().isEmpty()) {
//                downloadResult.put(FileTypes.ShipVoice, (Map<Boolean, List<DownloadStatus>>) (new ConcurrentHashMap()).put(false, (new ArrayList())));
                List<List<CombinedShipEntity>> _list = Lists.partition(start2PatchEntity.getModifiedShip(), 5);
                CompletableFuture[] cfs = _list.stream()
                    .map(item -> CompletableFuture.supplyAsync(() -> downloadVoice(item, false), executorService)
                                    .whenComplete((s, e) -> {
                                        System.out.println("任务"+s.size()+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                        getFileResult().get(FileType.ShipVoice).addAll(s);
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
    
    private List<FileDataEntity> downloadShip(List<CombinedShipEntity> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
        list.forEach(item -> {
            for(BaseStart2Enum type:ShipTypes.values()){
                if(type.getTypeName().contains("card_round") || type.getTypeName().contains("icon_box"))
                    continue;
                String urlPath = "kcs2/resources/ship/" + type.getTypeName();
                String filePath = "kcs2/resources/ship/" + parseString(item.getApi_name());
                String obfsname = ShipUtils.getPath(item.getApi_id(), type.getTypeName());
                if(obfsname == null){
                    continue;
                }
                if(seasonal && (type == ShipTypes.Full || type == ShipTypes.FullDmg))
                    obfsname = obfsname + "_" + item.getApi_filename();
//                String realname = ShipUtils.getWikiFileName(String.format("%03d", item.getApi_sortno()), type.getTypeName());
                String realname ;
                if(gameid2wikiid != null){
                    realname = ShipUtils.getWikiFileName(item.getApi_id(), gameid2wikiid.get(item.getApi_id()), type.getTypeName());
                } else {
                    realname = ShipUtils.getWikiFileName(item.getApi_id(), null, type.getTypeName());
                }
                if(realname == null) {
                    realname = obfsname;
                    filePath += "/others";
                }
//                realname = parseString(realname);
                String url = String.format("%s/%s/%s.png?version=%s", host, urlPath, obfsname, Integer.valueOf(item.getApi_version().get(0)));
                String folder = String.format("%s/%s", downloadFolder, filePath);
                String path = String.format("%s/%s.png", folder, realname);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, realname+".png", requestConfig, closeableHttpClient);
                DownloadStatus downloadStatus = new DownloadStatus();
                downloadStatus.setParentPath(filePath);
                downloadStatus.setFilename(realname+".png");
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
                    fileDataEntity.setType(FileType.Ship);
                    dblist.add(fileDataEntity);
                    downloadStatus.setHash(fileDataEntity.getHash());
                    downloadStatus.setIsSuccess(true);
                }
                resultlist.add(downloadStatus);
            }
        });
        getDownloadResult().get(FileType.Ship).addAll(resultlist);
        return dblist;
    }
    
    private List<FileDataEntity> downloadSlotitem(List<Api_mst_slotitem> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
        list.forEach(item -> {
            for(BaseStart2Enum type:SlotTypes.values()){
                String urlPath = "kcs2/resources/slot/" + type.getTypeName();
                String filePath = "kcs2/resources/slot/" + parseString(item.getApi_name());
                String obfsname = SlotUtils.getPath(item.getApi_id(), type.getTypeName());
                if(obfsname == null)
                    return;
                String realname = SlotUtils.getWikiFileName(String.format("%03d", item.getApi_sortno()), type.getTypeName());
                if(realname == null) {
                    realname = obfsname;
                    filePath += "/others";
                }
//                realname = parseString(realname);
                String url = String.format("%s/%s/%s.png?version=%s", host, urlPath, obfsname, item.getApi_version());
                String folder = String.format("%s/%s", downloadFolder, filePath);
                String path = String.format("%s/%s.png", folder, realname);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, realname+".png", requestConfig, closeableHttpClient);
                DownloadStatus downloadStatus = new DownloadStatus();
                downloadStatus.setParentPath(filePath);
                downloadStatus.setFilename(realname+".png");
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
                    fileDataEntity.setType(FileType.Slotitem);
                    dblist.add(fileDataEntity);
                    downloadStatus.setHash(fileDataEntity.getHash());
                    downloadStatus.setIsSuccess(true);
                }
                resultlist.add(downloadStatus);
            }
        });
        getDownloadResult().get(FileType.Slotitem).addAll(resultlist);
        return dblist;
    }
    
    private List<FileDataEntity> downloadFurniture(List<CombinedFurnitureEntity> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
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
//            obfsname = parseString(obfsname);
            String url = String.format("%s/%s/%s.png?version=%s", host, prePath, obfsname, item.getApi_version());
            String folder = String.format("%s/%s", downloadFolder, prePath);
            String path = String.format("%s/%s.png", folder, obfsname);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, obfsname+".png", requestConfig, closeableHttpClient);
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setParentPath(prePath);
            downloadStatus.setFilename(obfsname+".png");
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
                fileDataEntity.setType(FileType.Furniture);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            resultlist.add(downloadStatus);
        });
        getDownloadResult().get(FileType.Furniture).addAll(resultlist);
        return dblist;
    }
    
    private List<FileDataEntity> downloadUseitem(List<Api_mst_useitem> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            String prePath = "kcs2/resources/useitem/card_";
            if(StringUtils.isBlank(item.getApi_name()))
                return;
            String filename = String.format("%03d", item.getApi_id());
            String url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
            String folder = String.format("%s/%s", downloadFolder, prePath);
            String path = String.format("%s/%s.png", folder, filename);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig, closeableHttpClient);
            if(fileDataEntity == null) {
                prePath = "kcs2/resources/useitem/card";
                url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
                folder = String.format("%s/%s", downloadFolder, prePath);
                path = String.format("%s/%s.png", folder, filename);
                fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig, closeableHttpClient);
            }
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setParentPath(prePath);
            downloadStatus.setFilename(filename+".png");
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
                fileDataEntity.setType(FileType.Useitem);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            resultlist.add(downloadStatus);
        });
        getDownloadResult().get(FileType.Useitem).addAll(resultlist);
        return dblist;
    }
    
    private List<FileDataEntity> downloadPayitem(List<Api_mst_payitem> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/img/item";
        int version = (int) (Math.random()*90 + 10);
        String filename = "item_payitemicon";
        String url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
        String folder = String.format("%s/%s", downloadFolder, prePath);
        String path = String.format("%s/%s.png", folder, filename);
        FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig, closeableHttpClient);
        DownloadStatus downloadStatus = new DownloadStatus();
        downloadStatus.setParentPath(prePath);
        downloadStatus.setFilename(filename+".png");
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
            fileDataEntity.setType(FileType.Payitem);
            dblist.add(fileDataEntity);
            downloadStatus.setHash(fileDataEntity.getHash());
            downloadStatus.setIsSuccess(true);
        }
        getDownloadResult().get(FileType.Payitem).add(downloadStatus);
        return dblist;
    }
    
    private List<FileDataEntity> downloadMapinfo(List<Api_mst_mapinfo> list, Map<Integer, Api_mst_mapbgm> hashmap, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            String prePath = "kcs2/resources/map/" + String.format("%03d", item.getApi_maparea_id());
            String filename = String.format("%02d_image", item.getApi_no());
            String url = String.format("%s/%s/%s.png?version=%s", host, prePath, filename, version);
            String folder = String.format("%s/%s", downloadFolder, prePath);
            String path = String.format("%s/%s.png", folder, filename);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename+".png", requestConfig, closeableHttpClient);
            
            Api_mst_mapbgm api_mst_mapbgm = hashmap.get(item.getApi_id());
            
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setParentPath(prePath);
            downloadStatus.setFilename(filename+".png");
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
                fileDataEntity.setType(FileType.Mapinfo);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            resultlist.add(downloadStatus);
        });
        getDownloadResult().get(FileType.Mapinfo).addAll(resultlist);
        return dblist;
    }
    
    private List<FileDataEntity> downloadNewMapbgm(List<Api_mst_mapbgm> list){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/resources/bgm/battle";
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            if(item.getApi_map_bgm().get(0).equals(item.getApi_map_bgm().get(1))){
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_map_bgm().get(0), version, true, closeableHttpClient);
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            } else {
                IntStream.range(0, 1).forEach(index -> {
                    FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_map_bgm().get(index), version, true, closeableHttpClient);
                    if(fileDataEntity != null){
                        dblist.add(fileDataEntity);
                    }
                });
            }
            
            if(item.getApi_boss_bgm().get(0).equals(item.getApi_boss_bgm().get(1))){
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_boss_bgm().get(0), version, true, closeableHttpClient);
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            } else {
                IntStream.range(0, 1).forEach(index -> {
                    FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_boss_bgm().get(index), version, true, closeableHttpClient);
                    if(fileDataEntity != null){
                        dblist.add(fileDataEntity);
                    }
                });
            }
            
            if(item.getApi_moving_bgm() > 0){
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(item, prePath, item.getApi_moving_bgm(), version, true, closeableHttpClient);
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            }
            
        });
        return dblist;
    }
    
    private List<FileDataEntity> downloadModifiedMapbgm(Map<Integer, List<Integer>> map){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        String prePath = "kcs2/resources/bgm/battle";
        int version = (int) (Math.random()*90 + 10);
        map.forEach((k, list) -> {
            IntStream.range(0, 4).forEach(index -> {
                if(list.get(index).equals(-1)){
                    return;
                }
                FileDataEntity fileDataEntity = getMapbgmFileDataEntity(null, prePath, list.get(index), version, false, closeableHttpClient);
                if(index == 4){ //api_moving_bgm
                    
                }
                if(fileDataEntity != null){
                    dblist.add(fileDataEntity);
                }
            });
        });
        return dblist;
    }
    
    private FileDataEntity getMapbgmFileDataEntity(Api_mst_mapbgm item, String prePath, int id, int version, boolean isNew, CloseableHttpClient closeableHttpClient){
        String obfsname = BaseUrl.getItemUrl("bgm", id, "battle");
        String url = String.format("%s/%s/%s.mp3?version=%s", host, prePath, obfsname, version);
        String folder = String.format("%s/%s", downloadFolder, prePath);
        String path = String.format("%s/%s.mp3", folder, obfsname);
        FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, obfsname+".mp3", requestConfig, closeableHttpClient);
        int itemid = (item != null)? item.getApi_id(): -1;
        DownloadStatus downloadStatus = new DownloadStatus();
        downloadStatus.setParentPath(prePath);
        downloadStatus.setFilename(obfsname+".mp3");
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
            fileDataEntity.setType(FileType.Mapbgm);
            downloadStatus.setHash(fileDataEntity.getHash());
            downloadStatus.setIsSuccess(true);
        }
        getDownloadResult().get(FileType.Mapbgm).add(downloadStatus);
        return fileDataEntity;
    }

    private List<FileDataEntity> downloadBgm(List<Api_mst_bgm> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
        String prePath = "kcs2/resources/bgm/port";
        int version = (int) (Math.random()*90 + 10);
        list.forEach(item -> {
            String obfsname = BaseUrl.getItemUrl("bgm", item.getApi_id(), "port");
            String url = String.format("%s/%s/%s.mp3?version=%s", host, prePath, obfsname, version);
            String folder = String.format("%s/%s", downloadFolder, prePath);
            String path = String.format("%s/%s.mp3", folder, obfsname);
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, obfsname+".mp3", requestConfig, closeableHttpClient);
            DownloadStatus downloadStatus = new DownloadStatus();
            downloadStatus.setParentPath(prePath);
            downloadStatus.setFilename(obfsname+".mp3");
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
                fileDataEntity.setType(FileType.Bgm);
                dblist.add(fileDataEntity);
                downloadStatus.setHash(fileDataEntity.getHash());
                downloadStatus.setIsSuccess(true);
            }
            resultlist.add(downloadStatus);
        });
        getDownloadResult().get(FileType.Bgm).addAll(resultlist);
        return dblist;
    }
    
    public List<FileDataEntity> downloadVoice(List<CombinedShipEntity> list, boolean isNew){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        List<FileDataEntity> dblist = new ArrayList<>();
        List<DownloadStatus> resultlist = new ArrayList<>();
        list.forEach(item -> {
            int shipID = item.getApi_id();
            if(shipID >= 1500){return;}
            List<String>  memberList = new ArrayList<>();
            String voiceName;
            String prePath = String.format("kcs/sound/kc%s", item.getApi_filename());
            for (int i=1; i<KeyArrayValue.vcKey.length; i++){
//                    voiceName=String.valueOf((shipID + 7) * 17 * (KeyArrayValue.vcKey[i] - KeyArrayValue.vcKey[i - 1]) % 99173 + 100000);
                    voiceName=String.valueOf(17 * (shipID + 7) * KeyArrayValue.vcKey[i - 1] % 99173 + 100000);
                    memberList.add(voiceName);
            }
            memberList.forEach(_voiceName -> {
                String url = String.format("%s/%s/%s.mp3", host, prePath, _voiceName);
                String folder = String.format("%s/%s", downloadFolder, prePath);
                String path = String.format("%s/%s.mp3", folder, _voiceName);
                FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, _voiceName+".mp3", requestConfig, closeableHttpClient);
                DownloadStatus downloadStatus = new DownloadStatus();
                downloadStatus.setParentPath(prePath);
                downloadStatus.setFilename(_voiceName+".mp3");
                downloadStatus.setId(item.getApi_id());
                downloadStatus.setName(item.getApi_name());
                downloadStatus.setUrl(url);
                downloadStatus.setPath(path);
                downloadStatus.setIsNew(isNew);
                if(fileDataEntity != null){
                    fileDataEntity.setItemid(shipID);
                    fileDataEntity.setFilename(_voiceName+".mp3");
                    fileDataEntity.setHash(CommontUtils.getFileHex(path));
                    fileDataEntity.setPath(String.format("%s/%s.mp3", prePath, _voiceName));
                    fileDataEntity.setTimestamp(date);
                    fileDataEntity.setType(FileType.ShipVoice);
                    dblist.add(fileDataEntity);
                    downloadStatus.setHash(fileDataEntity.getHash());
                    downloadStatus.setIsSuccess(true);
                }
                resultlist.add(downloadStatus);
            });
        });
        getDownloadResult().get(FileType.ShipVoice).addAll(resultlist);
        return dblist;
    } 
    
    private String parseString(String str){
        LOG.info("parseString: {}", str);
        return str.replaceAll("/", "2F").replaceAll("\\\\", "2F")
                .replaceAll("\\|", "7C")
                .replaceAll(":", "3A")
                .replaceAll("\\?", "3F")
                .replaceAll("\"", "22")
                .replaceAll("<", "3C")
                .replaceAll(">", "3E")
                .replaceAll("\\+", "2B")
//                .replaceAll("-", "")
//                .replaceAll("_", "")
                .replaceAll("&", "26")
                .replaceAll("#", "23")
//                .replaceAll("!", "")
                .replaceAll("'", "27")
                .replaceAll(" ", "+")
                ;
    }

    /**
     * @return the downloadResult
     */
    public Map<FileType, List<DownloadStatus>> getDownloadResult() {
        return downloadResult;
    }

    /**
     * @return the fileResult
     */
    public Map<FileType, List<FileDataEntity>> getFileResult() {
        return fileResult;
    }

    /**
     * @return the downloadFolder
     */
    public String getDownloadFolder() {
        return downloadFolder;
    }

    /**
     * @param downloadFolder the downloadFolder to set
     */
    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    /**
     * @param seasonal the seasonal to set
     */
    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    /**
     * @param gameid2wikiid the gameid2wikiid to set
     */
    public void setGameid2wikiid(Map<Integer, String> gameid2wikiid) {
        this.gameid2wikiid = gameid2wikiid;
    }
    
}
