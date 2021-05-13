package com.foxminded.university.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.foxminded.university.repository.TestRepository;

class ScheduleTest {
    Schedule schedule = new Schedule(TestRepository.getJulySchedule());

    @Test
    void whenGetScheduleFrom1stTo9thOfJulyThenResultIsRight() throws ParseException {
        Date firstOfJuly = TestRepository.firstOfJuly();
        Date ninthOfJuly = TestRepository.ninthOfJuly();
        List<ScheduleItem> expected = TestRepository.getJulyScheduleFrom1stTo9th();
        List<ScheduleItem> actual = schedule.getSchedule(TestRepository.getJohnSmith(), firstOfJuly, ninthOfJuly);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void whenGetScheduleFrom10thTo29thOfJulyThenResultIsRight() throws ParseException {
        Date tenthOfJuly = TestRepository.tenthOfJuly();
        Date twentyNinthOfJuly = TestRepository.twentyNinthOfJuly();
        List<ScheduleItem> expected = TestRepository.getJulyScheduleFrom10To12();
        List<ScheduleItem> actual = schedule.getSchedule(TestRepository.getJohnSmith(), tenthOfJuly, twentyNinthOfJuly);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void whenGetScheduleFrom30thTo31stOfJulyThenResultIsRight() throws ParseException {
        Date thirtiethOfJuly = TestRepository.thirtiethOfJuly();
        Date thirtyfirstOfJuly = TestRepository.thirtyFirstOfJuly();
        List<ScheduleItem> expected = TestRepository.getJulyScheduleFrom30To31();
        List<ScheduleItem> actual = schedule.getSchedule(TestRepository.getJohnSmith(), thirtiethOfJuly,
            thirtyfirstOfJuly);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }
}
