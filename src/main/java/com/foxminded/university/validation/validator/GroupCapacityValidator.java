package com.foxminded.university.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.service.GroupService;
import com.foxminded.university.service.StudentService;
import com.foxminded.university.validation.annotation.GroupCapacityConstraint;

@Component
public class GroupCapacityValidator implements ConstraintValidator<GroupCapacityConstraint, StudentDTO>, Validator {
    @Autowired
    public StudentService studentService;
    @Autowired
    public GroupService groupService;

    @Value("${maxNumberOfStudents}")
    private Integer limit;

    private String template;

    public GroupCapacityValidator() {
    }

    @Override
    public void initialize(GroupCapacityConstraint groupCapacityConstraint) {
        this.template = groupCapacityConstraint.template();
    }

    @Override
    public boolean isValid(StudentDTO studentDTO, ConstraintValidatorContext context) {
        boolean result = validate(studentDTO);

        if (!result) {
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(template)
                .addPropertyNode("groupId")
                .addConstraintViolation();
        }

        return result;
    }

    private boolean validate(StudentDTO studentDTO) {
        boolean result = true;
        int groupId = studentDTO.getGroupId();
        int studentId = studentDTO.getId();
        Long count = groupService.getNumberOfStudents(groupId);

        if (count >= limit) {
            boolean isInGroup = studentService.isInGroup(studentId, groupId);
            if (!isInGroup) {
                result = false;
            }
        }

        return result;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StudentDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentDTO studentDTO = (StudentDTO) target;
        boolean result = validate(studentDTO);

        if (!result) {
            errors.reject("Group is full!");
        }
    }
}
