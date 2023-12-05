package com.training.denmit.taskManagerApi.converter;

import com.training.denmit.taskManagerApi.dto.user.UserDto;
import com.training.denmit.taskManagerApi.model.User;

public interface UserConverter {

    User fromUserDto(UserDto userDto);
}
