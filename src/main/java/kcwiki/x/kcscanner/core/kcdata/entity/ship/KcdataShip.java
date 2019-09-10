/**
  * Copyright 2018 bejson.com 
  */
package kcwiki.x.kcscanner.core.kcdata.entity.ship;
import java.util.List;

/**
 * Auto-generated: 2018-11-04 20:44:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class KcdataShip {

    private int id;
    private int sort_no;
    private String name;
    private String yomi;
    private int stype;
    private int ctype;
    private int cnum;
    private int backs;
    private int after_lv;
    private int after_ship_id;
    private String get_mes;
    private int voice_f;
    private String filename;
    private List<String> file_version;
    private List<Integer> book_table_id;
    private String book_sinfo;
    private Stats stats;
    private Graph graph;
    private String wiki_id;
    private String chinese_name;
    private boolean can_drop;
    private boolean can_construct;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setSort_no(int sort_no) {
         this.sort_no = sort_no;
     }
     public int getSort_no() {
         return sort_no;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setYomi(String yomi) {
         this.yomi = yomi;
     }
     public String getYomi() {
         return yomi;
     }

    public void setStype(int stype) {
         this.stype = stype;
     }
     public int getStype() {
         return stype;
     }

    public void setCtype(int ctype) {
         this.ctype = ctype;
     }
     public int getCtype() {
         return ctype;
     }

    public void setCnum(int cnum) {
         this.cnum = cnum;
     }
     public int getCnum() {
         return cnum;
     }

    public void setBacks(int backs) {
         this.backs = backs;
     }
     public int getBacks() {
         return backs;
     }

    public void setAfter_lv(int after_lv) {
         this.after_lv = after_lv;
     }
     public int getAfter_lv() {
         return after_lv;
     }

    public void setAfter_ship_id(int after_ship_id) {
         this.after_ship_id = after_ship_id;
     }
     public int getAfter_ship_id() {
         return after_ship_id;
     }

    public void setGet_mes(String get_mes) {
         this.get_mes = get_mes;
     }
     public String getGet_mes() {
         return get_mes;
     }

    public void setVoice_f(int voice_f) {
         this.voice_f = voice_f;
     }
     public int getVoice_f() {
         return voice_f;
     }

    public void setFilename(String filename) {
         this.filename = filename;
     }
     public String getFilename() {
         return filename;
     }

    public void setFile_version(List<String> file_version) {
         this.file_version = file_version;
     }
     public List<String> getFile_version() {
         return file_version;
     }

    public void setBook_table_id(List<Integer> book_table_id) {
         this.book_table_id = book_table_id;
     }
     public List<Integer> getBook_table_id() {
         return book_table_id;
     }

    public void setBook_sinfo(String book_sinfo) {
         this.book_sinfo = book_sinfo;
     }
     public String getBook_sinfo() {
         return book_sinfo;
     }

    public void setStats(Stats stats) {
         this.stats = stats;
     }
     public Stats getStats() {
         return stats;
     }

    public void setGraph(Graph graph) {
         this.graph = graph;
     }
     public Graph getGraph() {
         return graph;
     }

    public void setWiki_id(String wiki_id) {
         this.wiki_id = wiki_id;
     }
     public String getWiki_id() {
         return wiki_id;
     }

    public void setChinese_name(String chinese_name) {
         this.chinese_name = chinese_name;
     }
     public String getChinese_name() {
         return chinese_name;
     }

    public void setCan_drop(boolean can_drop) {
         this.can_drop = can_drop;
     }
     public boolean getCan_drop() {
         return can_drop;
     }

    public void setCan_construct(boolean can_construct) {
         this.can_construct = can_construct;
     }
     public boolean getCan_construct() {
         return can_construct;
     }

}