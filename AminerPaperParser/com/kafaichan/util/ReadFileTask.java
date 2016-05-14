package com.kafaichan.util;

import com.kafaichan.model.Paper;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import com.kafaichan.util.Write2SQLTask;
/**
 * Created by kafaichan on 2016/5/12.
 */


public class ReadFileTask {
    private static String base_dir = "/home/jiahuichen/AminerData/";

    private static String filepath;
    private static final Pattern id_pattern = Pattern.compile("#index([^\\r\\n]*)");
    private static final Pattern title_pattern = Pattern.compile("#\\*([^\\r\\n]*)");
    private static final Pattern author_pattern = Pattern.compile("#@([^\\r\\n]*)");
    private static final Pattern affiliation_pattern = Pattern.compile("#o([^\\r\\n]*)");
    private static final Pattern year_pattern = Pattern.compile("#t ([0-9]*)");
    private static final Pattern venue_pattern = Pattern.compile("#c([^\\r\\n]*)");
    private static final Pattern refs_pattern = Pattern.compile("#% ([^\\r\\n]*)");
    private static final Pattern paper_abstract_pattern = Pattern.compile("#!([^\\r\\n]*)");

    private FileInputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private HashMap<String,Integer> confMap;
    private Set<String> yearSet;

    private Integer confIdx;

    public HashMap<String,Integer> getConfMap(){
	return confMap;    
    }

    public Set<String> getYearSet(){
	return yearSet;	 
    }

    public ReadFileTask(String filename){
        this.filepath = base_dir + filename;
        this.confMap = new HashMap<String,Integer>();
	this.confIdx = new Integer(1);
	this.yearSet = new HashSet<String>();

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

    private Integer getConfKey(String normalize_conference){
    	Integer obj = (Integer)confMap.get(normalize_conference);
	if(obj == null){
		confMap.put(normalize_conference, this.confIdx);
		obj = new Integer(this.confIdx); 
		this.confIdx+=1;
	}
	return obj;
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
                String paper_id = match(str,id_pattern);
                if(paper_id == null)continue;

                String title = fmatch(title_pattern);
                if(title == null)continue;
		title = title.replace("\\","\\\\").replace("\"","\\\"");
                String normalize_title = title.toLowerCase();

                String authors = fmatch(author_pattern);
		if(authors != null)authors = authors.replace("\\","\\\\").replace("\"","\\\"");
		else authors = new String(""); 

                String affiliation = fmatch(affiliation_pattern);
		if(affiliation != null)affiliation = affiliation.replace("\\","\\\\").replace("\"","\\\"");
		else affiliation = new String("");
		
                String year = fmatch(year_pattern);
                if(year != null)yearSet.add(year);
		
                String venue = fmatch(venue_pattern);
		
                String normalize_venue = null;
		Integer conf_key = null;
		if(venue != null) {
                    venue = venue.replace("\\","\\\\").replace("\"","\\\"");
		    normalize_venue = venue.toLowerCase();
                    conf_key = getConfKey(normalize_venue);
                }else{
			venue = new String("");
			normalize_venue = new String("");
		}

                ArrayList<Integer> refs = new ArrayList<Integer>();
                String line = bufferedReader.readLine();
                String result = match(line,refs_pattern);

                while(result != null){
                    if(result != null){
                        refs.add(Integer.parseInt(result));
                    }
                    line = bufferedReader.readLine();
                    result = match(line,refs_pattern);
                }
                String paper_abstract = match(line,paper_abstract_pattern);
		if(paper_abstract != null)paper_abstract.replace("\\","\\\\").replace("\"","\\\"");
		else paper_abstract = new String("");

                Paper paper = new Paper(paper_id, year, title,normalize_title, venue, normalize_venue,authors, affiliation, conf_key, paper_abstract, refs);
                cnt++;
		if(cnt % 1000 == 0){
			System.out.println(cnt);
                	WriteFileTask.tasks[0].sqltask.executeBatch();
		}
		WriteFileTask.tasks[0].writeToDisk(paper);
           	WriteFileTask.tasks[0].sqltask.insertPaper(paper);
	    }
        }catch (IOException e) {
            e.printStackTrace();
        }catch(NumberFormatException ne){
            System.out.println(cnt);
            ne.printStackTrace();
        }
    }
}
