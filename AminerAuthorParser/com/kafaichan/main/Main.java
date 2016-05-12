package com.kafaichan.main;

import com.kafaichan.util.ReadFileTask;
import com.kafaichan.util.WriteFileTask;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        WriteFileTask.tasks[0].start();

        ReadFileTask readFileTask = new ReadFileTask("AMiner-Author.txt");
        readFileTask.startParse();
	
	try{
		WriteFileTask.tasks[0].join();
	}catch(InterruptedException e){
		e.printStackTrace();
	}

        System.out.println(System.currentTimeMillis()-start);
        System.out.println("Finish");

    }
}
