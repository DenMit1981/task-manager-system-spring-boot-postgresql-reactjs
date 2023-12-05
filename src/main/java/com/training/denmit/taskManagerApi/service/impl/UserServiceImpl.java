package com.training.denmit.taskManagerApi.service.impl;

import com.training.denmit.taskManagerApi.config.security.jwt.JwtTokenProvider;
import com.training.denmit.taskManagerApi.converter.UserConverter;
import com.training.denmit.taskManagerApi.dto.user.UserDto;
import com.training.denmit.taskManagerApi.exception.UserIsPresentException;
import com.training.denmit.taskManagerApi.exception.UserNotFoundException;
import com.training.denmit.taskManagerApi.model.User;
import com.training.denmit.taskManagerApi.model.enums.Role;
import com.training.denmit.taskManagerApi.repository.UserRepository;
import com.training.denmit.taskManagerApi.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

    private static final String USER_IS_PRESENT = "User with login %s is already present";
    private static final String USER_NOT_FOUND = "User with login %s not found";
    private static final String USER_HAS_ANOTHER_PASSWORD = "User with login %s has another password. " +
            "Go to register or enter valid credentials";
    private static final String ADMIN = "admin";

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public User save(UserDto userDto) {
        checkUserBeforeSave(userDto);

        User user = userConverter.fromUserDto(userDto);

        user.setRole(getUserRoleByLoginPassword(user.getLogin(), user.getPassword()));

        userRepository.save(user);

        LOGGER.info("New user : {}", user);

        return user;
    }

    @Override
    @Transactional
    public Map<Object, Object> authenticateUser(UserDto userDto) {
        User user = getByLoginAndPassword(userDto.getLogin(), userDto.getPassword());

        String token = jwtTokenProvider.createToken(user.getLogin(), user.getRole());

        Map<Object, Object> response = new HashMap<>();

        response.put("userName", user.getLogin());
        response.put("role", user.getRole());
        response.put("token", token);

        return response;
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, login)));
    }

    @Override
    @Transactional
    public User getByLoginAndPassword(String login, String password) {
        User user = getByLogin(login);

        if (passwordEncoder.matches(password, user.getPassword())) {

            LOGGER.info("User : {}", user);

            return user;
        }

        LOGGER.error(String.format(USER_HAS_ANOTHER_PASSWORD, login));

        throw new UserNotFoundException(String.format(USER_HAS_ANOTHER_PASSWORD, login));
    }

    @Override
    @Transactional
    public List<String> getAllExecutors() {
        return userRepository.findAllExecutors()
                .stream()
                .map(User::getLogin)
                .sorted()
                .collect(Collectors.toList());

    }

    private boolean isUserPresent(UserDto userDto) {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream().anyMatch(user -> user.getLogin().equals(userDto.getLogin()));
    }

    private void checkUserBeforeSave(UserDto userDto) {
        if (isUserPresent(userDto)) {
            LOGGER.error(String.format(USER_IS_PRESENT, userDto.getLogin()));

            throw new UserIsPresentException(String.format(USER_IS_PRESENT, userDto.getLogin()));
        }
    }

    private Role getUserRoleByLoginPassword(String login, String password) {
        if (!login.equals(ADMIN) && !password.equals(ADMIN)) {
            return Role.ROLE_USER;
        }
        return Role.ROLE_ADMIN;
    }
}

