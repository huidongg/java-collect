package cn.hd.test.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class 更新精选的颜色 {
    private static String jxSql = "update question_bind set color ='${color}', modify_time = 1617359583000 where question_id =${questionId} and is_delete = 0;";

    public static void main(String[] args) throws IOException {
        List<String> envList = Files.readAllLines(Paths.get("test/env.txt"));
        Map<String, String> envMap = envList.stream().reduce(new LinkedHashMap<>(), (all, item)->{
            String [] arr = item.split("\t", 2);
            all.put(arr[0], "#" + arr[1]);
            return all;
        }, (v1, v2)->v1);
        List<String> msgList = Files.readAllLines(Paths.get("test/message.txt"));
        Map<String, 生成精选.Jx> jxMap = msgList.stream().reduce(new LinkedHashMap<>(), (all, item)->{
            String [] arr = item.split("\t", 3);
            生成精选.Jx jx = new 生成精选.Jx();
            jx.setQuestionId(arr[0]);
//            jx.setIpId(arr[1]);
            jx.setColorName(arr[2]);
            all.put(arr[0], jx);
            return all;
        }, (v1, v2)->v1);
        List<生成精选.Jx> jxList = new ArrayList<>();
        long id = 576489950831001601l;
        for (Map.Entry<String, 生成精选.Jx> entry : jxMap.entrySet()) {
            生成精选.Jx jx = entry.getValue();
            String color = envMap.get(jx.getColorName());
            if (color == null) {
                continue;
            }

            String sql = jxSql;
            sql = sql.replace("${questionId}", jx.getQuestionId());
            sql = sql.replace("${color}", color);

            System.out.println(sql);
        }

    }
}
