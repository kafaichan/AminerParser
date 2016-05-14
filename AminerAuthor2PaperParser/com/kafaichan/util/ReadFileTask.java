package com.kafaichan.util;

import com.kafaichan.model.Author2Paper;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by kafaichan on 2016/5/12.
 */


public class ReadFileTask {
    private static String base_dir = "/home/jiahuichen/AminerData/";

    private static String filepath;

    private FileInputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;


    public ReadFileTask(String filename){
        this.filepath = base_dir + filename;

        try {
            inputStream = new FileInputStream(new File(filepath));
            inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void startParse(){
	System.out.println("startParse");
        String str;
        int cnt = 1;
        try {
            while((str=bufferedReader.readLine()) != null){
		String[] arr = str.split("\\t");	
               	Author2Paper a2p = new Author2Paper(arr[0],arr[1],arr[2]);
                WriteFileTask.tasks[0].writeToDisk(a2p);
           	cnt++;
		if(cnt % 1000 == 0)System.out.println(cnt);
	    }
        }catch (IOException e) {
            e.printStackTrace();
        }catch(NumberFormatException ne){
            System.out.println(cnt);
            ne.printStackTrace();
        }
    }
}
