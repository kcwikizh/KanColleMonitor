/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket.types;

/**
 *
 * @author iHaru
 */
public enum ModuleType {
    SystemInfo("系统消息"),
    AutoLogin("自动登陆"),
    UploadStart2("自动上传Start2"),
    CoreFilesProcessor("文件处理"),
    Start2Download("Start2 文件下载"),
    ManualFileScan("手动文件扫描"),
    AutoFileScan("自动文件扫描"),
    DownloadResult("下载文件列表"),
    DownloadLog("下载文件记录")
    ;
    
    private String msg;
    
    ModuleType(String msg){
        this.msg = msg;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
}
