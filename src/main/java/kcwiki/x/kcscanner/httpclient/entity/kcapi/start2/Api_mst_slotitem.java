/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

/**
 *
 * @author iHaru
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kcwiki.x.kcscanner.core.start2.processor.DiffLogger;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
public class Api_mst_slotitem
{
    @EqualsExclude
    private int api_id;

    private int api_sortno;

    private String api_name;

    private List<Integer> api_type ;

    private int api_taik;

    private int api_souk;

    private int api_houg;

    private int api_raig;

    private int api_soku;

    private int api_baku;

    private int api_tyku;

    private int api_tais;

    private int api_atap;

    private int api_houm;

    private int api_raim;

    private int api_houk;

    private int api_raik;

    private int api_bakk;

    private int api_saku;

    private int api_sakb;

    private int api_luck;

    private int api_leng;

    private int api_rare;
    
    private int api_cost;
    
    private int api_distance;

    private List<Integer> api_broken ;

    private String api_info;

    private String api_usebull;

    private int api_version;
    
//    @Override
//    public boolean equals(Object obj) {
//        if(obj == null || !(obj instanceof Api_mst_slotitem)){
//            return false;
//        }
//        return EqualsBuilder.reflectionEquals(this, obj);
//    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_slotitem)){
            return false;
        }
        Api_mst_slotitem that = (Api_mst_slotitem) obj;
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode target = convertToNode(this, mapper);
        JsonNode source = convertToNode(that, mapper);
        JsonNode patch = JsonDiff.asJson(source, target);
        if(patch.size() > 0) {
            DiffLogger.addSlotitemDiff(this.getApi_name(), patch);
            return false;
        }
        return true;
    }
    
    private JsonNode convertToNode(Object obj, ObjectMapper mapper){
        Map<String, Object> map = mapper.convertValue(obj, Map.class);
        map.remove("api_id");
        JsonNode node = mapper.valueToTree(map);
        return node;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getApi_name());
        hash = 79 * hash + Objects.hashCode(this.getApi_type());
        hash = 79 * hash + Objects.hashCode(this.getApi_taik());
        hash = 79 * hash + Objects.hashCode(this.getApi_souk());
        hash = 79 * hash + Objects.hashCode(this.getApi_houg());
        hash = 79 * hash + Objects.hashCode(this.getApi_raig());
        hash = 79 * hash + Objects.hashCode(this.getApi_soku());
        hash = 79 * hash + Objects.hashCode(this.getApi_baku());
        hash = 79 * hash + Objects.hashCode(this.getApi_tyku());
        hash = 79 * hash + Objects.hashCode(this.getApi_tais());
        hash = 79 * hash + Objects.hashCode(this.getApi_atap());
        hash = 79 * hash + Objects.hashCode(this.getApi_houm());
        hash = 79 * hash + Objects.hashCode(this.getApi_raim());
        hash = 79 * hash + Objects.hashCode(this.getApi_houk());
        hash = 79 * hash + Objects.hashCode(this.getApi_raik());
        hash = 79 * hash + Objects.hashCode(this.getApi_bakk());
        hash = 79 * hash + Objects.hashCode(this.getApi_saku());
        hash = 79 * hash + Objects.hashCode(this.getApi_sakb());
        hash = 79 * hash + Objects.hashCode(this.getApi_luck());
        hash = 79 * hash + Objects.hashCode(this.getApi_leng());
        hash = 79 * hash + Objects.hashCode(this.getApi_cost());
        hash = 79 * hash + Objects.hashCode(this.getApi_distance());
        hash = 79 * hash + Objects.hashCode(this.getApi_rare());
        hash = 79 * hash + Objects.hashCode(this.getApi_broken());
        hash = 79 * hash + Objects.hashCode(this.getApi_info());
        hash = 79 * hash + Objects.hashCode(this.getApi_usebull());
        hash = 79 * hash + Objects.hashCode(this.getApi_version());
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
     * @return the api_type
     */
    public List<Integer> getApi_type() {
        return api_type;
    }

    /**
     * @param api_type the api_type to set
     */
    public void setApi_type(List<Integer> api_type) {
        this.api_type = api_type;
    }

    /**
     * @return the api_taik
     */
    public int getApi_taik() {
        return api_taik;
    }

    /**
     * @param api_taik the api_taik to set
     */
    public void setApi_taik(int api_taik) {
        this.api_taik = api_taik;
    }

    /**
     * @return the api_souk
     */
    public int getApi_souk() {
        return api_souk;
    }

    /**
     * @param api_souk the api_souk to set
     */
    public void setApi_souk(int api_souk) {
        this.api_souk = api_souk;
    }

    /**
     * @return the api_houg
     */
    public int getApi_houg() {
        return api_houg;
    }

    /**
     * @param api_houg the api_houg to set
     */
    public void setApi_houg(int api_houg) {
        this.api_houg = api_houg;
    }

    /**
     * @return the api_raig
     */
    public int getApi_raig() {
        return api_raig;
    }

    /**
     * @param api_raig the api_raig to set
     */
    public void setApi_raig(int api_raig) {
        this.api_raig = api_raig;
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
     * @return the api_baku
     */
    public int getApi_baku() {
        return api_baku;
    }

    /**
     * @param api_baku the api_baku to set
     */
    public void setApi_baku(int api_baku) {
        this.api_baku = api_baku;
    }

    /**
     * @return the api_tyku
     */
    public int getApi_tyku() {
        return api_tyku;
    }

    /**
     * @param api_tyku the api_tyku to set
     */
    public void setApi_tyku(int api_tyku) {
        this.api_tyku = api_tyku;
    }

    /**
     * @return the api_tais
     */
    public int getApi_tais() {
        return api_tais;
    }

    /**
     * @param api_tais the api_tais to set
     */
    public void setApi_tais(int api_tais) {
        this.api_tais = api_tais;
    }

    /**
     * @return the api_atap
     */
    public int getApi_atap() {
        return api_atap;
    }

    /**
     * @param api_atap the api_atap to set
     */
    public void setApi_atap(int api_atap) {
        this.api_atap = api_atap;
    }

    /**
     * @return the api_houm
     */
    public int getApi_houm() {
        return api_houm;
    }

    /**
     * @param api_houm the api_houm to set
     */
    public void setApi_houm(int api_houm) {
        this.api_houm = api_houm;
    }

    /**
     * @return the api_raim
     */
    public int getApi_raim() {
        return api_raim;
    }

    /**
     * @param api_raim the api_raim to set
     */
    public void setApi_raim(int api_raim) {
        this.api_raim = api_raim;
    }

    /**
     * @return the api_houk
     */
    public int getApi_houk() {
        return api_houk;
    }

    /**
     * @param api_houk the api_houk to set
     */
    public void setApi_houk(int api_houk) {
        this.api_houk = api_houk;
    }

    /**
     * @return the api_raik
     */
    public int getApi_raik() {
        return api_raik;
    }

    /**
     * @param api_raik the api_raik to set
     */
    public void setApi_raik(int api_raik) {
        this.api_raik = api_raik;
    }

    /**
     * @return the api_bakk
     */
    public int getApi_bakk() {
        return api_bakk;
    }

    /**
     * @param api_bakk the api_bakk to set
     */
    public void setApi_bakk(int api_bakk) {
        this.api_bakk = api_bakk;
    }

    /**
     * @return the api_saku
     */
    public int getApi_saku() {
        return api_saku;
    }

    /**
     * @param api_saku the api_saku to set
     */
    public void setApi_saku(int api_saku) {
        this.api_saku = api_saku;
    }

    /**
     * @return the api_sakb
     */
    public int getApi_sakb() {
        return api_sakb;
    }

    /**
     * @param api_sakb the api_sakb to set
     */
    public void setApi_sakb(int api_sakb) {
        this.api_sakb = api_sakb;
    }

    /**
     * @return the api_luck
     */
    public int getApi_luck() {
        return api_luck;
    }

    /**
     * @param api_luck the api_luck to set
     */
    public void setApi_luck(int api_luck) {
        this.api_luck = api_luck;
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
     * @return the api_rare
     */
    public int getApi_rare() {
        return api_rare;
    }

    /**
     * @param api_rare the api_rare to set
     */
    public void setApi_rare(int api_rare) {
        this.api_rare = api_rare;
    }

    /**
     * @return the api_cost
     */
    public int getApi_cost() {
        return api_cost;
    }

    /**
     * @param api_cost the api_cost to set
     */
    public void setApi_cost(int api_cost) {
        this.api_cost = api_cost;
    }

    /**
     * @return the api_distance
     */
    public int getApi_distance() {
        return api_distance;
    }

    /**
     * @param api_distance the api_distance to set
     */
    public void setApi_distance(int api_distance) {
        this.api_distance = api_distance;
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
     * @return the api_info
     */
    public String getApi_info() {
        return api_info;
    }

    /**
     * @param api_info the api_info to set
     */
    public void setApi_info(String api_info) {
        this.api_info = api_info;
    }

    /**
     * @return the api_usebull
     */
    public String getApi_usebull() {
        return api_usebull;
    }

    /**
     * @param api_usebull the api_usebull to set
     */
    public void setApi_usebull(String api_usebull) {
        this.api_usebull = api_usebull;
    }

    /**
     * @return the api_version
     */
    public int getApi_version() {
        return api_version;
    }

    /**
     * @param api_version the api_version to set
     */
    public void setApi_version(int api_version) {
        this.api_version = api_version;
    }

}
