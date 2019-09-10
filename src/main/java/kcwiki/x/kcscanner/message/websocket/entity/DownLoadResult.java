/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket.entity;

import java.util.List;
import kcwiki.x.kcscanner.types.FileType;

/**
 *
 * @author iHaru
 */
public class DownLoadResult {
    private FileType type;
    private List<String> filelist;

    /**
     * @return the type
     */
    public FileType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FileType type) {
        this.type = type;
    }

    /**
     * @return the filelist
     */
    public List<String> getFilelist() {
        return filelist;
    }

    /**
     * @param filelist the filelist to set
     */
    public void setFilelist(List<String> filelist) {
        this.filelist = filelist;
    }
}
