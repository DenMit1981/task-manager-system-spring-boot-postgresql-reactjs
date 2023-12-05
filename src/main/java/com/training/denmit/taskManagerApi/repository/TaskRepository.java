package com.training.denmit.taskManagerApi.repository;

import com.training.denmit.taskManagerApi.model.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAll(Pageable pageable);

    List<Task> findAllByExecutorLogin(String login);

    @Query("from Task t where t.executor.login =:login and t.executor.role like 'ROLE_USER'")
    List<Task> findAllByExecutorLogin(@Param("login") String login, Pageable pageable);

    @Query("from Task t order by t.executor.login, t.id")
    List<Task> findAllSortedByUserLoginAndTaskId(Pageable pageable);

    @Query("from Task t order by t.executor.login desc, t.id")
    List<Task> findAllDescendSortedByUserLoginAndTaskId(Pageable pageable);

    @Query("from Task t where t.executor.login =:login and t.executor.role like 'ROLE_USER' order by t.executor.login, t.id")
    List<Task> findAllByExecutorLoginSortedByUserLoginAndTaskId(@Param("login") String login, Pageable pageable);

    @Query("from Task t where t.executor.login =:login and t.executor.role like 'ROLE_USER' order by t.executor.login desc, t.id")
    List<Task> findAllByExecutorLoginDescendSortedByUserLoginAndTaskId(@Param("login") String login, Pageable pageable);

}
