package com.foxminded.university.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxminded.university.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer>, GroupRepositoryCustom {

}
