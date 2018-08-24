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
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
public class Api_mst_ship
{
    @EqualsExclude
    private int api_id;

    private int api_sortno;

    private int api_sort_id;

    private String api_name;

    private String api_yomi;

    private int api_stype;

    private int api_ctype;

    private int api_afterlv;

    private String api_aftershipid;

    private List<Integer> api_taik ;

    private List<Integer> api_souk ;

    private List<Integer> api_houg ;

    private List<Integer> api_raig ;

    private List<Integer> api_tyku ;

    private List<Integer> api_luck ;

    private int api_soku;

    private int api_leng;

    private int api_slot_num;

    private List<Integer> api_maxeq ;

    private int api_buildtime;

    private List<Integer> api_broken ;

    private List<Integer> api_powup ;

    private int api_backs;

    private String api_getmes;

    private int api_afterfuel;

    private int api_afterbull;

    private int api_fuel_max;

    private int api_bull_max;

    private int api_voicef;
    
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
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
     * @return the api_sortno
     */
    public int getApi_sortno() {
        return api_sortno;
    }

    /**
     * @param api_sortno the api_sortno to set
     */
    public void setApi_sortno(int api_sortno) {
        this.api_sortno = api_sortno;
    }

    /**
     * @return the api_sort_id
     */
    public int getApi_sort_id() {
        return api_sort_id;
    }

