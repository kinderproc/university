package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private List<Subject> subjects = new ArrayList<>();
    private Position position = Position.BACHELOR;

    public Teacher() {
    }

    public Teacher(String name, String surname, int age, String phone, Position position) {
        super(name, surname, age, phone);
        this.position = position;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Teacher other = (Teacher) obj;
        return position.equals(other.position);
    }
}
