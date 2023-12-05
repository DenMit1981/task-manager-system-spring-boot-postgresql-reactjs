package com.training.denmit.taskManagerApi.converter;

import com.training.denmit.taskManagerApi.dto.task.TaskCreationDto;
import com.training.denmit.taskManagerApi.dto.task.TaskEditDto;
import com.training.denmit.taskManagerApi.dto.task.TaskListViewDto;
import com.training.denmit.taskManagerApi.dto.task.TaskViewDto;
import com.training.denmit.taskManagerApi.model.Task;

public interface TaskConverter {

    Task fromTaskCreationDto(TaskCreationDto taskCreationDto);

    Task fromTaskEditDto(TaskEditDto taskEditDto);

    TaskViewDto convertToTaskViewDto(Task task);

    TaskListViewDto convertToTaskListViewDto(Task task);
}
