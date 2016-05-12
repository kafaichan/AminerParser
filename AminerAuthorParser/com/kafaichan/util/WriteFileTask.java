package com.kafaichan.util;

import com.kafaichan.model.Person;
import java.util.ArrayList;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kafaichan on 2016/5/12.
 */
public class WriteFileTask extends Thread{

    private static final String basedestDir = "/home/jiahuichen/AminerData/AuthorAfter/";
    private ConcurrentLinkedQueue<Person> linkedList;
    private File authorOutFile;

    public static WriteFileTask[] tasks = {
        new WriteFileTask()
    };


    public WriteFileTask(){
        linkedList = new ConcurrentLinkedQueue<Person>();
        authorOutFile = new File(basedestDir + "authors.csv");
    	
	if(authorOutFile == null || !authorOutFile.exists()){
            try {
                authorOutFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Person p){
        linkedList.offer(p);
    }


    public void run() {
        while(true){
            if(!linkedList.isEmpty()){
                Person p = linkedList.poll();
                if(p == null)continue;
                if(p.getId() == 1712433){
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
    private void writeToDisk(Person p){
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }    
}
