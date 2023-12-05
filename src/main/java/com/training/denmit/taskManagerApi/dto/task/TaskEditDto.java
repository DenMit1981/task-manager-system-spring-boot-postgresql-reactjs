package com.training.denmit.taskManagerApi.dto.task;

import com.training.denmit.taskManagerApi.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TaskEditDto {

    private static final String TITLE_FIELD_IS_EMPTY = "Title field shouldn't be empty";
    private static final String DESCRIPTION_FIELD_IS_EMPTY = "Description field shouldn't be empty";
    private static final String WRONG_SIZE_OF_TITLE = "Title shouldn't be more than 15 symbols";
    private static final String WRONG_SIZE_OF_DESCRIPTION = "Description shouldn't be more than 100 symbols";

    @NotEmpty(message = TITLE_FIELD_IS_EMPTY)
    @Size(max = 15, message = WRONG_SIZE_OF_TITLE)
    private String title;

    @NotEmpty(message = DESCRIPTION_FIELD_IS_EMPTY)
    @Size(max = 100, message = WRONG_SIZE_OF_DESCRIPTION)
    private String description;

    private Status status;
}
