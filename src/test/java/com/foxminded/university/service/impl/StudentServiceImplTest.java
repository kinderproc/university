package com.foxminded.university.service.impl;

import static com.foxminded.university.repository.TestRepository.newStudentDtoWithId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.foxminded.university.dao.repository.GroupRepository;
import com.foxminded.university.dao.repository.StudentRepository;
import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.exception.WrongParameterException;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;
import com.foxminded.university.repository.TestRepository;

public class StudentServiceImplTest {
    private Group superHeroes = TestRepository.superHeroesWithId();
    private Student hulk = TestRepository.hulkWithId();
    private Student newStudent = TestRepository.newStudent();
    private Student newStudentWithId = TestRepository.newStudentWithId();
    private StudentDTO hulkDto = TestRepository.hulkDto();
    private StudentDTO markDto = TestRepository.markDto();
    private StudentDTO newStudentDto = TestRepository.newStudentDto();
    private Group newGroupWithId = TestRepository.newGroupWithId();
    private List<Student> testStudents = TestRepository.getTestStudentsWithId();
    private List<StudentDTO> testStudentDtos = TestRepository.getTestStudentDtos();

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenFindAllThenReturnsAllStudents() {
        List<StudentDTO> expected = testStudentDtos;
        when(studentRepository.findAll()).thenReturn(testStudents);
        List<StudentDTO> actual = studentService.findAll();
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void whenFindByIdThenRightStudentReturned() {
        StudentDTO expected = hulkDto;
        int id = expected.getId();
        when(studentRepository.getOne(id)).thenReturn(hulk);
        StudentDTO actual = studentService.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void whenStudentIdOnCreateIsNotZeroThenThrowWrongParameterExcepti() {
        assertThrows(WrongParameterException.class, () -> studentService.create(markDto));
    }

    @Test
    public void whenCreateTheStudentItsReturnedWithId() {
        StudentDTO expected = newStudentDtoWithId();
        when(groupRepository.getOne(newStudentDtoWithId().getGroupId())).thenReturn(newGroupWithId);
        when(studentRepository.save(newStudent)).thenReturn(newStudentWithId);
        when(studentRepository.getOne(expected.getId())).thenReturn(newStudentWithId);
        StudentDTO actual = studentService.create(newStudentDto);
        assertEquals(expected, actual);
    }

    @Test
    public void whenUpdateTheStudentTheSameReturned() {
        StudentDTO expected = hulkDto;
        int studentId = expected.getId();
        int groupId = expected.getGroupId();
        when(studentRepository.getOne(studentId)).thenReturn(hulk);
        when(groupRepository.getOne(groupId)).thenReturn(superHeroes);
        when(studentRepository.save(hulk)).thenReturn(hulk);
        StudentDTO actual = studentService.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void whenStudentIdOnUpdateIsZeroThenThrowWrongParameterExcepti() {
        assertThrows(WrongParameterException.class, () -> studentService.update(newStudentDto));
    }

    @Test
    public void whenDeleteThenStudentRepositorySaveCalledOnce() {
        int studentId = hulk.getId();
        when(studentRepository.getOne(studentId)).thenReturn(hulk);
        studentService.delete(studentId);
        verify(studentRepository, times(1)).save(hulk);
    }

    @Test
    public void whenRestoreThenStudentRepositorySaveCalledOnce() {
        int studentId = hulk.getId();
        when(studentRepository.getOne(studentId)).thenReturn(hulk);
        studentService.restore(studentId);
        verify(studentRepository, times(1)).save(hulk);
    }
}