    /**
     * @param api_sort_id the api_sort_id to set
     */
    public void setApi_sort_id(int api_sort_id) {
        this.api_sort_id = api_sort_id;
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
     * @return the api_yomi
     */
    public String getApi_yomi() {
        return api_yomi;
    }

    /**
     * @param api_yomi the api_yomi to set
     */
    public void setApi_yomi(String api_yomi) {
        this.api_yomi = api_yomi;
    }

    /**
     * @return the api_stype
     */
    public int getApi_stype() {
        return api_stype;
    }

    /**
     * @param api_stype the api_stype to set
     */
    public void setApi_stype(int api_stype) {
        this.api_stype = api_stype;
    }

    /**
     * @return the api_ctype
     */
    public int getApi_ctype() {
        return api_ctype;
    }

    /**
     * @param api_ctype the api_ctype to set
     */
    public void setApi_ctype(int api_ctype) {
        this.api_ctype = api_ctype;
    }

    /**
     * @return the api_afterlv
     */
    public int getApi_afterlv() {
        return api_afterlv;
    }

    /**
     * @param api_afterlv the api_afterlv to set
     */
    public void setApi_afterlv(int api_afterlv) {
        this.api_afterlv = api_afterlv;
    }

    /**
     * @return the api_aftershipid
     */
    public String getApi_aftershipid() {
        return api_aftershipid;
    }

    /**
     * @param api_aftershipid the api_aftershipid to set
     */
    public void setApi_aftershipid(String api_aftershipid) {
        this.api_aftershipid = api_aftershipid;
    }

    /**
     * @return the api_taik
     */
    public List<Integer> getApi_taik() {
        return api_taik;
    }

    /**
     * @param api_taik the api_taik to set
     */
    public void setApi_taik(List<Integer> api_taik) {
        this.api_taik = api_taik;
    }

    /**
     * @return the api_souk
     */
    public List<Integer> getApi_souk() {
        return api_souk;
    }

    /**
     * @param api_souk the api_souk to set
     */
    public void setApi_souk(List<Integer> api_souk) {
        this.api_souk = api_souk;
    }

    /**
     * @return the api_houg
     */
    public List<Integer> getApi_houg() {
        return api_houg;
    }

    /**
     * @param api_houg the api_houg to set
     */
    public void setApi_houg(List<Integer> api_houg) {
        this.api_houg = api_houg;
    }

    /**
     * @return the api_raig
     */
    public List<Integer> getApi_raig() {
        return api_raig;
    }

    /**
     * @param api_raig the api_raig to set
     */
    public void setApi_raig(List<Integer> api_raig) {
        this.api_raig = api_raig;
    }

    /**
     * @return the api_tyku
     */
    public List<Integer> getApi_tyku() {
        return api_tyku;
    }

    /**
     * @param api_tyku the api_tyku to set
     */
    public void setApi_tyku(List<Integer> api_tyku) {
        this.api_tyku = api_tyku;
    }

    /**
     * @return the api_luck
     */
    public List<Integer> getApi_luck() {
        return api_luck;
    }

    /**
     * @param api_luck the api_luck to set
     */
    public void setApi_luck(List<Integer> api_luck) {
        this.api_luck = api_luck;
    }

    /**
     * @return the api_soku
     */
    public int getApi_soku() {
        return api_soku;
    }

    /**
     * @param api_soku the api_soku to set
     */
    public void setApi_soku(int api_soku) {
        this.api_soku = api_soku;
    }

    /**
     * @return the api_leng
     */
    public int getApi_leng() {
        return api_leng;
    }

    /**
     * @param api_leng the api_leng to set
     */
    public void setApi_leng(int api_leng) {
        this.api_leng = api_leng;
    }

    /**
     * @return the api_slot_num
     */
    public int getApi_slot_num() {
        return api_slot_num;
    }

    /**
     * @param api_slot_num the api_slot_num to set
     */
    public void setApi_slot_num(int api_slot_num) {
        this.api_slot_num = api_slot_num;
    }

    /**
     * @return the api_maxeq
     */
    public List<Integer> getApi_maxeq() {
        return api_maxeq;
    }

    /**
     * @param api_maxeq the api_maxeq to set
     */
    public void setApi_maxeq(List<Integer> api_maxeq) {
        this.api_maxeq = api_maxeq;
    }

    /**
     * @return the api_buildtime
     */
    public int getApi_buildtime() {
        return api_buildtime;
    }

    /**
     * @param api_buildtime the api_buildtime to set
     */
    public void setApi_buildtime(int api_buildtime) {
        this.api_buildtime = api_buildtime;
    }

    /**
     * @return the api_broken
     */
    public List<Integer> getApi_broken() {
        return api_broken;
    }

    /**
     * @param api_broken the api_broken to set
     */
    public void setApi_broken(List<Integer> api_broken) {
        this.api_broken = api_broken;
    }

    /**
     * @return the api_powup
     */
    public List<Integer> getApi_powup() {
        return api_powup;
    }

    /**
     * @param api_powup the api_powup to set
     */
    public void setApi_powup(List<Integer> api_powup) {
        this.api_powup = api_powup;
    }

    /**
     * @return the api_backs
     */
    public int getApi_backs() {
        return api_backs;
    }

    /**
     * @param api_backs the api_backs to set
     */
    public void setApi_backs(int api_backs) {
        this.api_backs = api_backs;
    }

    /**
     * @return the api_getmes
     */
    public String getApi_getmes() {
        return api_getmes;
    }

    /**
     * @param api_getmes the api_getmes to set
     */
    public void setApi_getmes(String api_getmes) {
        this.api_getmes = api_getmes;
    }

    /**
     * @return the api_afterfuel
     */
    public int getApi_afterfuel() {
        return api_afterfuel;
    }

    /**
     * @param api_afterfuel the api_afterfuel to set
     */
    public void setApi_afterfuel(int api_afterfuel) {
        this.api_afterfuel = api_afterfuel;
    }

    /**
     * @return the api_afterbull
     */
    public int getApi_afterbull() {
        return api_afterbull;
    }

    /**
     * @param api_afterbull the api_afterbull to set
     */
    public void setApi_afterbull(int api_afterbull) {
        this.api_afterbull = api_afterbull;
    }

    /**
     * @return the api_fuel_max
     */
    public int getApi_fuel_max() {
        return api_fuel_max;
    }

    /**
     * @param api_fuel_max the api_fuel_max to set
     */
    public void setApi_fuel_max(int api_fuel_max) {
        this.api_fuel_max = api_fuel_max;
    }

    /**
     * @return the api_bull_max
     */
    public int getApi_bull_max() {
        return api_bull_max;
    }

    /**
     * @param api_bull_max the api_bull_max to set
     */
    public void setApi_bull_max(int api_bull_max) {
        this.api_bull_max = api_bull_max;
    }

    /**
     * @return the api_voicef
     */
    public int getApi_voicef() {
        return api_voicef;
    }

    /**
     * @param api_voicef the api_voicef to set
     */
    public void setApi_voicef(int api_voicef) {
        this.api_voicef = api_voicef;
    }

}