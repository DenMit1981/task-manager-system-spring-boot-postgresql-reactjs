package com.training.denmit.taskManagerApi.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.denmit.taskManagerApi.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskViewDto {

    private Long id;

    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastModifiedDate;

    private Status status;

    private String executor;

    private String admin;
}
