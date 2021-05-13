package com.foxminded.university.service.impl;

import static com.foxminded.university.repository.TestRepository.newGroup;
import static com.foxminded.university.repository.TestRepository.newGroupDto;
import static com.foxminded.university.repository.TestRepository.newGroupWithId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.foxminded.university.dao.repository.GroupRepository;
import com.foxminded.university.dao.repository.StudentRepository;
import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.exception.WrongParameterException;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.repository.TestRepository;

public class GroupServiceImplTest {
    private Group superHeroes = TestRepository.superHeroesWithId();
    private Student hulk = TestRepository.hulkWithId();
    private List<GroupDTO> testGroupDtos = TestRepository.getTestGroupDtos();
    private List<Group> testGroups = TestRepository.getTestGroupsWithId();
    private GroupDTO superHeroesDto = TestRepository.superHeroesDto();
    private GroupDTO newGroupDto = TestRepository.newGroupDto();

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenFindAllThenReturnsAllGroups() {
        List<GroupDTO> expected = testGroupDtos;
        when(groupRepository.findAll()).thenReturn(testGroups);
        List<GroupDTO> actual = groupService.findAll();
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void whenFindByIdThenRightGroupReturned() {
        GroupDTO expected = superHeroesDto;
        int id = expected.getId();
        when(groupRepository.getOne(id)).thenReturn(superHeroes);
        GroupDTO actual = groupService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void whenFindAllForStudentThenReturnsRightGroups() {
        int studentId = hulk.getId();
        List<GroupDTO> expected = testGroupDtos;
        when(groupRepository.findAllForStudent(studentId)).thenReturn(testGroups);
        List<GroupDTO> actual = groupService.findAllForStudent(studentId);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void whenGroupIdOnCreateIsNotZeroThenThrowWrongParameterExcepti() {
        assertThrows(WrongParameterException.class, () -> groupService.create(superHeroesDto));
    }

    @Test
    public void whenCreateTheGroupItsReturnedWithId() {
        when(groupRepository.save(newGroup())).thenReturn(newGroupWithId());
        GroupDTO dto = groupService.create(newGroupDto());
        assertNotEquals(dto.getId(), 0);
    }

    @Test
    public void whenGroupIdOnUpdateIsZeroThenThrowWrongParameterExcepti() {
        assertThrows(WrongParameterException.class, () -> groupService.update(newGroupDto));
    }

    @Test
    public void whenUpdateTheGroupTheSamerReturned() {
        GroupDTO expected = superHeroesDto;
        Group group = superHeroes;
        int id = group.getId();
        when(groupRepository.getOne(id)).thenReturn(superHeroes);
        when(groupRepository.save(group)).thenReturn(group);
        GroupDTO actual = groupService.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetNumberOfStudentsThenNumberIsRight() {
        int groupId = superHeroes.getId();
        when(studentRepository.countByGroupIdAndDeleted(groupId, false)).thenReturn(3L);
        when(groupRepository.getOne(groupId)).thenReturn(superHeroes);
        Long expected = 3L;
        Long actual = groupService.getNumberOfStudents(groupId);
        assertEquals(expected, actual);
    }
}
