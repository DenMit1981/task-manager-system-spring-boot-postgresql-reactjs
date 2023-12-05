package com.training.denmit.taskManagerApi.security.service;

import com.training.denmit.taskManagerApi.exception.UserNotFoundException;
import com.training.denmit.taskManagerApi.model.User;
import com.training.denmit.taskManagerApi.repository.UserRepository;
import com.training.denmit.taskManagerApi.security.model.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String USER_NOT_FOUND = "User with login %s not found";

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format(USER_NOT_FOUND, login));
        }

        return new CustomUserDetails(user.get());
    }
}

