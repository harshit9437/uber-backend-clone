package com.harshit.uber_clone.Repository;

import com.harshit.uber_clone.Entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository
        extends JpaRepository<Driver,Long> {

}
