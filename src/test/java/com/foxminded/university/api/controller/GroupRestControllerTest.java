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

import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.repository.TestRepository;
import com.foxminded.university.service.GroupService;

public class GroupRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    GroupRestController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .build();
    }

    @Test
    public void when_GetGroups_Then_StatusIsOk() throws Exception {
        List<GroupDTO> groupDtos = TestRepository.getTestGroupDtos();
        when(groupService.findAll()).thenReturn(groupDtos);
        this.mockMvc.perform(get("/api/groups/"))
            .andExpect(status().isOk());
    }

    @Test
    public void when_GetDummies_Then_StatusIsOk() throws Exception {
        GroupDTO dummies = TestRepository.dummiesDto();
        int id = dummies.getId();
        when(groupService.findById(id)).thenReturn(dummies);
        this.mockMvc
            .perform(get(String.format("/api/groups/%d", id)))
            .andExpect(status().isOk());
    }

    @Test
    public void when_GetHasWrongParameter_Then_StatusIsBadRequest() throws Exception {
        this.mockMvc
            .perform(get(String.format("/api/groups/%s", "wrongParameter")))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void when_Post_Then_StatusIsOk() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", "Dandies");
        json.put("deleted", false);
        this.mockMvc
            .perform(post("/api/groups").contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
            .andExpect(status().isOk());
    }

    @Test
    public void when_PostHasWrongParameter_Then_StatusIsMethodNotAllowed() throws Exception {
        this.mockMvc
            .perform(post(String.format("/api/groups/%s", "wrongParameter")))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void when_Put_Then_StatusIsOk() throws Exception {
        GroupDTO dummies = TestRepository.dummiesDto();
        int id = dummies.getId();
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", "dudes");
        json.put("deleted", false);
        this.mockMvc
            .perform(put("/api/groups").contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
            .andExpect(status().isOk());
    }

    @Test
    public void when_PutHasWrongParameter_Then_StatusIsMethodNotAllowed() throws Exception {
        this.mockMvc
            .perform(put(String.format("/api/groups/%s", "wrongParameter")))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void when_Delete_Then_StatusIsOk() throws Exception {
        this.mockMvc
            .perform(delete(String.format("/api/groups/%d", TestRepository.dummiesDto().getId())))
            .andExpect(status().isOk());
    }

    @Test
    public void when_DeleteHasWrongParameter_Then_StatusIsMethodNotAllowed() throws Exception {
        this.mockMvc
            .perform(delete(String.format("/api/groups/%s", "wrongParameter")))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void when_Restore_Then_StatusIsOk() throws Exception {
        this.mockMvc
            .perform(get(String.format("/api/groups/%d/restore", TestRepository.dummiesDto().getId())))
            .andExpect(status().isOk());
    }
}
