package com.kafaichan.main;

import com.kafaichan.util.ReadFileTask;
import com.kafaichan.util.WriteFileTask;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();

        ReadFileTask readFileTask = new ReadFileTask("AMiner-Paper.txt");
        readFileTask.startParse();

	FileOutputStream outputStream = null;
	OutputStreamWriter streamWriter = null;
	BufferedWriter writer = null;
	
        try {
	    HashMap<String,Integer> confMap = readFileTask.getConfMap();
 
	    File outMapWrite = new File("/home/jiahuichen/AminerData/AminerPaperAfter/confMap.csv");
	    if(outMapWrite == null || !outMapWrite.exists()){
	    	try{
		   outMapWrite.createNewFile();	
		}catch(IOException e){
		   e.printStackTrace();
		}
   	    }	 
	    outputStream = new FileOutputStream(outMapWrite,true);
	    streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
	    writer = new BufferedWriter(streamWriter);
	    Set<Map.Entry<String,Integer>> entry = confMap.entrySet();
	    for(Map.Entry<String,Integer> x:entry){
	    	writer.write("\"" + x.getKey() + "\"" + "," + x.getValue());
		writer.newLine();
		writer.flush();
	    }
	    System.out.println("Finish confMap");
 
   	    Set<String> yearSet = readFileTask.getYearSet();
	    File yearFile = new File("/home/jiahuichen/AminerData/AminerPaperAfter/year.csv");
            if(yearFile == null || !yearFile.exists()){
	    	try{
		   yearFile.createNewFile();	
		}catch(IOException e){
		   e.printStackTrace();
		}
   	    }
	    outputStream = new FileOutputStream(yearFile,true);
	    streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
	    writer = new BufferedWriter(streamWriter);
	     
	    Iterator<String> iterator = yearSet.iterator(); 
	    while(iterator.hasNext()){
	    	writer.write("\"" + iterator.next() + "\"");
		writer.newLine();
		writer.flush();
	    }
	    System.out.println("Finish yearSet");
        }catch(FileNotFoundException e){
	    e.printStackTrace();
	}catch(UnsupportedEncodingException e){
	    e.printStackTrace();
	}catch(IOException e){
	    e.printStackTrace();
	}finally{
		try{
			if(writer != null)writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

        System.out.println(System.currentTimeMillis()-start);
        System.out.println("Finish");

    }
}
