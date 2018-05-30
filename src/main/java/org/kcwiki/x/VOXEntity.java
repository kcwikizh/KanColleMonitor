/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x;

/**
 *
 * @author x5171
 */
public class VOXEntity {
    private String title;
    private String url;
    private String sdate;
    private String date;
    private String source;
    private Integer attribute;
    private String content;
    private String rid;

    public VOXEntity (String rid, String title, String url, String sdate, String date, String source, Integer attribute, String content) {
        this.rid = rid;
        this.title = title;
        this.url = url;
        this.sdate = sdate;
        this.date = date;
        this.source = source;
        this.attribute = attribute;
        this.content = content;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the attribute
     */
    public Integer getAttribute() {
        return attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the rid
     */
    public String getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(String rid) {
        this.rid = rid;
    }

    /**
     * @return the sdate
     */
    public String getSdate() {
        return sdate;
    }

    /**
     * @param sdate the sdate to set
     */
    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
    
}
