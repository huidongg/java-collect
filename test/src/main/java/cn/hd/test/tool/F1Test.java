package cn.hd.test.tool;

import lombok.Data;

import java.io.IOException;

public class F1Test {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
//        List<String> st = Files.readAllLines(Paths.get("test/f1.txt"));
//        String [] ar = st.get(0).split(", ");
//        for (String a:ar) {
//            System.out.println(a);
//        }

        Stu stu = new Stu();
        stu.setAge(23);
        stu.setName("zhangsan");
        Cls cls = new Cls();
        cls.setClsNum(193);
        stu.setCls(cls);
        Stu stuNew = stu.clone();
        System.out.println(stu);
        System.out.println(stuNew);
        stuNew.setAge(24);
        stuNew.setName("zs");
        System.out.println(stu);
        System.out.println(stuNew);

    }

    @Data
    public static class Stu implements Cloneable {
        private Integer age;
        private String name;
        private Cls cls;

        public Stu clone() throws CloneNotSupportedException {
            return (Stu)super.clone();
        }
    }

    @Data
    public static class Cls implements Cloneable {
        private Integer clsNum;
    }
}
