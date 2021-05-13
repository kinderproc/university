package com.foxminded.university.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.repository.TestRepository;
import com.foxminded.university.service.GroupService;
import com.foxminded.university.service.StudentService;
import com.foxminded.university.validation.validator.GroupCapacityValidator;

public class StudentRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private GroupService groupService;

    @Mock
    private GroupCapacityValidator groupCapacityValidator;

    @InjectMocks
    StudentRestController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setValidator(groupCapacityValidator)
            .build();
    }

    @Test
    public void when_GetStudents_Then_StatusIsOk() throws Exception {
        List<StudentDTO> studentDtos = TestRepository.getTestStudentDtos();
        when(studentService.findAll()).thenReturn(studentDtos);
        this.mockMvc.perform(get("/api/students/"))
            .andExpect(status().isOk());
    }

    @Test
    public void when_GetHulk_Then_StatusIsOk() throws Exception {
        StudentDTO hulk = TestRepository.hulkDto();
        int id = hulk.getId();
        when(studentService.findById(id)).thenReturn(hulk);
        this.mockMvc
            .perform(get(String.format("/api/students/%d", id)))
            .andExpect(status().isOk());
    }

    @Test
    public void when_GetHasWrongParameter_Then_StatusIsBadRequest() throws Exception {
        this.mockMvc
            .perform(get(String.format("/api/students/%s", "wrongParameter")))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void when_Post_Then_StatusIsOk() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", "Sergey");
        json.put("surname", "Durnev");
        json.put("age", "35");
        json.put("phone", "+79119999999");
        json.put("groupId", 1);
        json.put("groupName", "The Beatles");
        json.put("deleted", false);
        this.mockMvc
            .perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
            .andExpect(status().isOk());
    }

    @Test
    public void when_PostHasWrongParameter_Then_StatusIsMethodNotAllowed() throws Exception {
        this.mockMvc
            .perform(post(String.format("/api/students/%s", "wrongParameter")))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void when_Put_Then_StatusIsOk() throws Exception {
        StudentDTO hulk = TestRepository.hulkDto();
        int id = hulk.getId();
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", "Sergey1");
        json.put("surname", "Durnev1");
        json.put("age", "36");
        json.put("phone", "+79119999998");
        json.put("groupId", 1);
        json.put("groupName", "The Beatles");
        json.put("deleted", false);

        this.mockMvc
            .perform(put("/api/students").contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
            .andExpect(status().isOk());
    }

    @Test
    public void when_PutHasWrongParameter_Then_StatusIsMethodNotAllowed() throws Exception {
        this.mockMvc
            .perform(put(String.format("/api/students/%s", "wrongParameter")))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void when_Delete_Then_StatusIsOk() throws Exception {
        this.mockMvc
            .perform(delete(String.format("/api/students/%d", TestRepository.hulkDto().getId())))
            .andExpect(status().isOk());
    }

    @Test
    public void when_DeleteHasWrongParameter_Then_StatusIsMethodNotAllowed() throws Exception {
        this.mockMvc
            .perform(delete(String.format("/api/students/%s", "wrongParameter")))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void when_Restore_Then_StatusIsOk() throws Exception {
        this.mockMvc
            .perform(get(String.format("/api/students/%d/restore", TestRepository.hulkDto().getId())))
            .andExpect(status().isOk());
    }
}
