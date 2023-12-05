package com.training.denmit.taskManagerApi.repository;

import com.training.denmit.taskManagerApi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);

    @Query("from User u where u.role like 'ROLE_USER'")
    List<User> findAllExecutors();
}