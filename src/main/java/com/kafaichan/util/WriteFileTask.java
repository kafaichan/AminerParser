package com.kafaichan.util;

import com.kafaichan.model.Paper;
import java.util.ArrayList;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kafaichan on 2016/5/12.
 */
public class WriteFileTask extends Thread{

    private static final String basedestDir = "/home/jiahuichen/AminerData/After/";
    private ConcurrentLinkedQueue<Paper> linkedList;
    private File paperOutFile;
    private File refOutFile; 

    public static WriteFileTask[] tasks = {
        new WriteFileTask()
    };


    public WriteFileTask(){
        linkedList = new ConcurrentLinkedQueue<Paper>();
        paperOutFile = new File(basedestDir + "papers.csv");
	refOutFile = new File(basedestDir + "refs.csv");
    	
	if(paperOutFile == null || !paperOutFile.exists()){
            try {
                paperOutFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
  	
	if(refOutFile == null || !refOutFile.exists()){
            try {
                refOutFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Paper p){
        linkedList.offer(p);
    }


    public void run() {
        while(true){
            if(!linkedList.isEmpty()){
                Paper p = linkedList.poll();
                if(p == null)continue;
                if(p.getId() == 2092333){
                    writeToDisk(p);
                    break;
                }
                writeToDisk(p);
            }
        }
    }

    private BufferedWriter generateWriter(File file) throws UnsupportedEncodingException, FileNotFoundException {
        FileOutputStream outputStream = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter writer = null;

        outputStream = new FileOutputStream(file,true);
        streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
        writer = new BufferedWriter(streamWriter);

        return writer;
    }
    private void writeToDisk(Paper p){
        BufferedWriter writer, writer1;
        writer = writer1 = null;
        try {
            writer = generateWriter(paperOutFile);
	    Integer id = p.getId();
	    String title = p.getTitle();
	    String normalize_title = p.getNormalizeTitle();
	    String authors = p.getAuthors();
 	    String affiliations = p.getAffiliations();
            String year = p.getYear();
            String venue = p.getVenue(); 
	    String normalize_venue = p.getNormalizeVenue();
            Integer venue_id = p.getVenueId();
	    String paper_abstract = p.getPaper_abstract();
	    ArrayList<Integer> refs = p.getRefs();
 
	    String line = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\",%s,\"%s\"",id, title==null?"":title,
		normalize_title==null?"":normalize_title,
		authors==null?"":authors,
		affiliations==null?"":affiliations,
		year==null?"":year,
		venue==null?"":venue,
		normalize_venue==null?"":normalize_venue,
		venue_id==null?"":venue_id,
		paper_abstract==null?"":paper_abstract
            );
            writer.write(line);
            writer.newLine();
            writer.flush();

	    writer1 = generateWriter(refOutFile);
	    for(Integer ref:refs){
		String refline = String.format("%d,%d",id,ref);
		writer1.write(refline);
		writer1.newLine();
		writer1.flush();	    
	    }
	    System.out.println(id);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(writer != null)writer.close();
		if(writer1 != null)writer1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
