package com.training.denmit.taskManagerApi.service;

import com.training.denmit.taskManagerApi.dto.task.TaskCreationDto;
import com.training.denmit.taskManagerApi.dto.task.TaskEditDto;
import com.training.denmit.taskManagerApi.dto.task.TaskListViewDto;
import com.training.denmit.taskManagerApi.dto.task.TaskViewDto;
import com.training.denmit.taskManagerApi.model.Task;

import java.util.List;

public interface TaskService {

    Task create(TaskCreationDto taskCreationDto, String login);

    TaskViewDto getById(Long id);

    List<TaskListViewDto> getAll(String login, String sortField, String sortDirection, int pageSize, int pageNumber);

    Task update(Long taskId, TaskEditDto taskEditDto, String login);

    void deleteById(Long id, String login);

    long getTotalAmount(String login);
}
