package com.foxminded.university.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.foxminded.university.repository.TestRepository;

class FacultyTest {
    @Test
    void whenAddRemoveAreCalledThenResultIsRight() {
        Faculty expected = TestRepository.getFaculty_1();
        Faculty actual = TestRepository.getEmptyFaculty();
        actual.addDepartment(TestRepository.getDepartment_1());
        assertEquals(expected, actual);

        expected = TestRepository.getFaculty_2();
        actual.removeDepartment(TestRepository.getDepartment_1());
        assertEquals(expected, actual);
    }
}