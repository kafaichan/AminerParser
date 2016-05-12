package com.kafaichan.model;

import java.util.ArrayList;

/**
 * Created by kafaichan on 2016/5/12.
 */
public class Paper{
    private static String[] csv_header = {
            "paper_id","title","normalize_title","authors","affiliations","year","venue","normalize_venue","venue_id","abstract"
    };

    private Integer id;
    private String year;
    private String title, normalize_title;
    private String venue, normalize_venue;
    private String authors, affiliations;
    private Integer venue_id;
    private String  paper_abstract;
    private ArrayList<Integer> refs;

    public Paper(String id, String year, String title, String venue, String normalize_venue, 
      String authors, String affiliations, Integer venue_id,String paper_abstract, ArrayList<Integer> refs)
    {
        this.id = Integer.parseInt(id);
        this.year = year; 
	this.title = title;
        if(title != null)this.normalize_title = title.toLowerCase();
        else this.normalize_title = null;
 
        this.venue = venue;
       	this.normalize_venue = normalize_venue;

	this.authors = authors;
	this.affiliations = affiliations;
	this.venue_id = venue_id;
	this.paper_abstract = paper_abstract;
	this.refs = refs;
    }

    public static String[] getCsv_header() {
        return csv_header;
    }

    public static void setCsv_header(String[] csv_header) {
        Paper.csv_header = csv_header;
    }

    public Integer getId() {
        return id;
    }

    public String getYear() {
        return year;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getNormalizeTitle(){
        return normalize_title;
    }

    public String getVenue() {
        return venue;
    }

    public String getNormalizeVenue(){
        return normalize_venue;    
    }

    public String getAuthors() {
        return authors;
    }

    public String getAffiliations(){
	return affiliations;    
    }

    public Integer getVenueId(){
        return venue_id;
    }

    public String getPaper_abstract() {
        return paper_abstract;
    }

    public ArrayList<Integer> getRefs() {
        return refs;
    }

    @Override
    public String toString(){
        return "id: " + id + ", " +
                "year: " + year + ", "+
                "title: " + title + ", "+
                "venue: " + venue + ", " +
                "paper_abstract: " + paper_abstract + ", " +
                "authors: " + authors + ", " +
                "refs: " + refs.toString();
    }
}
