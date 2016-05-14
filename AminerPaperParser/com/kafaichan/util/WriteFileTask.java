package com.kafaichan.util;

import com.kafaichan.model.Paper;
import java.util.ArrayList;
import java.io.*;
import java.util.LinkedList;
import com.kafaichan.util.Write2SQLTask; 

/**
 * Created by kafaichan on 2016/5/12.
 */
public class WriteFileTask{

    private static final String basedestDir = "/home/jiahuichen/AminerData/AminerPaperAfter/";
    private File paperOutFile;
    private File refOutFile; 

    public  Write2SQLTask sqltask; 

    public static WriteFileTask[] tasks = {
        new WriteFileTask()
    };

    
    public WriteFileTask(){
        paperOutFile = new File(basedestDir + "papers.csv");
	
	sqltask = new Write2SQLTask("root","icst");
    	
	if(paperOutFile == null || !paperOutFile.exists()){
            try {
                paperOutFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 	refOutFile = new File(basedestDir + "refs.csv");
	if(refOutFile == null || !refOutFile.exists()){
            try {
                refOutFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
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
    public void writeToDisk(Paper p){
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
 
	    String line = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",id, title,
		normalize_title,
		authors,
		affiliations,
		year==null?"":year,
		venue,
		normalize_venue,
		venue_id==null?"":venue_id,
		paper_abstract
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
