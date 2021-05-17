package cn.hd.test.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class FileSystem {
    public static int num;

    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(Paths.get("test/version.txt"));
        Map<String, UnityFileModel> mmap = new HashMap<>();
        list.forEach(x->{
            JSONObject jsonObject = JSON.parseObject(x);
            JSONObject mFiles = jsonObject.getJSONObject("m_files");
            mFiles.keySet().forEach(s-> {
                        JSONObject parseObject = mFiles.getJSONObject(s);
                        UnityFileModel model = JSON.toJavaObject(parseObject, UnityFileModel.class);
                        String scenemodel = parseObject.getString("m_fileName");
                        if (Pattern.compile("^scene_s.*\\d{17}+.*.+?.r$").matcher(scenemodel).find()||Pattern.compile("^model_m.*\\d{17}+.*.+?.r$").matcher(scenemodel).find()) {
                            mmap.put(scenemodel, model);
                        }
                    }
            );
        });
        System.out.println(JSON.toJSONString(mmap));
    }


    @Data
    public static class UnityFileModel {
        /**
         * 文件名称
         */
        private String m_fileName;
        /**
         * 文件hashcode值
         */
        private String hashcode;
        /**
         * 文件md5值
         */
        private String md5;
        /**
         * 依赖
         */
        private List<String> m_depends;
    }

}
