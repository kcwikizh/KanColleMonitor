/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket.types;

/**
 *
 * @author x5171
 */
public enum WebsocketMessageType {
    KanColleScanner_System_Info("系统消息"),
    KanColleScanner_AutoLogin("自动登陆"),
    KanColleScanner_UploadStart2("自动上传Start2"),
    KanColleScanner_Core_FilesProcessor("文件处理"),
    KanColleScanner_Start2_Download("Start2 文件下载"),
    KanColleScanner_Manual_FileScan("手动文件扫描"),
    KanColleScanner_Auto_FileScan("自动文件扫描")
    ;
    
    private String msg;
    
    WebsocketMessageType(String msg){
        this.msg = msg;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
}
