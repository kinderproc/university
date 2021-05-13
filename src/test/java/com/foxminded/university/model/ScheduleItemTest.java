package com.foxminded.university.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.foxminded.university.repository.TestRepository;

class ScheduleItemTest {
    @Test
    void whenAddRemoveAreCalledThenResultIsRight() {
        ScheduleItem expected = TestRepository.getScheduleItem_1();
        ScheduleItem actual = TestRepository.getEmptyScheduleItem();
        actual.addGroup(TestRepository.getDummiesGroup());
        actual.addGroup(TestRepository.getGeniusesGroup());
        assertEquals(expected, actual);

        expected = TestRepository.getScheduleItem_2();
        actual.removeGroup(TestRepository.getGeniusesGroup());
        assertEquals(expected, actual);
    }
}
