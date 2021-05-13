package com.foxminded.university.controller;

import static com.foxminded.university.controller.AttributeNames.ERROR_ATTR;
import static com.foxminded.university.controller.AttributeNames.SUCCESS_ATTR;
import static com.foxminded.university.controller.StudentController.STUDENT_DELETE_ENFE_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_DELETE_SUCC_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_DETAIL_ENFE_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_EDIT_CREATE_SUCC_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_EDIT_ENFE_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_EDIT_POST_ENFE_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_EDIT_UPDATE_SUCC_MSG;
import static com.foxminded.university.controller.StudentController.STUDENT_RESTORE_ENFE_MSG;
import static com.foxminded.university.repository.TestRepository.geniusesDto;
import static com.foxminded.university.repository.TestRepository.lorenzoDto;
import static com.foxminded.university.repository.TestRepository.markDto;
import static com.foxminded.university.repository.TestRepository.michaelDto;
import static com.foxminded.university.repository.TestRepository.newGroupDtoWithId;
import static com.foxminded.university.repository.TestRepository.newStudentDto;
import static com.foxminded.university.repository.TestRepository.newStudentDtoWithId;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityNotFoundException;
import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.repository.TestRepository;
import com.foxminded.university.service.GroupService;
import com.foxminded.university.service.StudentService;

