package cn.hd.test;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String str = "您好，您所购买的[${mealName}]到期时间为[${endTime}]，请您注意到期时间！为了能更专业的提供医疗服务，我们需要收集您的病例资料，请您在方便的时间尽快与医生助理[${assistantPhone}]联系，协助您整理您的病例资料，及早提供给专家，并预约时间，开启服务。";
        String rgex = "\\{(.*?)\\}";
        // System.out.println(getSubUtilSimple(str, rgex));
        List<String> propList = getSubUtil(str,rgex);
        OrderNoticeInfo info = new OrderNoticeInfo();
        info.setMealName("laosiA套餐");
        info.setEndTime("2021-12-01");
        info.setAssistantPhone("13555");
        info.setExpertName("zs");
        Map<String, String> map = new HashMap<>();
        for (String prop : propList) {
            try {
                Method m = info.getClass().getDeclaredMethod("get" + Character.toUpperCase(prop.charAt(0)) + prop.substring(1, prop.length()));
                map.put(prop, (String) m.invoke(info));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(map);
    }


    @Data
    private static class OrderNoticeInfo {
        // 用户姓名
        private String userName;
        // 用户手机号
        private String userPhone;
        // 业务内容
        private String serviceContent;
        // 通知日期
        private String noticeDate;
        // 套餐名称
        private String mealName;
        // 订单结束日期
        private String endTime;
        // 专家名
        private String expertName;
        // 发送用户的短信模板id
        private String userSmsTemId;
        // 发送自定义服务的用户的短信模板id
        private String userCustSmsTemId;
        // 发送助理的短信模板id
        private String assistantSmsTemId;
        // 发送自定义服务的助理的短信模板id
        private String assistantCustSmsTemId;
        // 助理手机号
        private String assistantPhone;

    }

    public static List<String> getSubUtil(String soap,String rgex){
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }

}
