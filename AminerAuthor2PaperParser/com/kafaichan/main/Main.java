package com.kafaichan.main;

import com.kafaichan.util.ReadFileTask;
import com.kafaichan.util.WriteFileTask;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();

        ReadFileTask readFileTask = new ReadFileTask("AMiner-Author2Paper.txt");
        readFileTask.startParse();
	

        System.out.println(System.currentTimeMillis()-start);
        System.out.println("Finish");

    }
}
