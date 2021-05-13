package com.foxminded.university.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.university.dto.StudentDTO;
import com.foxminded.university.exception.WrongParameterException;
import com.foxminded.university.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private StudentService studentService;

    private Logger logger = LogManager.getLogger(StudentRestController.class.getName());

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") int id) {
        ResponseEntity<StudentDTO> response = new ResponseEntity<>(HttpStatus.OK);

        try {
            response = new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            logger.warn(e.getMessage());
        }

        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO createStudent(@RequestBody @Valid StudentDTO student) {
        return studentService.create(student);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@RequestBody @Valid StudentDTO student) {
        return studentService.update(student);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable(name = "id") int id) {
        studentService.delete(id);
    }

    @GetMapping(value = "/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public void restoreStudent(@PathVariable(name = "id") int id) {
        studentService.restore(id);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler({ WrongParameterException.class })
    public ResponseEntity<Object> handleWrongParameterException(WrongParameterException e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
