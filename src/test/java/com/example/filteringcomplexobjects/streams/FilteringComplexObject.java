package com.example.filteringcomplexobjects.streams;

import com.example.filteringcomplexobjects.enums.SchoolClass;
import com.example.filteringcomplexobjects.model.Student;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FilteringComplexObject {

    StopWatch watch = new StopWatch();

    static Map<String, List<Student>> studentMap = new HashMap<>();

    private static void statistic(List<Student> students1) {
        List<Student> gt50 = students1.stream().filter(s -> s.getGrade() > 50).toList();
        List<Student> math = gt50.stream().filter(s -> s.getSchoolClass() == SchoolClass.MATH).toList();
        List<Student> name = math.stream().filter(s -> s.getName().length() == 5).toList();
        System.out.println("How many object are in 1. list: " + gt50.size() + " 2. list: " + math.size() + " 3. list: " + name.size());
    }

    @BeforeAll
    public static void setup() {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.stringLengthRange(3, 8);
        EasyRandom generator = new EasyRandom(parameters);

        studentMap.put("100", generator.objects(Student.class, 100).toList());
        studentMap.put("1000", generator.objects(Student.class, 1000).toList());
        studentMap.put("10000", generator.objects(Student.class, 10000).toList());
        studentMap.put("100000", generator.objects(Student.class, 100000).toList());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void multiFiltering(String key) {

        List<Student> students = studentMap.get(key);
        watch.start();
        students.stream()
                .filter(s -> s.getGrade() > 50)
                .filter(s -> s.getSchoolClass() == SchoolClass.MATH)
                .filter(s -> s.getName().length() == 5)
                .collect(Collectors.toList());
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with multi filter stream: " + watch.getTotalTimeSeconds());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void complexConditionFiltering(String key) {

        List<Student> students = studentMap.get(key);

        watch.start();
        students.stream()
                .filter(s -> s.getGrade() > 50
                && s.getSchoolClass() == SchoolClass.MATH
                && s.getName().length() == 5)
                .collect(Collectors.toList());
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with complex condition filter stream: " + watch.getTotalTimeNanos());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void forLoopFiltering(String key) {

        List<Student> students = studentMap.get(key);
        List<Student> filteredStudents =new ArrayList<>();

        watch.start();
        for (Student student:students) {
            if (student.getGrade() > 50
                    && student.getName().length() == 5
                    && student.getSchoolClass() == SchoolClass.MATH) {
                filteredStudents.add(student);
            }
        }
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with for loop: " + watch.getTotalTimeNanos());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void parallelStreamMultiFiltering(String key) {

        List<Student> students = studentMap.get(key);

        watch.start();
        students.parallelStream()
                .filter(s -> s.getGrade() > 50)
                .filter(s -> s.getSchoolClass() == SchoolClass.MATH)
                .filter(s -> s.getName().length() == 5)
                .collect(Collectors.toList());
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with multi filter parallel stream: " + watch.getTotalTimeNanos());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void parallelStreamComplexConditionFiltering(String key) {

        List<Student> students = studentMap.get(key);

        watch.start();
        students.parallelStream()
                .filter(s -> s.getGrade() > 50
                && s.getSchoolClass() == SchoolClass.MATH
                && s.getName().length() == 5)
                .collect(Collectors.toList());
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with complex condition filter parallel stream: " + watch.getTotalTimeNanos());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void parallelStreamMultiFilteringWithPredicates(String key) {

        List<Student> students = studentMap.get(key);

        Predicate<Student> f1 = s -> s.getGrade() > 50;
        Predicate<Student> f2 = s -> s.getSchoolClass() == SchoolClass.MATH;
        Predicate<Student> f3 = s -> s.getName().length() == 5;

        watch.start();
        students.parallelStream()
                .filter(f1)
                .filter(f2)
                .filter(f3)
                .collect(Collectors.toList());
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with multi filter parallel stream with Predicates: " + watch.getTotalTimeNanos());

    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "1000", "10000", "100000"})
    public void parallelStreamFilteringWithPredicatesUsingAnd(String key) {

        List<Student> students = studentMap.get(key);

        Predicate<Student> f1 = s -> s.getGrade() > 50;
        Predicate<Student> f2 = s -> s.getSchoolClass() == SchoolClass.MATH;
        Predicate<Student> f3 = s -> s.getName().length() == 5;

        watch.start();
        students.parallelStream()
                .filter(f1.and(f2.and(f3)))
                .collect(Collectors.toList());
        watch.stop();

        statistic(students);
        System.out.println("Total execution time to filter " + key + " items with complex condition with and filter parallel stream Predicates: " + watch.getTotalTimeNanos());

    }

}
