package com.foxminded.university.dao.impl;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.foxminded.university.dao.repository.GroupRepository;
import com.foxminded.university.dao.repository.StudentRepository;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.repository.TestRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GroupDaoImplIT {
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private Group dummies = TestRepository.dummies();
    private Group geniuses = TestRepository.geniuses();
    private Group superHeroes = TestRepository.superHeroes();
    private Student mark = TestRepository.mark();
    private Student michael = TestRepository.michael();
    private Student lorenzo = TestRepository.lorenzo();
    private List<Group> testGroups = TestRepository.getTestGroups();

    @Autowired
    Flyway flyway;

    @Autowired
    public GroupDaoImplIT(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @BeforeEach
    void init() {
        flyway.migrate();
        groupRepository.save(dummies);
        groupRepository.save(geniuses);
        mark.setGroup(geniuses);
        michael.setGroup(dummies);
        lorenzo.setGroup(dummies);
        studentRepository.save(mark);
        studentRepository.save(michael);
        studentRepository.save(lorenzo);
    }

    @Test
    void whenFindAllThenReturnsAllGroups() {
        List<Group> expected = testGroups;
        List<Group> actual = (List<Group>) groupRepository.findAll();
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void whenfindAllForStudentThenRelatedDeletedGroupIsReturned() {
        int groupId = lorenzo.getGroup().getId();
        Group group = groupRepository.findById(groupId).orElse(new Group());
        group.setDeleted(true);
        groupRepository.save(group);
        List<Group> groupList = groupRepository.findAllForStudent(lorenzo.getId());
        assertThat(groupList, hasItems(group));

        groupId = geniuses.getId();
        group = groupRepository.findById(groupId).orElse(new Group());
        group.setDeleted(true);
        groupRepository.save(group);
        groupList = groupRepository.findAllForStudent(lorenzo.getId());
        assertThat(groupList, not(hasItems(group)));
    }

    @Test
    public void whenFindByIdThenReturnsRightGroup() {
        Group expected = geniuses;
        int groupId = expected.getId();
        Group actual = groupRepository.findById(groupId).orElse(new Group());
        assertEquals(expected, actual);
    }

    @Test
    void whenCreateThenGroupIsCreated() {
        Group expected = superHeroes;
        groupRepository.save(expected);
        Group actual = groupRepository.findById(expected.getId()).orElse(new Group());
        assertEquals(actual, expected);
    }

    @Test
    void whenUpdateThenGroupIsModified() {
        Group expected = new Group();
        expected.setId(dummies.getId());
        expected.setName("Strangers");
        groupRepository.save(expected);
        Group actual = groupRepository.findById(expected.getId()).orElse(new Group());
        assertEquals(actual.getName(), expected.getName());
    }

    @Test
    void whenDeleteThenDeletedIsTrue() {
        int groupId = dummies.getId();
        Group group = groupRepository.findById(groupId).orElse(new Group());
        group.setDeleted(true);
        groupRepository.save(group);
        Group deletedGroup = groupRepository.findById(groupId).orElse(new Group());
        assertEquals(true, deletedGroup.isDeleted());
    }

    @AfterEach
    void clean() {
        flyway.clean();
    }
}
