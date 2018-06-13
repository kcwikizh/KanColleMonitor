/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.entity;

import java.util.Date;
import kcwiki.x.kcscanner.types.FileTypes;

/**
 *
 * @author x5171
 */
public class FileDataEntity {
    private Integer id;
    private String filename;
    private String path;
    private FileTypes type;
    private String lastmodified;
    private String hash; 
    private Date timestamp;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the type
     */
    public FileTypes getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FileTypes type) {
        this.type = type;
    }

    /**
     * @return the lastmodified
     */
    public String getLastmodified() {
        return lastmodified;
    }

    /**
     * @param lastmodified the lastmodified to set
     */
    public void setLastmodified(String lastmodified) {
        this.lastmodified = lastmodified;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
