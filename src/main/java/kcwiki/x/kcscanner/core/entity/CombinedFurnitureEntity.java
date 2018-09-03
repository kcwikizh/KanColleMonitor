/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;

/**
 *
 * @author x5171
 */
public class CombinedFurnitureEntity {
    @EqualsExclude
    private Integer api_id;

    private Integer api_type;
    @EqualsExclude
    private Integer api_no;

    private String api_title;

    private String api_description;

    private Integer api_rarity;

    private Integer api_price;

    private Integer api_saleflg;

    private Integer api_season;

    private String api_filename;

    private String api_version;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof CombinedFurnitureEntity)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.api_type);
        hash = 89 * hash + Objects.hashCode(this.api_title);
        hash = 89 * hash + Objects.hashCode(this.api_description);
        hash = 89 * hash + Objects.hashCode(this.api_rarity);
        hash = 89 * hash + Objects.hashCode(this.api_price);
        hash = 89 * hash + Objects.hashCode(this.api_saleflg);
        hash = 89 * hash + Objects.hashCode(this.api_season);
        hash = 89 * hash + Objects.hashCode(this.api_filename);
        hash = 89 * hash + Objects.hashCode(this.api_version);
        return hash;
    }

    /**
     * @return the api_id
     */
    public Integer getApi_id() {
        return api_id;
    }

    /**
     * @param api_id the api_id to set
     */
    public void setApi_id(Integer api_id) {
        this.api_id = api_id;
    }

    /**
     * @return the api_type
     */
    public Integer getApi_type() {
        return api_type;
    }

    /**
     * @param api_type the api_type to set
     */
    public void setApi_type(Integer api_type) {
        this.api_type = api_type;
    }

    /**
     * @return the api_no
     */
    public Integer getApi_no() {
        return api_no;
    }

    /**
     * @param api_no the api_no to set
     */
    public void setApi_no(Integer api_no) {
        this.api_no = api_no;
    }

    /**
     * @return the api_title
     */
    public String getApi_title() {
        return api_title;
    }

    /**
     * @param api_title the api_title to set
     */
    public void setApi_title(String api_title) {
        this.api_title = api_title;
    }

    /**
     * @return the api_description
     */
    public String getApi_description() {
        return api_description;
    }

    /**
     * @param api_description the api_description to set
     */
    public void setApi_description(String api_description) {
        this.api_description = api_description;
    }

    /**
     * @return the api_rarity
     */
    public Integer getApi_rarity() {
        return api_rarity;
    }

    /**
     * @param api_rarity the api_rarity to set
     */
    public void setApi_rarity(Integer api_rarity) {
        this.api_rarity = api_rarity;
    }

    /**
     * @return the api_price
     */
    public Integer getApi_price() {
        return api_price;
    }

    /**
     * @param api_price the api_price to set
     */
    public void setApi_price(Integer api_price) {
        this.api_price = api_price;
    }

    /**
     * @return the api_saleflg
     */
    public Integer getApi_saleflg() {
        return api_saleflg;
    }

    /**
     * @param api_saleflg the api_saleflg to set
     */
    public void setApi_saleflg(Integer api_saleflg) {
        this.api_saleflg = api_saleflg;
    }

    /**
     * @return the api_season
     */
    public Integer getApi_season() {
        return api_season;
    }

    /**
     * @param api_season the api_season to set
     */
    public void setApi_season(Integer api_season) {
        this.api_season = api_season;
    }

    /**
     * @return the api_filename
     */
    public String getApi_filename() {
        return api_filename;
    }

    /**
     * @param api_filename the api_filename to set
     */
    public void setApi_filename(String api_filename) {
        this.api_filename = api_filename;
    }

    /**
     * @return the api_version
     */
    public String getApi_version() {
        return api_version;
    }

    /**
     * @param api_version the api_version to set
     */
    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }
    
}