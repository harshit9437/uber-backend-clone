package com.harshit.uber_clone.Service;

import com.harshit.uber_clone.Dto.RideDto;
import com.harshit.uber_clone.Dto.RideResponseDto;
import com.harshit.uber_clone.Entity.Driver;
import com.harshit.uber_clone.Entity.Ride;
import com.harshit.uber_clone.Entity.RideStatus;
import com.harshit.uber_clone.Entity.User;
import com.harshit.uber_clone.Exception.ResourceNotFoundException;
import com.harshit.uber_clone.Repository.DriverRepository;
import com.harshit.uber_clone.Repository.RideRepository;
import com.harshit.uber_clone.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public RideService(
            RideRepository rideRepository,
            UserRepository userRepository,
            DriverRepository driverRepository) {

        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
    }
    public RideResponseDto createRide(RideDto rideDto){
        User user=userRepository.findById(rideDto.getUserId()).orElseThrow(()->new ResourceNotFoundException(
                "user not existed with id"+rideDto.getUserId()
        ));
        Driver driver=driverRepository.findById(rideDto.getDriverId()).orElseThrow(()->new ResourceNotFoundException(
                "driver not existed with id "+rideDto.getDriverId()
        ));
        Ride ride=new Ride();
        ride.setPickupLocation(rideDto.getPickupLocation());
        ride.setDropLocation(rideDto.getDropLocation());
        ride.setUser(user);
        ride.setDriver(driver);
        ride.setFare(ride.getFare());
        ride.setStatus(RideStatus.REQUESTED);
        Ride saveride= rideRepository.save(ride);
        RideResponseDto rideResponseDto=new RideResponseDto();
        rideResponseDto.setId(saveride.getId());
        rideResponseDto.setPickupLocation(saveride.getPickupLocation());
        rideResponseDto.setDropLocation(saveride.getDropLocation());
        rideResponseDto.setFare(0.0);
        rideResponseDto.setStatus(saveride.getStatus());
        rideResponseDto.setUserId(saveride.getUser().getId());
        rideResponseDto.setDriverId(saveride.getDriver().getId());
        return rideResponseDto;
    }
    public RideResponseDto getRide(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride doesn't exist with id"+id

        ));
        RideResponseDto response = new RideResponseDto();
        response.setId(ride.getId());
        response.setPickupLocation(ride.getPickupLocation());
        response.setDropLocation(ride.getDropLocation());

        response.setFare(ride.getFare());
        response.setStatus(RideStatus.REQUESTED);

        response.setUserId(ride.getUser().getId());
        response.setDriverId(ride.getDriver().getId());
        return response;

    }
    @GetMapping
    public List<RideResponseDto> getAllRides() {

        List<Ride> rides = rideRepository.findAll();

        List<RideResponseDto> response = new ArrayList<>();

        for (Ride ride : rides) {

            RideResponseDto dto = new RideResponseDto();

            dto.setId(ride.getId());
            dto.setPickupLocation(ride.getPickupLocation());
            dto.setDropLocation(ride.getDropLocation());
            dto.setFare(ride.getFare());
            dto.setStatus(ride.getStatus());

            dto.setUserId(ride.getUser().getId());
            dto.setDriverId(ride.getDriver().getId());

            response.add(dto);
        }

        return response;
    }
    public void deleteRideById(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not exist with this id "+id
        ));
        rideRepository.delete(ride);
    }
    public RideResponseDto acceptStatus(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not exist with this id "+id
        ));
        ride.setStatus(RideStatus.ACCEPTED);
        Ride saveRide=rideRepository.save(ride);
        RideResponseDto rideResponseDto=new RideResponseDto();
        rideResponseDto.setStatus(RideStatus.ACCEPTED);
        return rideResponseDto;
    }
}
