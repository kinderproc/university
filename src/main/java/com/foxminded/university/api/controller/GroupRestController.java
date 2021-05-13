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

import com.foxminded.university.dto.GroupDTO;
import com.foxminded.university.exception.WrongParameterException;
import com.foxminded.university.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupRestController {

    private GroupService groupService;

    private Logger logger = LogManager.getLogger(GroupRestController.class.getName());

    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDTO> getGroups() {
        return groupService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable("id") int id) {
        ResponseEntity<GroupDTO> response = new ResponseEntity<>(HttpStatus.OK);

        try {
            response = new ResponseEntity<>(groupService.findById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            logger.warn(e.getMessage());
        }

        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public GroupDTO createGroup(@RequestBody @Valid GroupDTO group) {
        return groupService.create(group);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GroupDTO updateGroup(@RequestBody @Valid GroupDTO group) {
        return groupService.update(group);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable(name = "id") int id) {
        groupService.delete(id);
    }

    @GetMapping(value = "/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public void restoreGroup(@PathVariable(name = "id") int id) {
        groupService.restore(id);
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
