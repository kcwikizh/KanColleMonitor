/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import static kcwiki.x.kcscanner.tools.ConstantValue.FILESEPARATOR;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMP_FOLDER;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class FileScanner {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FileScanner.class);
    
    public List<FileDataEntity> preScan(String listfile) {
        List<String> lines = new ArrayList<>();
        List<FileDataEntity> result = new ArrayList<>();
        try {
            List<String> _lines = Files.readAllLines(Paths.get(listfile), StandardCharsets.UTF_8);
            lines = _lines.stream()
                    .filter(line -> !StringUtils.isBlank(line))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            LOG.error("preScan - 读取{}文件时失败。", listfile);
        }
        String tempFolder = String.format("%s%s%s", TEMP_FOLDER, FILESEPARATOR, "prescan");
        if(!new File(tempFolder).exists())
                new File(tempFolder).mkdirs();
        String url;
        File _file;
        String host = AppDataCache.appConfigs.getKcserver_host();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        RequestConfig requestConfig = HttpClientConfig.makeProxyConfig(false);
        for(String line:lines) {
            url = String.format("http://%s/%s", host, line);
            _file = new File(url);
            FileDataEntity fileDataEntity = HttpUtils.scanFile(url, closeableHttpClient, requestConfig, url2path(line), _file.getName());
            if(fileDataEntity == null)
                continue;
            fileDataEntity.setFilename(_file.getName());
            fileDataEntity.setPath(line);
            result.add(fileDataEntity);
        }
        return result;
    }
    
    public List<FileDataEntity> fileScan(List<FileDataEntity> list) {
        List<FileDataEntity> result = new ArrayList<>();
        
        String tempFolder = String.format("%s%s%s", TEMP_FOLDER, FILESEPARATOR, "prescan");
        if(!new File(tempFolder).exists())
                new File(tempFolder).mkdirs();
        String url;
        File _file;
        String host = AppDataCache.appConfigs.getKcserver_host();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        RequestConfig requestConfig = HttpClientConfig.makeProxyConfig(false);
        for(FileDataEntity _fileDataEntity:list) {
            url = String.format("http://%s/%s", host, _fileDataEntity.getPath());
            _file = new File(url);
            boolean isModified = HttpUtils.checkModified(url, _fileDataEntity.getLastmodified(), closeableHttpClient, requestConfig);
            if(!isModified)
                continue;
            FileDataEntity fileDataEntity = HttpUtils.scanFile(url, closeableHttpClient, requestConfig, url2path(_fileDataEntity.getPath()), _file.getName());
            if(fileDataEntity == null)
                continue;
            fileDataEntity.setFilename(_file.getName());
            fileDataEntity.setPath(_fileDataEntity.getPath());
            result.add(fileDataEntity);
        }
        return result;
    }
    
    private String url2path(String url) {
        File file = new File(url);
        return file.getParent();
    }
    
}
