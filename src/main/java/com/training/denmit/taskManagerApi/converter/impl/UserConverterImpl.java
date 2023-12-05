package com.training.denmit.taskManagerApi.converter.impl;

import com.training.denmit.taskManagerApi.converter.UserConverter;
import com.training.denmit.taskManagerApi.dto.user.UserDto;
import com.training.denmit.taskManagerApi.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConverterImpl implements UserConverter {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User fromUserDto(UserDto userDto) {
        User user = new User();

        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }
}
