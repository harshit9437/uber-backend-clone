package com.harshit.uber_clone.Repository;

import com.harshit.uber_clone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User,Long> {

}
