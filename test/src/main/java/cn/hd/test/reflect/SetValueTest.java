package cn.hd.test.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetValueTest {
    static Map<Integer, SetM> map = new HashMap();
    public static void main(String[] args) {
        List<Object> list1 = new ArrayList<Object>();
        list1.add("张三");
        list1.add(40);

        List<Object> list2 = new ArrayList<Object>();
        list2.add("李四");
        list2.add(27);

        SetM setM1 = new SetM();
        setM1.setMeth("setName");
        setM1.setType(String.class);

        SetM setM2 = new SetM();
        setM2.setMeth("setAge");
        setM2.setType(Integer.class);

        map.put(0, setM1);
        map.put(1, setM2);
        Stu  stu1 = toStu(list1);

        System.out.println(stu1);

    }

    private static Stu toStu(List<Object> list) {
        Stu stu = new Stu();
        for (int i = 0; i < list.size(); i++) {
            try {
                Method m = stu.getClass().getDeclaredMethod(map.get(i).getMeth(), map.get(i).getType());
                m.invoke(stu, list.get(i));
            }catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return stu;
    }

    static class SetM {
        private String meth;
        private Class type;

        public String getMeth() {
            return meth;
        }

        public void setMeth(String meth) {
            this.meth = meth;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }
    }

    static class Stu {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Stu{");
            sb.append("name='").append(name).append('\'');
            sb.append(", age=").append(age);
            sb.append('}');
            return sb.toString();
        }
    }
}
