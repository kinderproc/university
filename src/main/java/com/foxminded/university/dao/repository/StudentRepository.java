package com.foxminded.university.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxminded.university.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Long countByGroupIdAndDeleted(int groupId, boolean deleted);

    boolean existsStudentByIdAndGroupId(int id, int groupId);
}
