package com.training.denmit.taskManagerApi.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    private static final String FIELD_IS_EMPTY = "Fields shouldn't be empty";
    private static final String WRONG_SIZE_OF_NAME_OR_PASSWORD = "Login or password shouldn't be less than 4 symbols";

    @NotBlank(message = FIELD_IS_EMPTY)
    @Size(min = 4, message = WRONG_SIZE_OF_NAME_OR_PASSWORD)
    private String login;

    @NotBlank(message = FIELD_IS_EMPTY)
    @Size(min = 4, message = WRONG_SIZE_OF_NAME_OR_PASSWORD)
    private String password;
}
