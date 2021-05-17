package cn.hd.test;

import com.sun.deploy.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("mealName", "套餐");
        map.put("endTime", "2021-01-05");
        map.put("assistantPhone", "1555443434");
        String patternString = "\\$\\{(" + StringUtils.join(map.keySet(), "|") + ")\\}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher("您好，您所购买的[${mealName}]到期时间为[${endTime}]，请您注意到期时间！为了能更专业的提供医疗服务，我们需要收集您的病历资料，请您在方便的时间尽快与医生助理[${assistantPhone}]联系，协助您整理您的病历资料，及早提供给专家，并预约时间，开启服务。");
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            String str = matcher.group(1);
            if(str!=null){
                String token = map.get(str);
                if(token!=null){
                    matcher.appendReplacement(sb, token);
                }
            }
        }
        matcher.appendTail(sb);
        System.out.println(sb);

    }
}
