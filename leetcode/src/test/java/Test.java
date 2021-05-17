import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Stu stu = new Stu();
        stu.setName("zs");
        List<String> zhuanList = new ArrayList<>();
        zhuanList.add("shuxue");
        zhuanList.add("yuwen");
        stu.setZhuanye(zhuanList);
        List<Cls> clsList = new ArrayList<>();
        Cls cls = new Cls();
        cls.setClsName("193");
        cls.setTecher("fenlaoshi");
        clsList.add(cls);
        stu.setClsList(clsList);
        Gao g = new Gao();
        dpc(stu, g);

        System.out.println(JSON.toJSONString(g));

    }

    static void dpc(Object s, Object d) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Field[] fields1 = s.getClass().getDeclaredFields();
        Field[] fields2 = d.getClass().getDeclaredFields();
        Map<String, Field> map = Arrays.stream(fields2).collect(Collectors.toMap(x -> x.getName(), y -> y));
        final StringBuilder vt = new StringBuilder();
        for (int i = 0; i < fields1.length; i++) {
            Field field1 = fields1[i];
            Field field2 = map.get(field1.getName());
            if (field2 == null) {
                continue;
            }
            field1.setAccessible(true);
            field2.setAccessible(true);
            Type type1 = field1.getGenericType();
            if (type1.getTypeName().startsWith("java.lang")) { // 基础属性值拷贝
                d.getClass().getMethod("set" + upperCase(field2.getName()), field1.getType()).invoke(d, field1.get(s));
//                field2.set(d, field1.get(s));
            } else if (type1.getTypeName().startsWith("java.util.List")) { // 集合拷贝
                // 查找泛型
                String fxn = fx(type1.getTypeName());
                if (fxn.startsWith("java.lang.List")) { // 如果是基础泛型, 直接拷贝
                    d.getClass().getMethod("set" + upperCase(field2.getName()), List.class).invoke(d, new Object[]{field1.get(s)});
                    // field2.set(d, field1.get(s));
                } else {
                    List list = new ArrayList();
                    for (Object ls : (List) field1.get(s)) {
                        Object oo = Class.forName(fxn).newInstance();
                        dpc(ls, oo);
                        list.add(oo);
                    }
                    d.getClass().getMethod("set" + upperCase(field2.getName()), List.class).invoke(d, new Object[]{list});
//                    field2.set(d, list);
                }
            } else {// 对象递归拷贝
                dpc(fields1, field2);
            }
        }
    }

    static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    static String fx(String rs) {
        String rgx = "<(.*?)>";
        Pattern pattern = Pattern.compile(rgx);
        Matcher m = pattern.matcher(rs);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    @Data
    static class Stu {
        private String name;
        private List<Cls> clsList;
        private List<String> zhuanye;
    }

    @Data
    static class Cls {
        private String clsName;
        private String techer;
    }

    static class Gao {
        private String name;
        private List<String> zhuanye;
        private String clsList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getZhuanye() {
            return zhuanye;
        }

        public void setZhuanye(List<String> zhuanye) {
            this.zhuanye = zhuanye;
        }

        public String getClsList() {
            return clsList;
        }

        public void setClsList(List<GCls> clsList) {
            this.clsList = clsList.toString();
        }
    }

    @Data
    static class GCls {
        private String clsName;
        private String techer;
    }

}
