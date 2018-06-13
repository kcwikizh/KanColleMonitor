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
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
public class Api_mst_slotitem
{
    @EqualsExclude
    private Integer api_id;
    
    private Integer api_sortno;

    private String api_name;

    private List<Integer> api_type;

    private Integer api_taik;

    private Integer api_souk;

    private Integer api_houg;

    private Integer api_raig;

    private Integer api_soku;

    private Integer api_baku;

    private Integer api_tyku;

    private Integer api_tais;

    private Integer api_atap;

    private Integer api_houm;

    private Integer api_raim;

    private Integer api_houk;

    private Integer api_raik;

    private Integer api_bakk;

    private Integer api_saku;

    private Integer api_sakb;

    private Integer api_luck;

    private Integer api_leng;
    
    private Integer api_cost;
    
    private Integer api_distance;

    private Integer api_rare;

    private List<Integer> api_broken;

    private String api_info;

    private String api_usebull;
    
    private Integer api_version;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_slotitem)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.api_name);
        hash = 79 * hash + Objects.hashCode(this.api_type);
        hash = 79 * hash + Objects.hashCode(this.api_taik);
        hash = 79 * hash + Objects.hashCode(this.api_souk);
        hash = 79 * hash + Objects.hashCode(this.api_houg);
        hash = 79 * hash + Objects.hashCode(this.api_raig);
        hash = 79 * hash + Objects.hashCode(this.api_soku);
        hash = 79 * hash + Objects.hashCode(this.api_baku);
        hash = 79 * hash + Objects.hashCode(this.api_tyku);
        hash = 79 * hash + Objects.hashCode(this.api_tais);
        hash = 79 * hash + Objects.hashCode(this.api_atap);
        hash = 79 * hash + Objects.hashCode(this.api_houm);
        hash = 79 * hash + Objects.hashCode(this.api_raim);
        hash = 79 * hash + Objects.hashCode(this.api_houk);
        hash = 79 * hash + Objects.hashCode(this.api_raik);
        hash = 79 * hash + Objects.hashCode(this.api_bakk);
        hash = 79 * hash + Objects.hashCode(this.api_saku);
        hash = 79 * hash + Objects.hashCode(this.api_sakb);
        hash = 79 * hash + Objects.hashCode(this.api_luck);
        hash = 79 * hash + Objects.hashCode(this.api_leng);
        hash = 79 * hash + Objects.hashCode(this.api_cost);
        hash = 79 * hash + Objects.hashCode(this.api_distance);
        hash = 79 * hash + Objects.hashCode(this.api_rare);
        hash = 79 * hash + Objects.hashCode(this.api_broken);
        hash = 79 * hash + Objects.hashCode(this.api_info);
        hash = 79 * hash + Objects.hashCode(this.api_usebull);
        hash = 79 * hash + Objects.hashCode(this.api_version);
        return hash;
    }

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_sortno(Integer api_sortno){
        this.api_sortno = api_sortno;
    }
    public Integer getApi_sortno(){
        return this.api_sortno;
    }
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_type(List<Integer> api_type){
        this.api_type = api_type;
    }
    public List<Integer> getApi_type(){
        return this.api_type;
    }
    public void setApi_taik(Integer api_taik){
        this.api_taik = api_taik;
    }
    public Integer getApi_taik(){
        return this.api_taik;
    }
    public void setApi_souk(Integer api_souk){
        this.api_souk = api_souk;
    }
    public Integer getApi_souk(){
        return this.api_souk;
    }
    public void setApi_houg(Integer api_houg){
        this.api_houg = api_houg;
    }
    public Integer getApi_houg(){
        return this.api_houg;
    }
    public void setApi_raig(Integer api_raig){
        this.api_raig = api_raig;
    }
    public Integer getApi_raig(){
        return this.api_raig;
    }
    public void setApi_soku(Integer api_soku){
        this.api_soku = api_soku;
    }
    public Integer getApi_soku(){
        return this.api_soku;
    }
    public void setApi_baku(Integer api_baku){
        this.api_baku = api_baku;
    }
    public Integer getApi_baku(){
        return this.api_baku;
    }
    public void setApi_tyku(Integer api_tyku){
        this.api_tyku = api_tyku;
    }
    public Integer getApi_tyku(){
        return this.api_tyku;
    }
    public void setApi_tais(Integer api_tais){
        this.api_tais = api_tais;
    }
    public Integer getApi_tais(){
        return this.api_tais;
    }
    public void setApi_atap(Integer api_atap){
        this.api_atap = api_atap;
    }
    public Integer getApi_atap(){
        return this.api_atap;
    }
    public void setApi_houm(Integer api_houm){
        this.api_houm = api_houm;
    }
    public Integer getApi_houm(){
        return this.api_houm;
    }
    public void setApi_raim(Integer api_raim){
        this.api_raim = api_raim;
    }
    public Integer getApi_raim(){
        return this.api_raim;
    }
    public void setApi_houk(Integer api_houk){
        this.api_houk = api_houk;
    }
    public Integer getApi_houk(){
        return this.api_houk;
    }
    public void setApi_raik(Integer api_raik){
        this.api_raik = api_raik;
    }
    public Integer getApi_raik(){
        return this.api_raik;
    }
    public void setApi_bakk(Integer api_bakk){
        this.api_bakk = api_bakk;
    }
    public Integer getApi_bakk(){
        return this.api_bakk;
    }
    public void setApi_saku(Integer api_saku){
        this.api_saku = api_saku;
    }
    public Integer getApi_saku(){
        return this.api_saku;
    }
    public void setApi_sakb(Integer api_sakb){
        this.api_sakb = api_sakb;
    }
    public Integer getApi_sakb(){
        return this.api_sakb;
    }
    public void setApi_luck(Integer api_luck){
        this.api_luck = api_luck;
    }
    public Integer getApi_luck(){
        return this.api_luck;
    }
    public void setApi_leng(Integer api_leng){
        this.api_leng = api_leng;
    }
    public Integer getApi_leng(){
        return this.api_leng;
    }
    public void setApi_rare(Integer api_rare){
        this.api_rare = api_rare;
    }
    public Integer getApi_rare(){
        return this.api_rare;
    }
    public void setApi_broken(List<Integer> api_broken){
        this.api_broken = api_broken;
    }
    public List<Integer> getApi_broken(){
        return this.api_broken;
    }
    public void setApi_info(String api_info){
        this.api_info = api_info;
    }
    public String getApi_info(){
        return this.api_info;
    }
    public void setApi_usebull(String api_usebull){
        this.api_usebull = api_usebull;
    }
    public String getApi_usebull(){
        return this.api_usebull;
    }

    /**
     * @return the api_version
     */
    public Integer getApi_version() {
        return api_version;
    }

    /**
     * @param api_version the api_version to set
     */
    public void setApi_version(Integer api_version) {
        this.api_version = api_version;
    }

    /**
     * @return the api_cost
     */
    public Integer getApi_cost() {
        return api_cost;
    }

    /**
     * @param api_cost the api_cost to set
     */
    public void setApi_cost(Integer api_cost) {
        this.api_cost = api_cost;
    }

    /**
     * @return the api_distance
     */
    public Integer getApi_distance() {
        return api_distance;
    }

    /**
     * @param api_distance the api_distance to set
     */
    public void setApi_distance(Integer api_distance) {
        this.api_distance = api_distance;
    }
}
