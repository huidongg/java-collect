package cn.hd.test.file;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlFileTest {
    public static void main(String[] args) throws IOException {

        URL url = new URL("http://res.vcoser.com.cn/Android/");
        URLConnection uc = url.openConnection();
        uc.setConnectTimeout(100000);
        uc.setReadTimeout(50000);
        uc.connect();
        InputStream in = uc.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String tmp ;
        List<String> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        while ((tmp =bufferedReader.readLine()) != null) {
            tmp = tmp.replaceAll("(<)(.*?)(>)", "");
            String [] x = tmp.split(" ");
            int len = x.length;
            if (x[0].length() == 16) {
                map.put(x[0], x[len - 1]);
            }
//            System.out.println( tmp.replaceAll("(<)(.*?)(>)", ""));
//            //Files.newBufferedWriter()
//            list.add(tmp.replaceAll("(<)(.*?)(>)", ""));
//            if (tmp.contains(".manifest")) {
//                s++;
//            } else {
//                Pattern r1 = Pattern.compile("(>)(.*?)(<)");
//                Matcher m1 = r1.matcher(tmp);
//                if (m1.find()) {
//                    String t = m1.group(2);
//                    if (t.length() == 16) {
//                        g++;
//                    }
//                }
//            }
//            if (!tmp.contains("manifest")) {
//                Pattern r1 = Pattern.compile("(>)(.*?)(<)");
//                Matcher m1 = r1.matcher(tmp);
//                if (m1.find()) {
//                    String t = m1.group(2);
//                    if (t.length() == 16) {
//                        System.out.println(m1.group(2) + " " + tmp.split("                                   ")[1].substring(18).trim());
//                        map.put(m1.group(2), tmp.split("                                   ")[1].substring(18).trim());
//                    }
//                }
//            }
            // 不包含manifest
//            Pattern r1 = Pattern.compile("(>)(((?!manifest).)*)(<)");


        }
//        Files.write(Paths.get("test/f2.txt"), list, StandardOpenOption.APPEND);
        bufferedReader.close();
        inputStreamReader.close();
        in.close();



//        List<String> list = Files.readAllLines(Paths.get("test/f1.txt"));
//        int s = 0;
//        int g = 0;
//        for (String x :list){
//            System.out.println( x.replaceAll("(<)(.*?)(>)", ""));
//
//        }
        System.out.println(map.size());

        System.out.println(JSON.toJSONString(map));
    }
}
