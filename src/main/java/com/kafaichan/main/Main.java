package com.kafaichan.main;

import com.kafaichan.util.ReadFileTask;
import com.kafaichan.util.WriteFileTask;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        WriteFileTask.tasks[0].start();

        ReadFileTask readFileTask = new ReadFileTask("AMiner-Paper.txt");
        readFileTask.startParse();

	FileOutputStream outputStream = null;
	OutputStreamWriter streamWriter = null;
	BufferedWriter writer = null;
	
        try {
            WriteFileTask.tasks[0].join();
	    HashMap<String,Integer> confMap = readFileTask.getConfMap();
 	    
	    File outMapWrite = new File("/home/jiahuichen/AminerData/After/confMap.csv");
	    outputStream = new FileOutputStream(outMapWrite,true);
	    streamWriter = new OutputStreamWriter(outputStream,"UTF-8");
	    writer = new BufferedWriter(streamWriter);
		
	    if(outMapWrite == null || !outMapWrite.exists()){
	    	try{
		   outMapWrite.createNewFile();	
		}catch(IOException e){
		   e.printStackTrace();
		}
   	    }	   
	    Set<Map.Entry<String,Integer>> entry = confMap.entrySet();
	   
	    for(Map.Entry<String,Integer> x:entry){
	    	writer.write("\"" + x.getKey() + "\"" + "," + x.getValue());
		writer.newLine();
		writer.flush();
	    }
	    System.out.println("Finish confMap"); 
	    File yearFile = new File("/home/jiahuichen/AminerData/After/year.csv");
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
	    
	    Set<String> years = readFileTask.getYearSet();
	    for(String year:years){
	    	writer.write(year);
		writer.newLine();
		writer.flush();
	    }
	    System.out.println("Finish yearSet");
        } catch (InterruptedException e) {
            e.printStackTrace();
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
