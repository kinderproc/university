package com.foxminded.university.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.foxminded.university.dao.repository.GroupRepository;
import com.foxminded.university.dao.repository.StudentRepository;
import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.exception.WrongParameterException;
import com.foxminded.university.model.Group;
import com.foxminded.university.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
    public static final String CREATE_WRONG_PARAMETER_EXCEPTION_MSG = "Parameter id is '%d', but should be '0'";
    public static final String UPDATE_WRONG_PARAMETER_EXCEPTION_MSG = "Parameter id shouldn't be equal '0'";

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<GroupDTO> findAll() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupsDTO = toDto(groups);
        return groupsDTO;
    }

    @Override
    public GroupDTO findById(int id) {
        Group group = groupRepository.getOne(id);
        return toDto(group);
    }

    @Override
    public List<GroupDTO> findAllForStudent(int studentId) {
        List<Group> groups = groupRepository.findAllForStudent(studentId);
        List<GroupDTO> groupsDTO = toDto(groups);
        return groupsDTO;
    }

    @Override
    public GroupDTO create(GroupDTO dto) {
        if (dto.getId() != 0) {
            String msg = String.format(CREATE_WRONG_PARAMETER_EXCEPTION_MSG, dto.getId());
            throw new WrongParameterException(msg);
        }

        Group group = toGroup(dto);
        group = groupRepository.save(group);
        return toDto(group);
    }

    @Override
    public GroupDTO update(GroupDTO dto) {
        if (dto.getId() == 0) {
            String msg = String.format(UPDATE_WRONG_PARAMETER_EXCEPTION_MSG, dto.getId());
            throw new WrongParameterException(msg);
        }

        Group group = toGroup(dto);
        group = groupRepository.save(group);
        return toDto(group);
    }

    @Override
    public void delete(int id) {
        Group group = groupRepository.getOne(id);
        group.setDeleted(true);
        groupRepository.save(group);
    }

    @Override
    public void restore(int id) {
        Group group = groupRepository.getOne(id);
        group.setDeleted(false);
        groupRepository.save(group);
    }

    private Group toGroup(GroupDTO dto) {
        Group group = new Group();
        int id = dto.getId();

        if (dto.getId() > 0) {
            group = groupRepository.getOne(id);
        }
        group.setName(dto.getName());

        return group;
    }

    private List<GroupDTO> toDto(List<Group> groups) {
        return groups.stream().map(group -> toDto(group)).collect(Collectors.toList());
    }

    private GroupDTO toDto(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDeleted(group.isDeleted());
        return groupDTO;
    }

    @Override
    public Long getNumberOfStudents(int groupId) {
        return studentRepository.countByGroupIdAndDeleted(groupId, false);
    }
}
