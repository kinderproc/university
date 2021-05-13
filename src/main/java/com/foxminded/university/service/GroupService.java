package com.foxminded.university.service;

import java.util.List;

import com.foxminded.university.dto.GroupDTO;

public interface GroupService extends FindService<GroupDTO> {

    GroupDTO create(GroupDTO dto);

    GroupDTO update(GroupDTO dto);

    void delete(int id);

    void restore(int id);

    List<GroupDTO> findAllForStudent(int studentId);

    Long getNumberOfStudents(int groupId);
}
