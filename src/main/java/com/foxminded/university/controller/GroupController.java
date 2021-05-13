package com.foxminded.university.controller;

import static com.foxminded.university.controller.AttributeNames.ERROR_ATTR;
import static com.foxminded.university.controller.AttributeNames.SUCCESS_ATTR;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.service.GroupService;

@Controller
public class GroupController {
    public static final String GROUP_LIST_NEQE_MSG = "Can't receive the group list! Please, try again.";
    public static final String GROUP_DETAIL_ENFE_MSG = "Can't get the details for group with id '%d'! Please, try again.";
    public static final String GROUP_DETAIL_NEQE_MSG = "Error occured when trying to get the details for group with id '%d'! Please, try again.";
    public static final String GROUP_EDIT_ENFE_MSG = "Can't find for edit any group with id '%d'! Please, try again.";
    public static final String GROUP_EDIT_NEQE_MSG = "Error occured when trying to edit the group with id '%d'! Please, try again.";
    public static final String GROUP_EDIT_CREATE_SUCC_MSG = "Group '%s' with id '%d' has been created!";
    public static final String GROUP_EDIT_UPDATE_SUCC_MSG = "Group '%s' with id '%d' has been updated!";
    public static final String GROUP_EDIT_POST_ENFE_MSG = "Can't find group with id '%d' during saving details! Please, try again.";
    public static final String GROUP_EDIT_POST_NEQE_MSG = "Error occured when trying to save details for the group with id '%d'! Please, try again.";
    public static final String GROUP_DELETE_SUCC_MSG = "Group with id '%d' has been deleted!";
    public static final String GROUP_DELETE_ENFE_MSG = "Can't find group with id '%d' during deletion! Please, try again.";
    public static final String GROUP_DELETE_NEQE_MSG = "Error occured when trying to delete the group with id '%d'! Please, try again.";
    public static final String GROUP_RESTORE_ENFE_MSG = "Can't find group with id '%d' during restoration! Please, try again.";
    public static final String GROUP_RESTORE_NEQE_MSG = "Error occured when trying to restore the group with id '%d'! Please, try again.";

    private Logger logger = LogManager.getLogger(GroupController.class.getName());
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping("/groupList")
    public ModelAndView groupList() {
        logger.debug("> groupList()");
        ModelAndView mv = new ModelAndView();
        List<GroupDTO> groups = groupService.findAll();
        mv.addObject("groups", groups);
        mv.setViewName("group/groupList");
        return mv;
    }

    @RequestMapping("/groupDetail")
    public ModelAndView groupDetail(@RequestParam int id) {
        logger.debug("> groupDetail(id={})", id);
        ModelAndView mv = new ModelAndView();

        try {
            GroupDTO group = groupService.findById(id);
            mv.addObject("group", group);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            String msg = String.format(GROUP_DETAIL_ENFE_MSG, id);
            mv.addObject(ERROR_ATTR, msg);
        }

        mv.setViewName("group/groupDetail");
        return mv;
    }

    @RequestMapping(value = "/groupEdit", method = RequestMethod.GET)
    public ModelAndView groupEditGet(@RequestParam int id) {
        logger.debug("> groupEditGet(id={})", id);
        ModelAndView mv = new ModelAndView();
        GroupDTO group = new GroupDTO();

        try {
            if (id > 0) {
                group = groupService.findById(id);
            }
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            String msg = String.format(GROUP_EDIT_ENFE_MSG, id);
            mv.addObject(ERROR_ATTR, msg);
        }

        mv.addObject("group", group);
        mv.setViewName("group/groupEdit");
        return mv;
    }

    @RequestMapping(value = "/groupEdit", method = RequestMethod.POST)
    public String groupEditPost(@ModelAttribute("group") @Valid GroupDTO group, BindingResult bindingResult,
        Model model, RedirectAttributes attributes) {
        logger.debug("> groupEditPost()");
        String msg = EMPTY;
        String viewName = "group/groupEdit";
        int groupId = group.getId();

        if (bindingResult.hasErrors()) {
            return viewName;
        }

        try {
            if (groupId == 0) {
                GroupDTO createdGroup = groupService.create(group);
                groupId = createdGroup.getId();
                viewName = "groupList";
                msg = String.format(GROUP_EDIT_CREATE_SUCC_MSG, createdGroup.getName(), createdGroup.getId());
            } else {
                GroupDTO updatedGroup = groupService.update(group);
                viewName = "groupDetail";
                attributes.addAttribute("id", groupId);
                msg = String.format(GROUP_EDIT_UPDATE_SUCC_MSG, updatedGroup.getName(), groupId);
            }

            attributes.addFlashAttribute(SUCCESS_ATTR, msg);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            viewName = "groupList";
            msg = String.format(GROUP_EDIT_POST_ENFE_MSG, groupId);
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        }

        return String.format("redirect:%s", viewName);
    }

    @RequestMapping("/groupDelete")
    public RedirectView groupDelete(RedirectAttributes attributes, @RequestParam int id) {
        logger.debug("> groupDelete(id={})", id);

        try {
            groupService.delete(id);
            String msg = String.format(GROUP_DELETE_SUCC_MSG, id);
            attributes.addFlashAttribute(SUCCESS_ATTR, msg);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            String msg = String.format(GROUP_DELETE_ENFE_MSG, id);
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        }

        return new RedirectView("groupList");
    }

    @RequestMapping("/groupRestore")
    public RedirectView groupRestore(RedirectAttributes attributes, @RequestParam int id) {
        logger.debug("> groupRestore(id={})", id);
        String redirectView = "groupDetail";

        try {
            groupService.restore(id);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            redirectView = "groupList";
            String msg = String.format(GROUP_RESTORE_ENFE_MSG, id);
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        }

        attributes.addAttribute("id", id);
        return new RedirectView(redirectView);
    }
}
