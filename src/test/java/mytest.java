import com.kafaichan.util.ReadFileTask;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kafaichan on 2016/5/13.
 */


public class mytest {

    @Test
    public void MatchTest(){
        String line = null;
        Pattern title_pattern = Pattern.compile("#\\*([^\\r\\n]*)");

//        System.out.println(line);
//        System.out.println(line.trim());
//        System.out.println(title_pattern.pattern());

        Matcher m = title_pattern.matcher(line);

        if(m.find()){
//            System.out.println("Found!");
            System.out.println(m.group(1).trim().length());
        }else{
            System.out.println("Not Found!!");
        }

    }

    @Test
    public void TrimTest(){
        String a = "abc\r\n";
        String b = a.trim();
        System.out.print(a);
        System.out.print(b);
        System.out.print("end");
    }

    @Test
    public void TestReadFileTask(){
        ReadFileTask readFileTask = new ReadFileTask("Aminer-Paper.txt");
        readFileTask.startParse();
    }

}
