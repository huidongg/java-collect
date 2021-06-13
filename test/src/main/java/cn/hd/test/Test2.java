package cn.hd.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        List<Stu> list = new ArrayList<>();
        Stu stu1 = new Stu();
        stu1.setName("zhangsan");
        stu1.setAge(25);
        list.add(stu1);
        Stu stu2 = new Stu();
        stu2.setName("lisi");
        stu2.setAge(34);
        list.add(stu2);
        System.out.println(JSON.toJSONString(list));
        String json = JSON.toJSONString(list);
        List<Stu> list1 = JSON.parseArray(json, Stu.class);
        System.out.println(JSONArray.toJSONString(list1));
    }
    @Data
    static class Stu {
        private String name;
        private Integer age;
    }
}
