package com.kafaichan.model;

/**
 * Created by kafaichan on 2016/5/13.
 */
public class Person {
    private Integer id; 
    private String name;
    private String normalize_name;
    private String affiliation;
    private String normalize_affiliation; 

    private Integer published_cnt; //the count of published papers of this author
    private Integer total_cn; // total number of citaition of this author;
    private Integer hindex; 
    private Double pindex; 
    private Double upindex; 
    private String keyterms; //seperated by semi-colon;

    public Integer getId(){ return id; }
    public String getName(){ return name; }
    public String getNormalizeName(){ return normalize_name; }
    public String getNormalizeAffiliation(){ return normalize_affiliation; }
    public String getAffiliation(){ return affiliation; }
    public Integer getPublishedCnt(){ return published_cnt; }
    public Integer getTotalCn(){ return total_cn; }
    public Integer getHindex(){ return hindex; }
    public Double getPindex(){ return pindex; }
    public Double getUPIndex(){ return upindex; }
    public String getKeyterms(){ return keyterms; }

    public Person(String id, String name, String affiliation, String published_cnt, 
      String total_cn, String hindex, String pindex, String upindex, String keyterms){
	this.id = Integer.parseInt(id);
	this.name = name;
	if(name != null)this.normalize_name = name.toLowerCase();
	else this.normalize_name = null;	

	this.affiliation = affiliation;
	if(affiliation != null)this.normalize_affiliation = affiliation.toLowerCase();
	else this.normalize_affiliation = null;
	
	
	this.published_cnt = published_cnt == null?0:Integer.parseInt(published_cnt);
	this.total_cn = total_cn == null?0:Integer.parseInt(total_cn);
	this.hindex = hindex == null?0:Integer.parseInt(hindex);
	this.pindex = pindex == null?0.0:Double.parseDouble(pindex);
	this.upindex = upindex == null?0.0:Double.parseDouble(upindex);	
	this.keyterms = keyterms;
    }
}
