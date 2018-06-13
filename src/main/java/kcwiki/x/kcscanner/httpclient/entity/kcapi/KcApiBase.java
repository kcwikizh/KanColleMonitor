/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi;


/**
 *
 * @author x5171
 * @param <T>
 */
public class KcApiBase <T> {
    private int api_result;

    private String api_result_msg;

    private T api_data;

    /**
     * @return the api_result
     */
    public int getApi_result() {
        return api_result;
    }

    /**
     * @param api_result the api_result to set
     */
    public void setApi_result(int api_result) {
        this.api_result = api_result;
    }

    /**
     * @return the api_result_msg
     */
    public String getApi_result_msg() {
        return api_result_msg;
    }

    /**
     * @param api_result_msg the api_result_msg to set
     */
    public void setApi_result_msg(String api_result_msg) {
        this.api_result_msg = api_result_msg;
    }

    /**
     * @return the api_data
     */
    public T getApi_data() {
        return api_data;
    }

    /**
     * @param api_data the api_data to set
     */
    public void setApi_data(T api_data) {
        this.api_data = api_data;
    }
}
