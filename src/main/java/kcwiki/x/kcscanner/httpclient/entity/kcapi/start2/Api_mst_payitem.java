/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

/**
 *
 * @author x5171
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
public class Api_mst_payitem
{
    @EqualsExclude
    private int api_id;
    private int api_type;
    private String api_name;
    private String api_description;
    private String api_shop_description;
    private List<Integer> api_item;
    private int api_price;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_payitem)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.getApi_type());
        hash = 89 * hash + Objects.hashCode(this.getApi_name());
        hash = 89 * hash + Objects.hashCode(this.getApi_description());
        hash = 89 * hash + Objects.hashCode(this.getApi_shop_description());
        hash = 89 * hash + Objects.hashCode(this.getApi_item());
        hash = 89 * hash + Objects.hashCode(this.getApi_price());
        return hash;
    }

    /**
     * @return the api_id
     */
    public int getApi_id() {
        return api_id;
    }

    /**
     * @param api_id the api_id to set
     */
    public void setApi_id(int api_id) {
        this.api_id = api_id;
    }

    /**
     * @return the api_type
     */
    public int getApi_type() {
        return api_type;
    }

    /**
     * @param api_type the api_type to set
     */
    public void setApi_type(int api_type) {
        this.api_type = api_type;
    }

    /**
     * @return the api_name
     */
    public String getApi_name() {
        return api_name;
    }

    /**
     * @param api_name the api_name to set
     */
    public void setApi_name(String api_name) {
        this.api_name = api_name;
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
     * @return the api_shop_description
     */
    public String getApi_shop_description() {
        return api_shop_description;
    }

    /**
     * @param api_shop_description the api_shop_description to set
     */
    public void setApi_shop_description(String api_shop_description) {
        this.api_shop_description = api_shop_description;
    }

    /**
     * @return the api_item
     */
    public List<Integer> getApi_item() {
        return api_item;
    }

    /**
     * @param api_item the api_item to set
     */
    public void setApi_item(List<Integer> api_item) {
        this.api_item = api_item;
    }

    /**
     * @return the api_price
     */
    public int getApi_price() {
        return api_price;
    }

    /**
     * @param api_price the api_price to set
     */
    public void setApi_price(int api_price) {
        this.api_price = api_price;
    }

}
