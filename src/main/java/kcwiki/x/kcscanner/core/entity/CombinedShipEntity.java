/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kcwiki.x.kcscanner.core.start2.processor.DiffLogger;
import org.apache.commons.lang3.builder.EqualsExclude;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_shipupgrade;

/**
 *
 * @author iHaru
 * https://qiita.com/rch850/items/30825e6ff0811fdc011d
 * https://stackoverflow.com/questions/7421474/how-can-i-tell-jackson-to-ignore-a-property-for-which-i-dont-have-control-over
 * 
 */
public class CombinedShipEntity {
    @EqualsExclude
    private Integer api_id;
    @EqualsExclude
    private String wiki_id;
    
    private Integer api_sortno;

    private String api_name;

    private String api_yomi;

    private Integer api_stype;

    private Integer api_ctype;

    private Integer api_afterlv;

    private String api_aftershipid;

    private List<Integer> api_taik;

    private List<Integer> api_souk;

    private List<Integer> api_houg;

    private List<Integer> api_raig;

    private List<Integer> api_tyku;
    
    private List<Integer> api_tais;

    private List<Integer> api_luck;

    private Integer api_soku;

    private Integer api_leng;

    private Integer api_slot_num;

    private List<Integer> api_maxeq;

    private Integer api_buildtime;

    private List<Integer> api_broken;

    private List<Integer> api_powup;

    private Integer api_backs;

    private String api_getmes;

    private Integer api_afterfuel;

    private Integer api_afterbull;

    private Integer api_fuel_max;

    private Integer api_bull_max;

    private Integer api_voicef;

    private String api_filename;

    private List<String> api_version;

    private List<Integer> api_boko_n;

    private List<Integer> api_boko_d;

    private List<Integer> api_kaisyu_n;

    private List<Integer> api_kaisyu_d;

    private List<Integer> api_kaizo_n;

    private List<Integer> api_kaizo_d;

    private List<Integer> api_map_n;

    private List<Integer> api_map_d;

    private List<Integer> api_ensyuf_n;

    private List<Integer> api_ensyuf_d;

    private List<Integer> api_ensyue_n;

    private List<Integer> api_battle_n;

    private List<Integer> api_battle_d;

    private List<Integer> api_weda;

    private List<Integer> api_wedb;
    
    private List<Api_mst_shipupgrade> api_mst_shipupgrade = new ArrayList<>();
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof CombinedShipEntity)){
            return false;
        }
        CombinedShipEntity that = (CombinedShipEntity) obj;
        removeVersion(this);
        removeVersion(that);
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode target = convertToNode(this, mapper);
        JsonNode source = convertToNode(that, mapper);
        JsonNode patch = JsonDiff.asJson(source, target);
        //api_version		：ファイルのバージョン 文字列 [グラフィック, ボイス, 母港ボイス]    文件版本 array[graphic,voice,main_voice]
