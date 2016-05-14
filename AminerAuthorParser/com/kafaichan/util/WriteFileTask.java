package com.kafaichan.util;

import com.kafaichan.model.Person;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by kafaichan on 2016/5/12.
 */
public class WriteFileTask{

    private static final String basedestDir = "/home/jiahuichen/AminerData/AminerAuthorAfter/";
    private File authorOutFile;

    public static WriteFileTask[] tasks = {
        new WriteFileTask()
    };

    public Write2SQLTask sqltask;

    public WriteFileTask(){
        authorOutFile = new File(basedestDir + "authors.csv");
	sqltask = new Write2SQLTask("","");
    	
	if(authorOutFile == null || !authorOutFile.exists()){
            try {
                authorOutFile.createNewFile();
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
    public void writeToDisk(Person p){
        BufferedWriter writer;
        writer = null;
        try {
            writer = generateWriter(authorOutFile);
	    Integer id = p.getId();
	    String name = p.getName();
	    String normalize_name = p.getNormalizeName();
 	    String affiliation = p.getAffiliation();
	    String normalize_affiliation = p.getNormalizeAffiliation();
            Integer pc = p.getPublishedCnt();
	    Integer tc = p.getTotalCn();
            Integer hi = p.getHindex();
            Double pi = p.getPindex();
	    Double upi = p.getUPIndex();
            String keyterms = p.getKeyterms();

	    String line = String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",%d,%d,%d,%.4f,%.4f,\"%s\"",id, name==null?"":name,
		normalize_name==null?"":normalize_name,
		affiliation==null?"":affiliation,
		normalize_affiliation==null?"":normalize_affiliation,
		pc, tc, hi,
	 	pi, upi,	
		keyterms==null?"":keyterms
            );
	    writer.write(line);
	    writer.newLine();
            writer.flush(); 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(writer != null)writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }    
}
