package com.example.filteringcomplexobjects.generateObjects;

import com.example.filteringcomplexobjects.model.Student;
import com.example.filteringcomplexobjects.repositories.StudentRepository;
import com.example.filteringcomplexobjects.services.StudentServiceImpl;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GeneratorTest {

    @MockBean
    private StudentServiceImpl studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void givenDefaultConfiguration_thenGenerateSingleObject() {
        EasyRandom generator = new EasyRandom();
        Student person = generator.nextObject(Student.class);
        System.out.println(person);
        assertNotNull(person.getName());
        assertNotNull(person.getGrade());
        assertNotNull(person.getYear());
    }

    @Test
    public void generateStudents() {
        EasyRandom generator = new EasyRandom();
        List<Student> students = generator.objects(Student.class, 10000).toList();
        System.out.println(students.size());
        System.out.println(students.get(1).getSchoolClass());
        assertEquals(10000, students.size());
    }

    @Test
    public void generatorConfiguration() {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.stringLengthRange(3, 8);

        EasyRandom generator = new EasyRandom(parameters);
        List<Student> students = generator.objects(Student.class, 10000).toList();
        System.out.println(students.get(1).getYear());
        System.out.println(students.get(1).getName());
        System.out.println(students.get(1).getGrade());
        System.out.println(students.get(1).getSchoolClass());
    }

}
