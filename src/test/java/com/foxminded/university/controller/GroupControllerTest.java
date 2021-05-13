package com.foxminded.university.controller;

import static com.foxminded.university.controller.AttributeNames.ERROR_ATTR;
import static com.foxminded.university.controller.AttributeNames.SUCCESS_ATTR;
import static com.foxminded.university.controller.GroupController.GROUP_DELETE_ENFE_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_DELETE_SUCC_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_DETAIL_ENFE_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_EDIT_CREATE_SUCC_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_EDIT_ENFE_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_EDIT_POST_ENFE_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_EDIT_UPDATE_SUCC_MSG;
import static com.foxminded.university.controller.GroupController.GROUP_RESTORE_ENFE_MSG;
import static com.foxminded.university.repository.TestRepository.dummiesDto;
import static com.foxminded.university.repository.TestRepository.geniusesDto;
import static com.foxminded.university.repository.TestRepository.newGroupDto;
import static com.foxminded.university.repository.TestRepository.newGroupDtoWithId;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityNotFoundException;
import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.repository.TestRepository;
import com.foxminded.university.service.GroupService;

public class GroupControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
            .standaloneSetup(groupController)
            .setViewResolvers(viewResolver)
            .build();
    }

    @Test
    public void when_GroupList_Then_StatusIsOk_NameIsRight_GroupsExistAndCorrect() throws Exception {
        List<GroupDTO> groupDtos = TestRepository.getTestGroupDtos();

        when(groupService.findAll()).thenReturn(groupDtos);
        this.mockMvc.perform(get("/groupList"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("group/groupList"))
            .andExpect(model().attributeExists("groups"))
            .andExpect(
                model().attribute("groups", containsInAnyOrder(geniusesDto(), dummiesDto())));
    }

    @Test
    public void when_GroupDetail_Then_StatusIsOk_NameIsRight_GroupExistsAndCorrect() throws Exception {
        int id = dummiesDto().getId();

        when(groupService.findById(id)).thenReturn(dummiesDto());
        this.mockMvc.perform(get("/groupDetail").param("id", String.valueOf(id)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("group/groupDetail"))
            .andExpect(model().attributeExists("group"))
            .andExpect(model().attribute("group", dummiesDto()));
    }

    @Test
    public void when_GroupDetailEntityNotFoundException_Then_ErrorMessageExistsAndCorrect() throws Exception {
        int id = dummiesDto().getId();
        String msg = String.format(GROUP_DETAIL_ENFE_MSG, id);

        when(groupService.findById(id)).thenThrow(EntityNotFoundException.class);
        this.mockMvc.perform(get("/groupDetail").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(model().attributeExists(ERROR_ATTR))
            .andExpect(
                model().attribute(ERROR_ATTR, msg));
    }

    @Test
    public void when_GroupEditGet_Then_StatusIsOk_NameIsRight_GroupExistsAndCorrect() throws Exception {
        int id = dummiesDto().getId();

        when(groupService.findById(id)).thenReturn(dummiesDto());
        this.mockMvc.perform(get("/groupEdit").param("id", String.valueOf(id)))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(view().name("group/groupEdit"))
            .andExpect(model().attributeExists("group"))
            .andExpect(model().attribute("group", dummiesDto()));
    }

    @Test
    public void when_GroupEditGet_EntityNotFoundException_Then_ErrorMessageExistsAndCorrect() throws Exception {
        int id = dummiesDto().getId();
        String msg = String.format(GROUP_EDIT_ENFE_MSG, id);

        when(groupService.findById(id)).thenThrow(EntityNotFoundException.class);
        this.mockMvc.perform(get("/groupEdit").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(model().attributeExists(ERROR_ATTR))
            .andExpect(
                model().attribute(ERROR_ATTR, msg));
    }

    @Test
    public void when_GroupEditPostCreateSuccess_Then_CheckStatusAndUrlAndAttributes() throws Exception {
        GroupDTO groupDto = newGroupDto();
        int id = newGroupDtoWithId().getId();

        when(groupService.create(newGroupDto())).thenReturn(newGroupDtoWithId());
        String msg = String.format(GROUP_EDIT_CREATE_SUCC_MSG, newGroupDtoWithId().getName(), id);
        String url = String.format("groupList");

        this.mockMvc.perform(post("/groupEdit").flashAttr("group", groupDto))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl(url))
            .andDo(print())
            .andExpect(flash().attribute(SUCCESS_ATTR, msg));
    }

    @Test
    public void when_GroupEditPostCreateEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        when(groupService.create(newGroupDto())).thenThrow(EntityNotFoundException.class);
        String msg = String.format(GROUP_EDIT_POST_ENFE_MSG, newGroupDto().getId());

        this.mockMvc.perform(post("/groupEdit").flashAttr("group", newGroupDto()))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }

    @Test
    public void when_GroupEditPostUpdateSuccess_Then_CheckStatusAndUrlAndAttributes() throws Exception {
        GroupDTO groupDto = dummiesDto();
        int id = dummiesDto().getId();

        when(groupService.update(dummiesDto())).thenReturn(dummiesDto());
        String msg = String.format(GROUP_EDIT_UPDATE_SUCC_MSG, dummiesDto().getName(), id);
        String url = String.format("groupDetail?id=%d", id);

        this.mockMvc.perform(post("/groupEdit").flashAttr("group", groupDto))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl(url))
            .andDo(print())
            .andExpect(flash().attribute(SUCCESS_ATTR, msg))
            .andExpect(model().attribute("id", String.valueOf(id)));
    }

    @Test
    public void when_GroupEditPostUpdateEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        int id = dummiesDto().getId();

        when(groupService.update(dummiesDto())).thenThrow(EntityNotFoundException.class);
        String msg = String.format(GROUP_EDIT_POST_ENFE_MSG, id);
        this.mockMvc.perform(post("/groupEdit").flashAttr("group", dummiesDto()))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }

    @Test
    public void when_GroupDeleteSuccess_Then_CheckStatusAndUrlAndAttributes() throws Exception {
        int id = dummiesDto().getId();

        String msg = String.format(GROUP_DELETE_SUCC_MSG, id);

        this.mockMvc.perform(post("/groupDelete").param("id", String.valueOf(id)))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("groupList"))
            .andDo(print())
            .andExpect(flash().attribute(SUCCESS_ATTR, msg));
    }

    @Test
    public void when_GroupDeleteEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        int id = dummiesDto().getId();
        String msg = String.format(GROUP_DELETE_ENFE_MSG, id);

        doThrow(EntityNotFoundException.class).when(groupService).delete(id);
        this.mockMvc.perform(post("/groupDelete").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }

    @Test
    public void when_GroupRestoreSuccess_Then_CheckStatusAndUrl() throws Exception {
        int id = dummiesDto().getId();
        String url = String.format("groupDetail?id=%d", id);

        this.mockMvc.perform(post("/groupRestore").param("id", String.valueOf(id)))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl(url))
            .andDo(print());
    }

    @Test
    public void when_GroupRestoreEntityNotFoundException_Then_ErrorMessageCorrect() throws Exception {
        int id = dummiesDto().getId();
        String msg = String.format(GROUP_RESTORE_ENFE_MSG, id);

        doThrow(EntityNotFoundException.class).when(groupService).restore(id);
        this.mockMvc.perform(post("/groupRestore").param("id", String.valueOf(id)))
            .andDo(print())
            .andExpect(flash().attribute("errorMessage", msg));
    }
}