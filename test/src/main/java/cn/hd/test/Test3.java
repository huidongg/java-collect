package cn.hd.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

public class Test3 {
    public static void main(String[] args) {
        String json = "{\"screenWidth\":1080,\"screenHeight\":1920,\"relationId\":588787346937966592,\"subjectIdList\":[\"573913558682255360\",\"582150299517980672\"],\"type\":100,\"remove\":0}";
        QuestionVideoCutRequest request = JSONObject.parseObject(json, QuestionVideoCutRequest.class);
        System.out.println(JSON.toJSONString(request));
    }

    @Data
    public static class QuestionVideoCutRequest {
        private Long questionScriptId;
        // 100 帖子, 200 合拍
        private Integer type;
        // 屏幕类型,1-横版Pia剧本, 3-横版Vlog, 4-全屏Vlog, 5-交互
        private Integer screenType;
        // 0 定制剪切, 1原来剪切-貌似不用了...
        private Integer remove;
        // 屏幕宽度
        private Integer screenWidth;
        // 屏幕高度
        private Integer screenHight;
        /**
         * subjectId的json数组, 对应question表的subject_id_list字段
         */
        private String subjectIdList;
    }



}
