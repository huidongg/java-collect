package cn.hd.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test5 {

    public static void main(String[] args) {
//        String str ="{ \"@timestamp\": \"07/Apr/2021:15:49:54  0800\", \"@fields\": { \"remote_addr\": \"192.168.13.242\", \"http_host\": \"web_test.vcoser.vip\",\"body_bytes_sent\": \"70\", \"request_time\": \"0.016\", \"status\": \"500\", \"request\": \"POST /open/v1.4.4.1/login/connectionCount HTTP/1.1\", \"request_method\": \"POST\", \"http_referrer\": \"-\", \"upstream_addr\": \"10.163.1.21:14004\",\"upstream_status\": \"500\",\"upstream_response_time\": \"0.013\",\"request_body\": \"{\"appBusinessCode\":\"\",\"appPlatform\":\"\",\"clientType\":\"android\",\"clientVersion\":\"v2.0.5\",\"clientVersionCode\":\"94\",\"deviceCode\":\"846e9351f66cead5\",\"deviceScreenSize\":\"1176*2328\",\"deviceType\":\"LIO-AL00\",\"deviceVersion\":\"10\",\"eventData\":{\"ipId\":\"502155479463731200\",\"triggerDate\":1617781794268,\"subjects\":\"[{\\\"createTime\\\":0,\\\"icon\\\":\\\"\\\",\\\"ipId\\\":0,\\\"isDelete\\\":0,\\\"modifyTime\\\":0,\\\"subjectContent\\\":\\\"凹凸世界\\\",\\\"subjectId\\\":502155479463731200,\\\"usedNum\\\":0,\\\"userId\\\":0},{\\\"createTime\\\":0,\\\"icon\\\":\\\"\\\",\\\"ipId\\\":0,\\\"isDelete\\\":0,\\\"modifyTime\\\":0,\\\"subjectContent\\\":\\\"金\\\",\\\"subjectId\\\":546354940249837568,\\\"usedNum\\\":0,\\\"userId\\\":0}]\",\"vlogId\":\"586295823061245952\",\"fingerprint\":\"8B96CEEDBF5C139608BE5D7F83BE32A9\",\"personModelId\":\"569537651107528704\",\"ownerId\":\"585799373051158528\",\"triggerTime\":\"2021-04-07 15:49:54\",\"userId\":\"-1\"},\"eventId\":\"Browse_Vlog\",\"macAddress\":\"02:00:00:00:00:00\",\"msgCode\":\"/open/v1.4.4.1/login/connectionCount\",\"networkType\":\"wifi\",\"tnpsToken\":\"084947760a0e873c966898a2d3b862b5e090\"}\",\"http_x_forwarded_for\": \"-\", \"http_user_agent\": \"okhttp/4.4.0\" } }";
////        String pattern = "(.*?)(request_body*)(.*}\")";
////        String pattern = "request_body.*}\",";
//        String pattern = ".*(?<=request_body).+(?=}\",)";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(str);
//        if (m.find()) {
//            System.out.println(m.group());
//        }
//        System.out.println(LocalDate.now().getDayOfYear());

//        String rs = "${ip_name}！${ip_name}！${ip_name}！";
//        String word = "}";
//        if (word.contains("$") || word.contains("{") || word.contains("}")) {
//            System.out.println("aa");
//        }
//
//        String rg = rs.replaceAll("(\\$\\{)([\\w]+)(\\})", "刀哥");
//        System.out.println(rg);

        //

//        for (int i = 1; i < 6; i++) {
////          System.out.println(ysh(10, i, 6));
//
//            List<Integer> list = ysh(10, i, 6);
//            List<String> mbList = new ArrayList<>();
//
//            for (int j = 0; j < 10; j++) {
//                mbList.add("a+" + j);
//            }
//
//            for (Integer r : list) {
//                mbList.add(r-((i-1)*10), "9527");
//            }
//            System.out.println(mbList);
//
//        }

//        String x = "[{\"Action\":\"指人说教\",\"Sound \":\"\",\"Aside \":\"\",\"Emoji\":\"\",\"Lines\":\"看你吃的这么开心，好吃吗？要不要加点什么？\"}]";
//        List<Action> list = JSON.parseArray(x, Action.class);
//        for (Action action : list) {
//            System.out.println(action);
//        }




    }

//    @Data
//    public static class Action {
//        @JsonProperty("Action")
//        private String action;
//        @JsonProperty("Lines")
//        private String lines;
//    }

    public static List<Integer> ysh(int pageSize, int pageNum, int fg) {
        // 最大分隔数
        int s = (pageSize * pageNum) / fg;
        List<Integer> rt = new ArrayList<>();
        int c;
        while ((c= s-- * fg) > pageSize * (pageNum - 1)) {
            rt.add(c);
        }
        return rt.stream().sorted().collect(Collectors.toList());
    }
}
