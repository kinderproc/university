package com.foxminded.university.dao.repository;

import java.util.List;

import com.foxminded.university.model.Group;

public interface GroupRepositoryCustom {
    public List<Group> findAllForStudent(int studentId);
}
