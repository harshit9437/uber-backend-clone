package com.harshit.uber_clone.Repository;

import com.harshit.uber_clone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
