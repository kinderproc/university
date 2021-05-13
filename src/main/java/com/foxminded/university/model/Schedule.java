package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule {
    private final String WRONG_ARGUMENT_TYPE = "Argument person at getSchedule has wrong type and can't be processed";

    private List<ScheduleItem> items = new ArrayList<>();

    public Schedule() {
    }

    public Schedule(List<ScheduleItem> scheduleItems) {
        this.items = scheduleItems;
    }

    public void add(ScheduleItem item) {
        items.add(item);
    }

    public void remove(ScheduleItem item) {
        items.remove(item);
    }

    public List<ScheduleItem> getSchedule(Person person, Date from, Date to) {
        if (!(person instanceof Teacher) && !(person instanceof Student)) {
            throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
        }

        List<ScheduleItem> result = new ArrayList<>();

        if (person instanceof Teacher) {
            result = items.stream()
                .filter(s -> s.getDate().after(from) || s.getDate().equals(from))
                .filter(s -> s.getDate().before(to) || s.getDate().equals(to))
                .filter(s -> s.getTeacher().equals((Teacher) person)).collect(Collectors.toList());
        } else if (person instanceof Student) {
            result = items.stream()
                .filter(s -> s.getDate().after(from) || s.getDate().equals(from))
                .filter(s -> s.getDate().before(to) || s.getDate().equals(to))
                .filter(s -> s.getGroups().contains(((Student) person).getGroup())).collect(Collectors.toList());
        }

        return result;
    }

    public List<ScheduleItem> getItems() {
        return items;
    }

    public void setItems(List<ScheduleItem> items) {
        this.items = items;
    }
}