public class StudentControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private GroupService groupService;

    @Mock
    private StudentService studentService;

    @Mock
    private Environment env;

    @Mock
    private Validator validator;

    @BeforeEach
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
            .standaloneSetup(studentController)
            .setViewResolvers(viewResolver)
            .setValidator(validator)
            .build();
    }

    @Test
    public void when_StudentList_Then_StatusIsOk_NameIsRight_StudentsExistAndCorrect() throws Exception {
        List<StudentDTO> studentDtos = TestRepository.getTestStudentDtos();

        when(studentService.findAll()).thenReturn(studentDtos);
        this.mockMvc.perform(get("/studentList"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("student/studentList"))
            .andExpect(model().attributeExists("students"))
            .andExpect(model().attribute("students", containsInAnyOrder(markDto(), michaelDto(), lorenzoDto())));
    }

    @Test
    public void when_StudentDetail_Then_StatusIsOk_NameIsRight_StudentExistsAndCorrect() throws Exception {
        int id = markDto().getId();

        when(studentService.findById(id)).thenReturn(markDto());
        this.mockMvc.perform(get("/studentDetail").param("id", String.valueOf(id)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("student/studentDetail"))
            .andExpect(model().attributeExists("student"))
            .andExpect(model().attribute("student", markDto()));
    }

    @Test
    public void when_StudentDetailEntityNotFoundException_Then_ErrorMessageExistsAndCorrect() throws Exception {
        int id = markDto().getId();
        String msg = String.format(STUDENT_DETAIL_ENFE_MSG, id);

        when(studentService.findById(id)).thenThrow(EntityNotFoundException.class);
        this.mockMvc.perform(get("/studentDetail").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(model().attributeExists(ERROR_ATTR))
            .andExpect(
                model().attribute(ERROR_ATTR, msg));
    }

    @Test
    public void when_StudentEditGet_Then_StatusIsOk_NameIsRight_StudentExistsAndCorrect() throws Exception {
        int id = markDto().getId();
        List<GroupDTO> groupDtos = TestRepository.getTestGroupDtos();
        GroupDTO emptyGroup = new GroupDTO();
        emptyGroup.setId(0);
        emptyGroup.setName(null);
        groupDtos.add(0, emptyGroup);

        when(studentService.findById(id)).thenReturn(markDto());
        when(groupService.findAllForStudent(id)).thenReturn(groupDtos);
        this.mockMvc.perform(get("/studentEdit").param("id", String.valueOf(id)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("student/studentEdit"))
            .andExpect(model().attributeExists("student"))
            .andExpect(model().attribute("student", markDto()))
            .andExpect(model().attributeExists("groups"))
            .andExpect(model().attribute("groups", groupDtos));
    }

    @Test
    public void when_StudentEditGet_EntityNotFoundException_Then_ErrorMessageExistsAndCorrect() throws Exception {
        int id = markDto().getId();
        String msg = String.format(STUDENT_EDIT_ENFE_MSG, id);

        when(studentService.findById(id)).thenThrow(EntityNotFoundException.class);
        this.mockMvc.perform(get("/studentEdit").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(model().attributeExists(ERROR_ATTR))
            .andExpect(
                model().attribute(ERROR_ATTR, msg));
    }

    @Test
    public void when_StudentEditPostCreateSuccess_Then_CheckStatusAndUrlAndAttributes() throws Exception {
        int studentId = newStudentDtoWithId().getId();
        int groupId = newGroupDtoWithId().getId();
        String msg = String.format(STUDENT_EDIT_CREATE_SUCC_MSG, newStudentDtoWithId().getSurname(), studentId);
        String url = String.format("studentList", studentId);

        when(groupService.findById(groupId)).thenReturn(newGroupDtoWithId());
        when(groupService.getNumberOfStudents(groupId)).thenReturn(1L);
        when(env.getProperty("maxNumberOfStudents")).thenReturn("999");
        when(studentService.create(newStudentDto())).thenReturn(newStudentDtoWithId());

        this.mockMvc.perform(post("/studentEdit").flashAttr("student", newStudentDto()))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl(url))
            .andDo(print())
            .andExpect(flash().attribute(SUCCESS_ATTR, msg));
    }

    @Test
    public void when_StudentEditPostCreateEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        StudentDTO studentDto = newStudentDto();
        int studentId = newStudentDto().getId();
        int groupId = newGroupDtoWithId().getId();

        when(groupService.findById(newGroupDtoWithId().getId())).thenReturn(newGroupDtoWithId());
        when(groupService.getNumberOfStudents(groupId)).thenReturn(1L);
        when(env.getProperty("maxNumberOfStudents")).thenReturn("999");
        when(studentService.create(newStudentDto())).thenThrow(EntityNotFoundException.class);

        String msg = String.format(STUDENT_EDIT_POST_ENFE_MSG, studentId);
        this.mockMvc.perform(post("/studentEdit").flashAttr("student", studentDto))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }

    @Test
    public void when_StudentEditPostUpdateSuccess_Then_CheckStatusAndUrlAndAttributes() throws Exception {
        int id = markDto().getId();

        when(studentService.update(markDto())).thenReturn(markDto());
        String msg = String.format(STUDENT_EDIT_UPDATE_SUCC_MSG, markDto().getSurname(), id);
        String url = String.format("studentDetail?id=%d", id);

        this.mockMvc.perform(post("/studentEdit").flashAttr("student", markDto()))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl(url))
            .andDo(print())
            .andExpect(flash().attribute(SUCCESS_ATTR, msg))
            .andExpect(model().attribute("id", String.valueOf(id)));
    }

    @Test
    public void when_StudentEditPostUpdateEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        int id = markDto().getId();
        when(studentService.update(markDto())).thenThrow(EntityNotFoundException.class);
        when(groupService.findById(markDto().getGroupId())).thenReturn(geniusesDto());
        String msg = String.format(STUDENT_EDIT_POST_ENFE_MSG, id);
        this.mockMvc.perform(post("/studentEdit").flashAttr("student", markDto()))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }

    @Test
    public void when_StudentDeleteSuccess_Then_CheckStatusAndUrlAndAttributes() throws Exception {
        int id = markDto().getId();

        String msg = String.format(STUDENT_DELETE_SUCC_MSG, id);

        this.mockMvc.perform(post("/studentDelete").param("id", String.valueOf(id)))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("studentList"))
            .andDo(print())
            .andExpect(flash().attribute(SUCCESS_ATTR, msg));
    }

    @Test
    public void when_StudentDeleteEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        int id = markDto().getId();
        String msg = String.format(STUDENT_DELETE_ENFE_MSG, id);

        doThrow(EntityNotFoundException.class).when(studentService).delete(id);
        this.mockMvc.perform(post("/studentDelete").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }

    @Test
    public void when_StudentRestoreSuccess_Then_CheckStatusAndUrl() throws Exception {
        int studentId = markDto().getId();
        int groupId = markDto().getGroupId();
        String url = String.format("studentDetail?id=%d", studentId);

        when(studentService.findById(studentId)).thenReturn(markDto());
        when(groupService.findById(groupId)).thenReturn(geniusesDto());
        when(env.getProperty("maxNumberOfStudents")).thenReturn("999");
        this.mockMvc.perform(post("/studentRestore").param("id", String.valueOf(studentId)))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl(url))
            .andDo(print());
    }

    @Test
    public void when_StudentRestoreEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        int studentId = markDto().getId();
        int groupId = markDto().getGroupId();
        String msg = String.format(STUDENT_RESTORE_ENFE_MSG, studentId);

        when(studentService.findById(studentId)).thenReturn(markDto());
        when(groupService.findById(groupId)).thenReturn(geniusesDto());
        when(env.getProperty("maxNumberOfStudents")).thenReturn("999");
        doThrow(EntityNotFoundException.class).when(studentService).restore(studentId);
        this.mockMvc.perform(post("/studentRestore").param("id", String.valueOf(studentId)))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }
}
