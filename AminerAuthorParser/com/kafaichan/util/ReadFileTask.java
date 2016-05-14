package com.kafaichan.util;

import com.kafaichan.model.Person;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by kafaichan on 2016/5/12.
 */


public class ReadFileTask {
    private static String base_dir = "/home/jiahuichen/AminerData/";

    private static String filepath;
    private static final Pattern id_pattern = Pattern.compile("#index([^\\r\\n]*)");
    private static final Pattern name_pattern = Pattern.compile("#n([^\\r\\n]*)");
    private static final Pattern affiliation_pattern = Pattern.compile("#a([^\\r\\n]*)");
    private static final Pattern pc_pattern = Pattern.compile("#pc ([0-9]*)");
    private static final Pattern cn_pattern = Pattern.compile("#cn ([0-9]*)");
    private static final Pattern hi_pattern = Pattern.compile("#hi ([0-9]*)");
    private static final Pattern pi_pattern = Pattern.compile("#pi ([0-9]*\\.[0-9]+)");
    private static final Pattern upi_pattern = Pattern.compile("#upi ([0-9]*\\.[0-9]+)");
    private static final Pattern keyterm_pattern = Pattern.compile("#t([^\\r\\n]*)");

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


    private String match(String line, Pattern p){
        Matcher m = p.matcher(line);
        String result = null;

        if(m.find()){
            String info = m.group(1).trim();
            if(info.length() != 0)result = info;
        }
        return result;
    }

    public String fmatch(Pattern p) throws IOException {
        String line = bufferedReader.readLine();
        if(line == null)return null;
        return match(line,p);
    }


    public void startParse(){
	System.out.println("startParse");
        String str;
        int cnt = 1;
        try {
            while((str=bufferedReader.readLine()) != null){
                if(str.length() == 0){
                    continue;
                }
                String person_id  = match(str,id_pattern);
                if(person_id == null)continue;

                String name  = fmatch(name_pattern);
                if(name == null)continue;
		name = name.replace("\\","\\\\").replace("\"","\\\"");

                String affiliation = fmatch(affiliation_pattern);
		if(affiliation != null)affiliation = affiliation.replace("\\","\\\\").replace("\"","\\\"");
		String pc = fmatch(pc_pattern);
		String cn = fmatch(cn_pattern);
		String hi = fmatch(hi_pattern);
		String pi = fmatch(pi_pattern);
		String upi = fmatch(upi_pattern);
		String keyterms = fmatch(keyterm_pattern);
		if(keyterms != null)keyterms = keyterms.replace("\\","\\\\").replace("\"","\\\"");
                Person person = new Person(person_id,name,affiliation,pc,cn,hi,pi,upi,keyterms);
		cnt++;
		if(cnt % 10000 == 0){
			WriteFileTask.tasks[0].sqltask.executeBatch();
			System.out.println(cnt);
		}
                WriteFileTask.tasks[0].writeToDisk(person);
		WriteFileTask.tasks[0].sqltask.insertPerson(person);
           }
        }catch (IOException e) {
            e.printStackTrace();
        }catch(NumberFormatException ne){
            System.out.println(cnt);
            ne.printStackTrace();
        }
    }
}
