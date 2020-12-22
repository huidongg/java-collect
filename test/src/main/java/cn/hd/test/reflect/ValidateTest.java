package cn.hd.test.reflect;

import java.lang.reflect.Field;

public class ValidateTest {
    public static void main(String[] args) {
        Valid valid = new Valid();
        valid.setAge(26);
        valid.setName(null);
        validNoNull(valid);
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
