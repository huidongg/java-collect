package cn.hd.test.tool;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class 生成精选 {
    private static String jxSql = "INSERT INTO `xq_xiquan`.`question_bind` (`question_bind_id`, `question_id`, `ip_id`, `oper_detail`, `color`, `sort`, `type`, `is_delete`, `create_time`, `modify_time`) VALUES ('${question_bind_id}', '${question_id}', '${ip_id}', '${oper_detail}', '${color}', '${sort}', '10', '0', '1613640348225', '1613902484372');";
    public static void main(String[] args) throws IOException {
        List<String> operDetailList = Files.readAllLines(Paths.get("test/jx.txt"));
        Map<String, String> operDetailMap = operDetailList.stream().reduce(new LinkedHashMap<>(), (all, item)->{
            String [] arr = item.split("\t", 2);
            all.put(arr[0], arr[1]);
            return all;
        }, (v1, v2)->v1);
        // System.out.println(JSON.toJSONString(operDetailMap));

        List<String> envList = Files.readAllLines(Paths.get("test/env.txt"));
        Map<String, String> envMap = envList.stream().reduce(new LinkedHashMap<>(), (all, item)->{
            String [] arr = item.split("\t", 2);
            all.put(arr[0], "#" + arr[1]);
            return all;
        }, (v1, v2)->v1);
        //System.out.println(JSON.toJSONString(envMap));
        List<String> msgList = Files.readAllLines(Paths.get("test/message.txt"));
        Map<String, Jx> jxMap = msgList.stream().reduce(new LinkedHashMap<>(), (all, item)->{
            String [] arr = item.split("\t", 3);
            Jx jx = new Jx();
            jx.setQuestionId(arr[0]);
            jx.setIpId(arr[1]);
            jx.setColorName(arr[2]);
            all.put(arr[0], jx);
            return all;
        }, (v1, v2)->v1);
        // System.out.println(JSON.toJSONString(jxMap));
        List<Jx> jxList = new ArrayList<>();
        long id = 576489950831001601l;
        for (Map.Entry<String, String> entry : operDetailMap.entrySet()) {
            Jx jx = jxMap.get(entry.getKey());
            jx.setQuestionBindId(String.valueOf(id));
            jx.setOperDetail(operDetailMap.get(entry.getKey()));
            jx.setColor(envMap.get(jx.getColorName()));
            jxList.add(jx);
            id++;
        }
        // System.out.println(JSON.toJSONString(jxList));
        jxList.sort((x, y)->x.getQuestionId().compareTo(y.getQuestionId()));
        int sort = 0;
        for (Jx jx : jxList) {
            String sql = jxSql;
            sql = sql.replace("${question_bind_id}", jx.getQuestionBindId());
            sql = sql.replace("${question_id}", jx.getQuestionId());
            sql = sql.replace("${ip_id}", jx.getIpId());
            sql = sql.replace("${oper_detail}", jx.getOperDetail());
            String color;
            if (jx.getColor() == null) {
                color = "#272E36";
//                System.out.println("questionId=" + jx.getQuestionId());
            } else {
                color = jx.getColor();
            }
            sql = sql.replace("${color}", color);
            sql = sql.replace("${sort}", String.valueOf(sort));
            sort+=100;
            System.out.println(sql);
        }
    }

    @Data
    static class Jx {
        private String questionBindId;
        private String questionId;
        private String ipId;
        private String operDetail;
        private String color;
        private String colorName;
        private String sort;
    }
}
