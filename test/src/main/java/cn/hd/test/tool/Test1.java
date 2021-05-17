package cn.hd.test.tool;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Test1 {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        We we = transfer();
        System.out.println(we.getName() + " " + we.getAge());
    }

    private static <T> T transfer(Class<T> c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhanglaosan");
        T o = c.newInstance();
        BeanUtils.populate(o, map);
        return o;
    }

    private static We transfer() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        We we = We.class.newInstance();
        // we.setAge(25);
        Map<String, String> map = new HashMap<>();
        map.put("age", "25");
        map.put("name", "zhanglaosan");

        BeanUtils.populate(we, map);
        return we;
    }

    @Data
    public static class We {
        private String name;
        private Integer age;
    }
}
