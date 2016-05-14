package com.kafaichan.util;

import com.kafaichan.model.Author2Paper;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by kafaichan on 2016/5/12.
 */
public class WriteFileTask extends Thread{

    private static final String basedestDir = "/home/jiahuichen/AminerData/AminerAuthor2PaperAfter/";
    private File a2pOutFile;

    public static WriteFileTask[] tasks = {
        new WriteFileTask()
    };


    public WriteFileTask(){
        a2pOutFile = new File(basedestDir + "author2p.csv");
    	
	if(a2pOutFile == null || !a2pOutFile.exists()){
            try {
                a2pOutFile.createNewFile();
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
    public void writeToDisk(Author2Paper p){
        BufferedWriter writer;
        writer = null;
        try {
            writer = generateWriter(a2pOutFile);
            Integer author_id = p.getAuthorId();
	    Integer paper_id = p.getPaperId();

	    String line = String.format("%d,%d",author_id,paper_id);
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
