package com.training.denmit.taskManagerApi.service.impl;

import com.training.denmit.taskManagerApi.converter.TaskConverter;
import com.training.denmit.taskManagerApi.dto.task.TaskCreationDto;
import com.training.denmit.taskManagerApi.dto.task.TaskEditDto;
import com.training.denmit.taskManagerApi.dto.task.TaskListViewDto;
import com.training.denmit.taskManagerApi.dto.task.TaskViewDto;
import com.training.denmit.taskManagerApi.exception.AccessDeniedException;
import com.training.denmit.taskManagerApi.exception.TaskNotFoundException;
import com.training.denmit.taskManagerApi.model.Task;
import com.training.denmit.taskManagerApi.model.User;
import com.training.denmit.taskManagerApi.model.enums.Role;
import com.training.denmit.taskManagerApi.model.enums.Status;
import com.training.denmit.taskManagerApi.repository.TaskRepository;
import com.training.denmit.taskManagerApi.service.TaskService;
import com.training.denmit.taskManagerApi.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LogManager.getLogger(TaskServiceImpl.class.getName());
    private static final String ACCESS_DENIED_FOR_EXECUTOR = "Access is allowed only for administrator";
    private static final String TASK_NOT_FOUND_BY_ID = "Task with id %s not found";

    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final UserService userService;

    @Override
    @Transactional
    public Task create(TaskCreationDto taskCreationDto, String login) {
        checkUserAccess(login);

        Task task = taskConverter.fromTaskCreationDto(taskCreationDto);

        task.setAdmin(userService.getByLogin(login));
        task.setExecutor(userService.getByLogin(taskCreationDto.getExecutor()));
        task.setCreationDate(LocalDate.now());
        task.setLastModifiedDate(LocalDate.now());
        task.setStatus(Status.NEW);

        taskRepository.save(task);

        LOGGER.info("New task : {}", task);

        return task;
    }

    @Override
    @Transactional
    public TaskViewDto getById(Long id) {
        return taskConverter.convertToTaskViewDto(findById(id));
    }

    @Override
    @Transactional
    public List<TaskListViewDto> getAll(String login, String sortField, String sortDirection, int pageSize, int pageNumber) {
        List<Task> tasks;

        User user = userService.getByLogin(login);

        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            tasks = getAll(sortField, sortDirection, pageSize, pageNumber);
        } else {
            tasks = getAllByUserLogin(login, sortField, sortDirection, pageSize, pageNumber);
        }

        LOGGER.info("All tasks: {}", tasks);

        return tasks.stream()
                .map(taskConverter::convertToTaskListViewDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Task update(Long taskId, TaskEditDto taskEditDto, String login) {
        checkUserAccess(login);
        Task task = findById(taskId);
        Task updatedTask = taskConverter.fromTaskEditDto(taskEditDto);

        updatedTask.setId(task.getId());
        updatedTask.setLastModifiedDate(LocalDate.now());
        updatedTask.setAdmin(task.getAdmin());
        updatedTask.setExecutor(task.getExecutor());
        updatedTask.setCreationDate(task.getCreationDate());

        taskRepository.save(updatedTask);

        LOGGER.info("Updated task : {}", task);

        return updatedTask;
    }

    @Override
    @Transactional
    public void deleteById(Long id, String login) {
        checkUserAccess(login);
        taskRepository.deleteById(id);

        LOGGER.info("Tasks after removing task with id = {} : {}", id, taskRepository.findAll());
    }

    @Override
    @Transactional
    public long getTotalAmount(String login) {
        User user = userService.getByLogin(login);

        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            return taskRepository.count();
        }

        return taskRepository.findAllByExecutorLogin(login).size();
    }

    private List<Task> getAll(String sortField, String sortDirection, int pageSize, int pageNumber) {
        if (sortField.equals("executor")) {
            return getAllSortedByUser(sortDirection, pageSize, pageNumber);
        }

        return taskRepository.findAll(PageRequest.of(pageNumber - 1, pageSize,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField)));
    }

    private List<Task> getAllByUserLogin(String login, String sortField, String sortDirection, int pageSize, int pageNumber) {
        if (sortField.equals("executor")) {
            return getAllByExecutorLoginSortedByUser(login, sortDirection, pageSize, pageNumber);
        }

        return taskRepository.findAllByExecutorLogin(login, PageRequest.of(pageNumber - 1, pageSize,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField)));
    }

    private List<Task> getAllSortedByUser(String sortDirection, int pageSize, int pageNumber) {
        if (sortDirection.equals("desc")) {
            return taskRepository.findAllDescendSortedByUserLoginAndTaskId(PageRequest.of(pageNumber - 1, pageSize));
        }

        return taskRepository.findAllSortedByUserLoginAndTaskId(PageRequest.of(pageNumber - 1, pageSize));
    }

    private List<Task> getAllByExecutorLoginSortedByUser(String login, String sortDirection, int pageSize, int pageNumber) {
        if (sortDirection.equals("desc")) {
            return taskRepository.findAllByExecutorLoginDescendSortedByUserLoginAndTaskId(login,
                    PageRequest.of(pageNumber - 1, pageSize));
        }

        return taskRepository.findAllByExecutorLoginSortedByUserLoginAndTaskId(login, PageRequest.of(pageNumber - 1, pageSize));
    }

    private Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND_BY_ID, id)));
    }

    private void checkUserAccess(String login) {
        if (userService.getByLogin(login).getRole().equals(Role.ROLE_USER)) {
            LOGGER.error(ACCESS_DENIED_FOR_EXECUTOR);

            throw new AccessDeniedException(ACCESS_DENIED_FOR_EXECUTOR);
        }
    }
}
