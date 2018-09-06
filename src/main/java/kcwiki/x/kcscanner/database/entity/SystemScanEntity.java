/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.entity;

import java.util.Date;

/**
 *
 * @author x5171
 */
public class SystemScanEntity {
    private Integer id;
    private String name;
    private String timeline;
    private Integer init;
    private Date lastmodified;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the timeline
     */
    public String getTimeline() {
        return timeline;
    }

    /**
     * @param timeline the timeline to set
     */
    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    /**
     * @return the init
     */
    public Integer getInit() {
        return init;
    }

    /**
     * @param init the init to set
     */
    public void setInit(Integer init) {
        this.init = init;
    }

    /**
     * @return the lastmodified
     */
    public Date getLastmodified() {
        return lastmodified;
    }

    /**
     * @param lastmodified the lastmodified to set
     */
    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }
}
