package cn.hd.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ValidateTest {
    public static void main(String[] args) {
        Valid valid1 = new Valid();
        valid1.setName("张老三");

        Valid valid2 = new Valid();
        valid2.setName("张老三");

        System.out.println(isEquals(valid1, valid2));
    }

    private static Boolean isEquals(Object obj1, Object obj2) {
        Method[] methods = obj1.getClass().getDeclaredMethods();
        for(Method method : methods) {
            if (method.getName().startsWith("get")) {
                try {
                    Object o1 = method.invoke(obj1);
                    Object o2 = method.invoke(obj2);
                    if (o1 == null) {
                        if (o2 == null) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (!o1.equals(o2)) {
                            return false;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    private static void validNoNull(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        final StringBuilder vt = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    throw new RuntimeException(field.getName() + "is null");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }
    }

    public static class Valid {
        private Integer age;
        private String name;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Valid{");
            sb.append("age=").append(age);
            sb.append(", name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
