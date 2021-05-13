package com.foxminded.university.dao.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.foxminded.university.dao.repository.GroupRepository;
import com.foxminded.university.dao.repository.StudentRepository;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.repository.TestRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StudentDaoImplIT {
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private Group dummies = TestRepository.dummies();
    private Group geniuses = TestRepository.geniuses();
    private Group superHeroes = TestRepository.superHeroes();
    private Student mark = TestRepository.mark();
    private Student michael = TestRepository.michael();
    private Student lorenzo = TestRepository.lorenzo();
    private List<Student> testStudents = TestRepository.getTestStudents();

    @Autowired
    Flyway flyway;

    @Autowired
    public StudentDaoImplIT(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @BeforeEach
    void init() {
        flyway.migrate();
        groupRepository.save(dummies);
        groupRepository.save(geniuses);
        groupRepository.save(superHeroes);
        mark.setGroup(geniuses);
        michael.setGroup(dummies);
        lorenzo.setGroup(dummies);
        studentRepository.save(mark);
        studentRepository.save(michael);
        studentRepository.save(lorenzo);
    }

    @Test
    public void whenFindAllThenReturnsALlStudents() {
        List<Student> expected = testStudents;
        List<Student> actual = (List<Student>) studentRepository.findAll();
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void whenFindByIdThenReturnsRightStudent() {
        Student expected = michael;
        int studentId = expected.getId();
        Student actual = studentRepository.findById(studentId).orElse(new Student());
        assertEquals(expected, actual);
    }

    @Test
    void whenCreateThenStudentIsCreated() {
        Student expected = new Student(0, "Mihaylo", "Lomonosov", 308, "Peagon Gleb", geniuses);
        studentRepository.save(expected);
        Student actual = studentRepository.findById(expected.getId()).orElse(new Student());
        assertEquals(actual, expected);
    }

    @Test
    void whenUpdateThenStudentIsModified() {
        Student expected = michael;
        Student actual = new Student();
        actual.setId(lorenzo.getId());
        actual.setName(expected.getName());
        actual.setSurname(expected.getSurname());
        actual.setGroup(expected.getGroup());
        actual.setAge(expected.getAge());
        actual.setPhone(expected.getPhone());
        studentRepository.save(actual);
        actual = studentRepository.findById(actual.getId()).orElse(new Student());
        assertEquals(actual, expected);
    }

    @Test
    void whenDeleteThenDeletedIsTrue() {
        Student student = lorenzo;
        int studentId = student.getId();
        student = studentRepository.findById(studentId).orElse(new Student());
        student.setDeleted(true);
        studentRepository.save(student);
        Student deletedStudent = studentRepository.findById(studentId).orElse(new Student());
        assertEquals(true, deletedStudent.isDeleted());
    }

    @AfterEach
    void clean() {
        flyway.clean();
    }
}
