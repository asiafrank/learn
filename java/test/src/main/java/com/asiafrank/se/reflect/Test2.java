package com.asiafrank.se.reflect;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class Test2 {

    public static void main(String[] args) throws Exception {
        Student s = new Student();
        s.setName("asiafrank");
        s.setGender("boy");
        s.setPhone("18868945420");

        Pattern p = Pattern.compile("^get.+");
        Class<Student> c = Student.class;
        for (Method m : c.getDeclaredMethods()) {
            if (p.matcher(m.getName()).matches()) {
                System.out.println(m.getReturnType());
                System.out.println(m.invoke(s));
            }
        }
    }
}

class Student {
    private String name;
    private String gender;
    private String phone;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
