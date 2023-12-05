package com.training.denmit.taskManagerApi.converter.impl;

import com.training.denmit.taskManagerApi.converter.TaskConverter;
import com.training.denmit.taskManagerApi.dto.task.TaskCreationDto;
import com.training.denmit.taskManagerApi.dto.task.TaskEditDto;
import com.training.denmit.taskManagerApi.dto.task.TaskListViewDto;
import com.training.denmit.taskManagerApi.dto.task.TaskViewDto;
import com.training.denmit.taskManagerApi.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskConverterImpl implements TaskConverter {

    @Override
    public Task fromTaskCreationDto(TaskCreationDto taskCreationDto) {
        Task task = new Task();

        task.setTitle(taskCreationDto.getTitle());
        task.setDescription(taskCreationDto.getDescription());

        return task;
    }

    @Override
    public Task fromTaskEditDto(TaskEditDto taskEditDto) {
        Task task = new Task();

        task.setTitle(taskEditDto.getTitle());
        task.setDescription(taskEditDto.getDescription());
        task.setStatus(taskEditDto.getStatus());

        return task;
    }

    @Override
    public TaskViewDto convertToTaskViewDto(Task task) {
        TaskViewDto taskViewDto = new TaskViewDto();

        taskViewDto.setId(task.getId());
        taskViewDto.setTitle(task.getTitle());
        taskViewDto.setDescription(task.getDescription());
        taskViewDto.setCreationDate(task.getCreationDate());
        taskViewDto.setLastModifiedDate(task.getLastModifiedDate());
        taskViewDto.setStatus(task.getStatus());
        taskViewDto.setExecutor(task.getExecutor().getLogin());
        taskViewDto.setAdmin(task.getAdmin().getLogin());

        return taskViewDto;
    }

    @Override
    public TaskListViewDto convertToTaskListViewDto(Task task) {
        TaskListViewDto taskListViewDto = new TaskListViewDto();

        taskListViewDto.setId(task.getId());
        taskListViewDto.setTitle(task.getTitle());
        taskListViewDto.setExecutor(task.getExecutor().getLogin());
        taskListViewDto.setCreationDate(task.getCreationDate());
        taskListViewDto.setLastModifiedDate(task.getLastModifiedDate());
        taskListViewDto.setStatus(task.getStatus());

        return taskListViewDto;
    }
}
