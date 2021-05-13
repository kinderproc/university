package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Group> groups = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();

    public Department() {
    }

    public Department(String name, List<Group> groups, List<Teacher> teachers, List<Subject> subjects) {
        this.name = name;
        this.groups = groups;
        this.teachers = teachers;
        this.subjects = subjects;
    }

    public void addGroup(Group group) {
        groups.add(group);
        group.setDepartment(this);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
        group.setDepartment(null);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
