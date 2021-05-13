package com.foxminded.university.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.foxminded.university.dao.repository.GroupRepository;
import com.foxminded.university.dao.repository.StudentRepository;
import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.exception.WrongParameterException;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    public static final String CREATE_WRONG_PARAMETER_EXCEPTION_MSG = "Parameter id is '%d', but should be '0'";
    public static final String UPDATE_WRONG_PARAMETER_EXCEPTION_MSG = "Parameter id shouldn't be equal '0'";

    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<StudentDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentsDTO = students.stream()
            .map(student -> toDto(student))
            .collect(Collectors.toList());

        return studentsDTO;
    }

    @Override
    public StudentDTO findById(int id) {
        Student student = studentRepository.getOne(id);

        return toDto(student);
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        if (dto.getId() != 0) {
            String msg = String.format(CREATE_WRONG_PARAMETER_EXCEPTION_MSG, dto.getId());
            throw new WrongParameterException(msg);
        }

        Student student = toStudent(dto);
        student = studentRepository.save(student);

        return toDto(student);
    }

    @Override
    public StudentDTO update(StudentDTO dto) {
        if (dto.getId() == 0) {
            String msg = String.format(UPDATE_WRONG_PARAMETER_EXCEPTION_MSG, dto.getId());
            throw new WrongParameterException(msg);
        }

        Student student = toStudent(dto);
        student = studentRepository.save(student);

        return toDto(student);
    }

    @Override
    public void delete(int id) {
        Student student = studentRepository.getOne(id);
        student.setDeleted(true);
        studentRepository.save(student);
    }

    @Override
    public void restore(int id) {
        Student student = studentRepository.getOne(id);
        student.setDeleted(false);
        studentRepository.save(student);
    }

    @Override
    public boolean isInGroup(int id, int groupId) {
        return studentRepository.existsStudentByIdAndGroupId(id, groupId);
    }

    private StudentDTO toDto(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setAge(String.valueOf(student.getAge()));
        studentDTO.setPhone(student.getPhone());
        studentDTO.setDeleted(student.isDeleted());

        Group group = student.getGroup();
        if (group != null) {
            studentDTO.setGroupId(group.getId());
            studentDTO.setGroupName(group.getName());
        }

        return studentDTO;
    }

    private Student toStudent(StudentDTO dto) {
        Student student = new Student();

        if (dto.getId() > 0) {
            student = studentRepository.getOne(dto.getId());
        }
        student.setName(dto.getName());
        student.setSurname(dto.getSurname());
        student.setAge(Integer.valueOf(dto.getAge()));
        student.setPhone(dto.getPhone());

        if (dto.getGroupId() > 0) {
            Group group = this.groupRepository.getOne(dto.getGroupId());
            student.setGroup(group);
        } else {
            student.setGroup(null);
        }

        return student;
    }
}
