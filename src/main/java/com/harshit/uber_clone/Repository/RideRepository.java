package com.harshit.uber_clone.Repository;

import com.harshit.uber_clone.Entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository
        extends JpaRepository<Ride,Long> {

}
