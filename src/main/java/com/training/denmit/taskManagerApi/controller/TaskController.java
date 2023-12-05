package com.training.denmit.taskManagerApi.controller;

import com.training.denmit.taskManagerApi.dto.task.TaskCreationDto;
import com.training.denmit.taskManagerApi.dto.task.TaskEditDto;
import com.training.denmit.taskManagerApi.dto.task.TaskListViewDto;
import com.training.denmit.taskManagerApi.dto.task.TaskViewDto;
import com.training.denmit.taskManagerApi.model.Task;
import com.training.denmit.taskManagerApi.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RequestMapping(value = "/tasks")
@Api("Task controller")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ApiOperation(value = "Create a new task", authorizations = @Authorization(value = "Bearer"))
    public ResponseEntity<?> save(@RequestBody @Valid TaskCreationDto taskCreationDto, Principal principal) {
        Task savedTask = taskService.create(taskCreationDto, principal.getName());

        String currentUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        String savedTaskLocation = currentUri + "/" + savedTask.getId();

        return ResponseEntity.status(CREATED)
                .header(HttpHeaders.LOCATION, savedTaskLocation)
                .body(savedTask);
    }

    @GetMapping
    @ApiOperation(value = "Get all tasks", authorizations = @Authorization(value = "Bearer"))
    public ResponseEntity<List<TaskListViewDto>> getAll(Principal principal,
                                                        @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                                        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                        @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        List<TaskListViewDto> tasks = taskService.getAll(principal.getName(), sortField, sortDirection, pageSize, pageNumber);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    @ApiOperation(value = "Get task by ID", authorizations = @Authorization(value = "Bearer"))
    public ResponseEntity<TaskViewDto> getById(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.getById(taskId));
    }

    @PutMapping("/{taskId}")
    @ApiOperation(value = "update task by ID", authorizations = @Authorization(value = "Bearer"))
    public ResponseEntity<?> update(Principal principal, @PathVariable("taskId") Long taskId,
                                    @RequestBody @Valid TaskEditDto taskEditDto) {
        Task updatedTask = taskService.update(taskId, taskEditDto, principal.getName());

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    @ApiOperation(value = "Delete task by ID", authorizations = @Authorization(value = "Bearer"))
    public ResponseEntity<Void> delete(@PathVariable("taskId") Long taskId, Principal principal) {
        taskService.deleteById(taskId, principal.getName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/total")
    @ApiOperation(value = "Get total number of tasks for users by login", authorizations = @Authorization(value = "Bearer"))
    public ResponseEntity<?> getTotalAmount(Principal principal) {
        return ResponseEntity.ok(taskService.getTotalAmount(principal.getName()));
    }
}
