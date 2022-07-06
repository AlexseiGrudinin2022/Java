package mongo.lesson1.model;

import java.util.Arrays;
import java.util.List;

public class Student {

    private String name;
    private int age;
    private List<String> courses;

    public Student() {

    }

    public Student(String name, int age, String... courses) {
        this.name = name;
        this.age = age;
        this.courses = Arrays.asList(courses);
    }

    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }


    public List<String> getCourses() {
        return courses;
    }

    private String showCourses() {
        String result = "";
        for (String course : courses) {
            result += "\t"+ course + "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return "Имя студента: " + name + "\n" +
                "Возраст студента: " + age + "\n" +
                "Курсы студента: \n" + showCourses();
    }


}