//        if(!this.getApi_version().get(0).equals(that.getApi_version().get(0))) 
//            return false;
        if(patch.size() > 0) {
            DiffLogger.addSlotitemDiff(this.getApi_name(), patch);
            return false;
        }
        return true;
    }
    
    private void removeVersion(CombinedShipEntity combinedShipEntity){
        if(combinedShipEntity.getApi_version().size() == 3){
            combinedShipEntity.getApi_version().remove(1);
            combinedShipEntity.getApi_version().remove(1);
        } else {
            System.err.print("WTF?!");
        }
    }
    
    private JsonNode convertToNode(Object obj, ObjectMapper mapper){
        Map<String, Object> map = mapper.convertValue(obj, Map.class);
        map.remove("api_id");
        map.remove("wiki_id");
//        map.remove("api_version");
        ((List<Map<String, Object>> ) map.get("api_mst_shipupgrade"))
                .forEach(o -> {
                    o.remove("api_id");
                });
        JsonNode node = mapper.valueToTree(map);
        return node;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.getApi_name());
        hash = 79 * hash + Objects.hashCode(this.getApi_yomi());
        hash = 79 * hash + Objects.hashCode(this.getApi_stype());
        hash = 79 * hash + Objects.hashCode(this.getApi_ctype());
        hash = 79 * hash + Objects.hashCode(this.getApi_afterlv());
        hash = 79 * hash + Objects.hashCode(this.getApi_aftershipid());
        hash = 79 * hash + Objects.hashCode(this.getApi_taik());
        hash = 79 * hash + Objects.hashCode(this.getApi_souk());
        hash = 79 * hash + Objects.hashCode(this.getApi_houg());
        hash = 79 * hash + Objects.hashCode(this.getApi_raig());
        hash = 79 * hash + Objects.hashCode(this.getApi_tyku());
        hash = 79 * hash + Objects.hashCode(this.getApi_tais());
        hash = 79 * hash + Objects.hashCode(this.getApi_luck());
        hash = 79 * hash + Objects.hashCode(this.getApi_soku());
        hash = 79 * hash + Objects.hashCode(this.getApi_leng());
        hash = 79 * hash + Objects.hashCode(this.getApi_slot_num());
        hash = 79 * hash + Objects.hashCode(this.getApi_maxeq());
        hash = 79 * hash + Objects.hashCode(this.getApi_buildtime());
        hash = 79 * hash + Objects.hashCode(this.getApi_broken());
        hash = 79 * hash + Objects.hashCode(this.getApi_powup());
        hash = 79 * hash + Objects.hashCode(this.getApi_backs());
        hash = 79 * hash + Objects.hashCode(this.getApi_getmes());
        hash = 79 * hash + Objects.hashCode(this.getApi_afterfuel());
        hash = 79 * hash + Objects.hashCode(this.getApi_afterbull());
        hash = 79 * hash + Objects.hashCode(this.getApi_fuel_max());
        hash = 79 * hash + Objects.hashCode(this.getApi_bull_max());
        hash = 79 * hash + Objects.hashCode(this.getApi_voicef());
        hash = 79 * hash + Objects.hashCode(this.getApi_filename());
        hash = 79 * hash + Objects.hashCode(this.getApi_version());
        hash = 79 * hash + Objects.hashCode(this.getApi_boko_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_boko_d());
        hash = 79 * hash + Objects.hashCode(this.getApi_kaisyu_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_kaisyu_d());
        hash = 79 * hash + Objects.hashCode(this.getApi_kaizo_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_kaizo_d());
        hash = 79 * hash + Objects.hashCode(this.getApi_map_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_map_d());
        hash = 79 * hash + Objects.hashCode(this.getApi_ensyuf_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_ensyuf_d());
        hash = 79 * hash + Objects.hashCode(this.getApi_ensyue_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_battle_n());
        hash = 79 * hash + Objects.hashCode(this.getApi_battle_d());
        hash = 79 * hash + Objects.hashCode(this.getApi_weda());
        hash = 79 * hash + Objects.hashCode(this.getApi_wedb());
        hash = 79 * hash + Objects.hashCode(this.getApi_mst_shipupgrade());
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
     * @return the api_sortno
     */
    public Integer getApi_sortno() {
        return api_sortno;
    }

    /**
     * @param api_sortno the api_sortno to set
     */
    public void setApi_sortno(Integer api_sortno) {
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
    public Integer getApi_stype() {
        return api_stype;
    }

    /**
     * @param api_stype the api_stype to set
     */
    public void setApi_stype(Integer api_stype) {
        this.api_stype = api_stype;
    }

    /**
     * @return the api_ctype
     */
    public Integer getApi_ctype() {
        return api_ctype;
    }

    /**
     * @param api_ctype the api_ctype to set
     */
    public void setApi_ctype(Integer api_ctype) {
        this.api_ctype = api_ctype;
    }

    /**
     * @return the api_afterlv
     */
    public Integer getApi_afterlv() {
        return api_afterlv;
    }

    /**
     * @param api_afterlv the api_afterlv to set
     */
    public void setApi_afterlv(Integer api_afterlv) {
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
     * @return the api_tais
     */
    public List<Integer> getApi_tais() {
        return api_tais;
    }

    /**
     * @param api_tais the api_tais to set
     */
    public void setApi_tais(List<Integer> api_tais) {
        this.api_tais = api_tais;
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
    public Integer getApi_soku() {
        return api_soku;
    }

    /**
     * @param api_soku the api_soku to set
     */
    public void setApi_soku(Integer api_soku) {
        this.api_soku = api_soku;
    }

    /**
     * @return the api_leng
     */
    public Integer getApi_leng() {
        return api_leng;
    }

    /**
     * @param api_leng the api_leng to set
     */
    public void setApi_leng(Integer api_leng) {
        this.api_leng = api_leng;
    }

    /**
     * @return the api_slot_num
     */
    public Integer getApi_slot_num() {
        return api_slot_num;
    }

    /**
     * @param api_slot_num the api_slot_num to set
     */
    public void setApi_slot_num(Integer api_slot_num) {
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
    public Integer getApi_buildtime() {
        return api_buildtime;
    }

    /**
     * @param api_buildtime the api_buildtime to set
     */
    public void setApi_buildtime(Integer api_buildtime) {
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
    public Integer getApi_backs() {
        return api_backs;
    }

    /**
     * @param api_backs the api_backs to set
     */
    public void setApi_backs(Integer api_backs) {
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
    public Integer getApi_afterfuel() {
        return api_afterfuel;
    }

    /**
     * @param api_afterfuel the api_afterfuel to set
     */
    public void setApi_afterfuel(Integer api_afterfuel) {
        this.api_afterfuel = api_afterfuel;
    }

    /**
     * @return the api_afterbull
     */
    public Integer getApi_afterbull() {
        return api_afterbull;
    }

    /**
     * @param api_afterbull the api_afterbull to set
     */
    public void setApi_afterbull(Integer api_afterbull) {
        this.api_afterbull = api_afterbull;
    }

    /**
     * @return the api_fuel_max
     */
    public Integer getApi_fuel_max() {
        return api_fuel_max;
    }

    /**
     * @param api_fuel_max the api_fuel_max to set
     */
    public void setApi_fuel_max(Integer api_fuel_max) {
        this.api_fuel_max = api_fuel_max;
    }

    /**
     * @return the api_bull_max
     */
    public Integer getApi_bull_max() {
        return api_bull_max;
    }

    /**
     * @param api_bull_max the api_bull_max to set
     */
    public void setApi_bull_max(Integer api_bull_max) {
        this.api_bull_max = api_bull_max;
    }

    /**
     * @return the api_voicef
     */
    public Integer getApi_voicef() {
        return api_voicef;
    }

    /**
     * @param api_voicef the api_voicef to set
     */
    public void setApi_voicef(Integer api_voicef) {
        this.api_voicef = api_voicef;
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
    public List<String> getApi_version() {
        return api_version;
    }

    /**
     * @param api_version the api_version to set
     */
    public void setApi_version(List<String> api_version) {
        this.api_version = api_version;
    }

    /**
     * @return the api_boko_n
     */
    public List<Integer> getApi_boko_n() {
        return api_boko_n;
    }

    /**
     * @param api_boko_n the api_boko_n to set
     */
    public void setApi_boko_n(List<Integer> api_boko_n) {
        this.api_boko_n = api_boko_n;
    }

    /**
     * @return the api_boko_d
     */
    public List<Integer> getApi_boko_d() {
        return api_boko_d;
    }

    /**
     * @param api_boko_d the api_boko_d to set
     */
    public void setApi_boko_d(List<Integer> api_boko_d) {
        this.api_boko_d = api_boko_d;
    }

    /**
     * @return the api_kaisyu_n
     */
    public List<Integer> getApi_kaisyu_n() {
        return api_kaisyu_n;
    }

    /**
     * @param api_kaisyu_n the api_kaisyu_n to set
     */
    public void setApi_kaisyu_n(List<Integer> api_kaisyu_n) {
        this.api_kaisyu_n = api_kaisyu_n;
    }

    /**
     * @return the api_kaisyu_d
     */
    public List<Integer> getApi_kaisyu_d() {
        return api_kaisyu_d;
    }

    /**
     * @param api_kaisyu_d the api_kaisyu_d to set
     */
    public void setApi_kaisyu_d(List<Integer> api_kaisyu_d) {
        this.api_kaisyu_d = api_kaisyu_d;
    }

    /**
     * @return the api_kaizo_n
     */
    public List<Integer> getApi_kaizo_n() {
        return api_kaizo_n;
    }

    /**
     * @param api_kaizo_n the api_kaizo_n to set
     */
    public void setApi_kaizo_n(List<Integer> api_kaizo_n) {
        this.api_kaizo_n = api_kaizo_n;
    }

    /**
     * @return the api_kaizo_d
     */
    public List<Integer> getApi_kaizo_d() {
        return api_kaizo_d;
    }

    /**
     * @param api_kaizo_d the api_kaizo_d to set
     */
    public void setApi_kaizo_d(List<Integer> api_kaizo_d) {
        this.api_kaizo_d = api_kaizo_d;
    }

    /**
     * @return the api_map_n
     */
    public List<Integer> getApi_map_n() {
        return api_map_n;
    }

    /**
     * @param api_map_n the api_map_n to set
     */
    public void setApi_map_n(List<Integer> api_map_n) {
        this.api_map_n = api_map_n;
    }

    /**
     * @return the api_map_d
     */
    public List<Integer> getApi_map_d() {
        return api_map_d;
    }

    /**
     * @param api_map_d the api_map_d to set
     */
    public void setApi_map_d(List<Integer> api_map_d) {
        this.api_map_d = api_map_d;
    }

    /**
     * @return the api_ensyuf_n
     */
    public List<Integer> getApi_ensyuf_n() {
        return api_ensyuf_n;
    }

    /**
     * @param api_ensyuf_n the api_ensyuf_n to set
     */
    public void setApi_ensyuf_n(List<Integer> api_ensyuf_n) {
        this.api_ensyuf_n = api_ensyuf_n;
    }

    /**
     * @return the api_ensyuf_d
     */
    public List<Integer> getApi_ensyuf_d() {
        return api_ensyuf_d;
    }

    /**
     * @param api_ensyuf_d the api_ensyuf_d to set
     */
    public void setApi_ensyuf_d(List<Integer> api_ensyuf_d) {
        this.api_ensyuf_d = api_ensyuf_d;
    }

    /**
     * @return the api_ensyue_n
     */
    public List<Integer> getApi_ensyue_n() {
        return api_ensyue_n;
    }

    /**
     * @param api_ensyue_n the api_ensyue_n to set
     */
    public void setApi_ensyue_n(List<Integer> api_ensyue_n) {
        this.api_ensyue_n = api_ensyue_n;
    }

    /**
     * @return the api_battle_n
     */
    public List<Integer> getApi_battle_n() {
        return api_battle_n;
    }

    /**
     * @param api_battle_n the api_battle_n to set
     */
    public void setApi_battle_n(List<Integer> api_battle_n) {
        this.api_battle_n = api_battle_n;
    }

    /**
     * @return the api_battle_d
     */
    public List<Integer> getApi_battle_d() {
        return api_battle_d;
    }

    /**
     * @param api_battle_d the api_battle_d to set
     */
    public void setApi_battle_d(List<Integer> api_battle_d) {
        this.api_battle_d = api_battle_d;
    }

    /**
     * @return the api_weda
     */
    public List<Integer> getApi_weda() {
        return api_weda;
    }

    /**
     * @param api_weda the api_weda to set
     */
    public void setApi_weda(List<Integer> api_weda) {
        this.api_weda = api_weda;
    }

    /**
     * @return the api_wedb
     */
    public List<Integer> getApi_wedb() {
        return api_wedb;
    }

    /**
     * @param api_wedb the api_wedb to set
     */
    public void setApi_wedb(List<Integer> api_wedb) {
        this.api_wedb = api_wedb;
    }

    /**
     * @return the api_mst_shipupgrade
     */
    public List<Api_mst_shipupgrade> getApi_mst_shipupgrade() {
        return api_mst_shipupgrade;
    }

    /**
     * @param api_mst_shipupgrade the api_mst_shipupgrade to set
     */
    public void setApi_mst_shipupgrade(List<Api_mst_shipupgrade> api_mst_shipupgrade) {
        this.api_mst_shipupgrade = api_mst_shipupgrade;
    }

    /**
     * @return the wiki_id
     */
    public String getWiki_id() {
        return wiki_id;
    }

    /**
     * @param wiki_id the wiki_id to set
     */
    public void setWiki_id(String wiki_id) {
        this.wiki_id = wiki_id;
    }

}
