package com.example.filteringcomplexobjects.services;

import com.example.filteringcomplexobjects.enums.SchoolClass;
import com.example.filteringcomplexobjects.model.Student;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    @Override
    public List<Student> generateStudents() {
        EasyRandom generator = new EasyRandom();
        List<Student> students = generator.objects(Student.class, 10000).collect(Collectors.toList());
        System.out.println(students.size());
        return students;
    }

    public Student studentClass() {
        Student student = new Student();

        student.setName("Marko");
        student.setYear(2023);
        student.setGrade(50);
        student.setSchoolClass(SchoolClass.MATH);

        return student;
    }

}
