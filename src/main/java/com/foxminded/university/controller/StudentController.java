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

import com.foxminded.university.dao.exception.EntityWasNotCreatedException;
import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.service.GroupService;
import com.foxminded.university.service.StudentService;

@Controller
public class StudentController {
    public static final String STUDENT_LIST_NEQE_MSG = "Can't receive the student list! Please, try again.";
    public static final String STUDENT_DETAIL_ENFE_MSG = "Can't get the details for student with id '%d'! Please, try again.";
    public static final String STUDENT_DETAIL_NEQE_MSG = "Error occured when trying to get the details for student with id '%d'! Please, try again.";
    public static final String STUDENT_EDIT_ENFE_MSG = "Can't find for edit any student with id '%d'! Please, try again.";
    public static final String STUDENT_EDIT_NEQE_MSG = "Error occured when trying to edit the student with id '%d'! Please, try again.";
    public static final String STUDENT_EDIT_CREATE_GROUP_FULL_MSG = "Student wasn't created. Group '%s' is full!";
    public static final String STUDENT_EDIT_RESTORE_GROUP_FULL_MSG = "Student wasn't restored. Group '%s' is full!";
    public static final String STUDENT_EDIT_CREATE_SUCC_MSG = "Student '%s' with id '%d' has been created!";
    public static final String STUDENT_EDIT_UPDATE_SUCC_MSG = "Student '%s' with id '%d' has been updated!";
    public static final String STUDENT_EDIT_POST_ENFE_MSG = "Can't find student with id '%d' during saving details! Please, try again.";
    public static final String STUDENT_EDIT_POST_NEQE_MSG = "Error occured when trying to save details for the student with id '%d'! Please, try again.";
    public static final String STUDENT_DELETE_SUCC_MSG = "Student with id '%d' has been deleted!";
    public static final String STUDENT_DELETE_ENFE_MSG = "Can't find student with id '%d' during deletion! Please, try again.";
    public static final String STUDENT_DELETE_NEQE_MSG = "Error occured when trying to delete the student with id '%d'! Please, try again.";
    public static final String STUDENT_RESTORE_ENFE_MSG = "Can't find student with id '%d' during restoration! Please, try again.";
    public static final String STUDENT_RESTORE_NEQE_MSG = "Error occured when trying to restore the student with id '%d'! Please, try again.";

    private Logger logger = LogManager.getLogger(StudentController.class.getName());
    private StudentService studentService;
    private GroupService groupService;

    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @RequestMapping("/studentList")
    public ModelAndView studentList() {
        logger.debug("> studentList()");
        ModelAndView mv = new ModelAndView();
        List<StudentDTO> students = studentService.findAll();
        mv.addObject("students", students);
        mv.setViewName("student/studentList");
        return mv;
    }

    @RequestMapping("/studentDetail")
    public ModelAndView studentDetail(@RequestParam int id) {
        logger.debug("> student(id={})", id);

        ModelAndView mv = new ModelAndView();

        try {
            StudentDTO student = studentService.findById(id);
            mv.addObject("student", student);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            String msg = String.format(STUDENT_DETAIL_ENFE_MSG, id);
            mv.addObject(ERROR_ATTR, msg);
        }

        mv.setViewName("student/studentDetail");
        return mv;
    }

    @RequestMapping(value = "/studentEdit", method = RequestMethod.GET)
    public ModelAndView studentEditGet(@RequestParam int id) {
        logger.debug("> studentEditGet(id={})", id);

        ModelAndView mv = new ModelAndView();
        StudentDTO student = new StudentDTO();
        List<GroupDTO> groups = groupService.findAllForStudent(id);
        GroupDTO emptyGroup = new GroupDTO();
        emptyGroup.setId(0);
        emptyGroup.setName(null);
        groups.add(0, emptyGroup);

        try {
            if (id > 0) {
                student = studentService.findById(id);
            }
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            String msg = String.format(STUDENT_EDIT_ENFE_MSG, id);
            mv.addObject(ERROR_ATTR, msg);
        }

        mv.addObject("student", student);
        mv.addObject("groups", groups);
        mv.setViewName("student/studentEdit");
        return mv;
    }

    @RequestMapping(value = "/studentEdit", method = RequestMethod.POST)
    public String studentEditPost(@ModelAttribute("student") @Valid StudentDTO student, BindingResult bindingResult,
        Model model, RedirectAttributes attributes) {
        String msg = EMPTY;
        String viewName = "student/studentEdit";
        int studentId = student.getId();

        if (bindingResult.hasErrors()) {
            model.addAttribute("groups", groupService.findAllForStudent(studentId));
            return viewName;
        }

        try {
            if (studentId == 0) {
                StudentDTO createdStudent = studentService.create(student);
                studentId = createdStudent.getId();
                viewName = "studentList";
                msg = String.format(STUDENT_EDIT_CREATE_SUCC_MSG, createdStudent.getSurname(),
                    createdStudent.getId());
            } else {
                StudentDTO updatedStudent = studentService.update(student);
                viewName = "studentDetail";
                attributes.addAttribute("id", studentId);
                msg = String.format(STUDENT_EDIT_UPDATE_SUCC_MSG, updatedStudent.getSurname(), updatedStudent.getId());
            }

            attributes.addFlashAttribute(SUCCESS_ATTR, msg);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            viewName = "studentList";
            msg = String.format(STUDENT_EDIT_POST_ENFE_MSG, studentId);
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        } catch (EntityWasNotCreatedException e) {
            logger.warn(e.getMessage());
            viewName = "studentList";
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        }

        return String.format("redirect:%s", viewName);
    }

    @RequestMapping("/studentDelete")
    public RedirectView studentDelete(RedirectAttributes attributes, @RequestParam int id) {
        logger.debug("> studentDelete(id={})", id);
        String msg = EMPTY;

        try {
            studentService.delete(id);
            msg = String.format(STUDENT_DELETE_SUCC_MSG, id);
            attributes.addFlashAttribute(SUCCESS_ATTR, msg);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            msg = String.format(STUDENT_DELETE_ENFE_MSG, id);
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        }

        return new RedirectView("studentList");
    }

    @RequestMapping("/studentRestore")
    public RedirectView studentRestore(RedirectAttributes attributes, @RequestParam int id) {
        logger.debug("> studentRestore(id={})", id);
        String viewName = "studentDetail";
        String msg = EMPTY;

        try {
            studentService.restore(id);
        } catch (EntityNotFoundException e) {
            logger.warn(e.getMessage());
            viewName = "studentList";
            msg = String.format(STUDENT_RESTORE_ENFE_MSG, id);
            attributes.addFlashAttribute(ERROR_ATTR, msg);
        }

        attributes.addAttribute("id", id);
        return new RedirectView(viewName);
    }
}
