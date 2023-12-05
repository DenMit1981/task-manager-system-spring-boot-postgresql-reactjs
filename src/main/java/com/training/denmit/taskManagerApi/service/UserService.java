package com.training.denmit.taskManagerApi.service;

import com.training.denmit.taskManagerApi.dto.user.UserDto;
import com.training.denmit.taskManagerApi.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User save(UserDto userDto);

    Map<Object, Object> authenticateUser(UserDto userDto);

    User getByLogin(String login);

    User getByLoginAndPassword(String login, String password);

    List<String> getAllExecutors();
}
