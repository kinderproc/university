package com.foxminded.university.service;

import com.foxminded.university.dto.StudentDTO;

public interface StudentService extends FindService<StudentDTO> {
    StudentDTO create(StudentDTO dto);

    StudentDTO update(StudentDTO dto);

    void delete(int id);

    void restore(int id);

    boolean isInGroup(int id, int groupId);
}
