package com.example.dome.application.repository;


import com.example.dome.application.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {


    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User set password=?2 where id=?1")
    void updatePassword(long id, String password);
}
