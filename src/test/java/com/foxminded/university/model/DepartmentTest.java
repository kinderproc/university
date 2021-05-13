package com.foxminded.university.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.foxminded.university.repository.TestRepository;

class DepartmentTest {
    @Test
    void whenAddRemoveAreCalledThenResultIsRight() {
        Department expected = TestRepository.getDepartment_1();
        Department actual = TestRepository.getEmptyDepartment();
        Group dummiesGroup = TestRepository.getDummiesGroup();
        Group geniusesGroup = TestRepository.getGeniusesGroup();
        actual.addGroup(dummiesGroup);
        actual.addGroup(geniusesGroup);
        actual.addTeacher(TestRepository.getJohnSmith());
        actual.addTeacher(TestRepository.getValentinaOctjabr());
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.COMPUTER_SCIENCE));
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.MATRIX_HACKING));
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.MARKSIZM));
        actual.addSubject(TestRepository.getSubjects().get(TestRepository.LENINIZM));
        assertEquals(expected, actual);
        assertEquals(dummiesGroup.getDepartment(), actual);
        assertEquals(geniusesGroup.getDepartment(), actual);

        expected = TestRepository.getDepartment_2();
        actual.removeGroup(TestRepository.getGeniusesGroup());
        actual.removeSubject(TestRepository.getSubjects().get(TestRepository.MARKSIZM));
        actual.removeSubject(TestRepository.getSubjects().get(TestRepository.LENINIZM));
        actual.removeTeacher(TestRepository.getValentinaOctjabr());
        assertEquals(expected, actual);
    }
}
