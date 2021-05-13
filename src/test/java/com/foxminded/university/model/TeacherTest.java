package com.foxminded.university.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.foxminded.university.repository.TestRepository;

class TeacherTest {
    @Test
    void whenAddRemoveAreCalledThenResultIsRight() {
        Teacher expected = TestRepository.getTeacher_1();
        Teacher actual = new Teacher();
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.COMPUTER_SCIENCE));
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.MARKSIZM));
        assertEquals(expected, actual);

        expected = TestRepository.getTeacher_2();
        actual.removeSubject(TestRepository.getSubjects().get(TestRepository.COMPUTER_SCIENCE));
        actual.removeSubject(TestRepository.getSubjects().get(TestRepository.MARKSIZM));
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.MATRIX_HACKING));
        assertEquals(expected, actual);
    }
}
