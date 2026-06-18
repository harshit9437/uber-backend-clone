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
    private RideResponseDto convertToDto(Ride ride){

        RideResponseDto dto = new RideResponseDto();

        dto.setId(ride.getId());
        dto.setPickupLocation(ride.getPickupLocation());
        dto.setDropLocation(ride.getDropLocation());
        dto.setFare(ride.getFare());
        dto.setStatus(ride.getStatus());
        dto.setUserId(ride.getUser().getId());
        dto.setDriverId(ride.getDriver().getId());

        return dto;
    }
    public RideResponseDto createRide(RideDto rideDto){
        User user=userRepository.findById(rideDto.getUserId()).orElseThrow(()->new ResourceNotFoundException(
                "user not existed with id"+rideDto.getUserId()
        ));
        List<Driver> drivers = driverRepository.findByAvailableTrue();

        if(drivers.isEmpty()){
            throw new IllegalArgumentException(
                    "No drivers available"
            );
        }

        Driver driver = drivers.get(0);
        double fare=calculateFare(rideDto.getDistance());

        Ride ride=new Ride();
        ride.setPickupLocation(rideDto.getPickupLocation());
        ride.setDropLocation(rideDto.getDropLocation());
        ride.setUser(user);
        ride.setDriver(driver);
        ride.setFare(fare);
        ride.setStatus(RideStatus.REQUESTED);
        Ride saveride= rideRepository.save(ride);

        return convertToDto(saveride);
    }
    public RideResponseDto getRide(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride doesn't exist with id"+id

        ));

        return convertToDto(ride);

    }

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

        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ride does not exist with this id " + id
                ));

        if(ride.getStatus() != RideStatus.REQUESTED){
            throw new IllegalArgumentException(
                    "Only requested rides can be accepted"
            );


        }

        ride.setStatus(RideStatus.ACCEPTED);
        Driver driver=ride.getDriver();
        driver.setAvailable(false);
        driverRepository.save(driver);
        Ride saveRide = rideRepository.save(ride);

        return convertToDto(saveRide);
    }
    public RideResponseDto startStatus(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not existed with id "+id
        ));
        if(ride.getStatus() != RideStatus.ACCEPTED){
            throw new IllegalArgumentException(
                    "Only Accepted rides can be start"
            );

        }
        Driver driver=ride.getDriver();
        driver.setAvailable(false);
        driverRepository.save(driver);
        ride.setStatus(RideStatus.STARTED);
        Ride saveRide=rideRepository.save(ride);
        return convertToDto(saveRide);
    }
    public RideResponseDto completeStatus(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not existed with id "+id
        ));
        if(ride.getStatus()!=RideStatus.STARTED){ throw new IllegalArgumentException(
                "ride cannot complete which is not started");
        }
        ride.setStatus(RideStatus.COMPLETED);
        Driver driver=ride.getDriver();
        driver.setAvailable(true);
        driverRepository.save(driver);
        Ride saveride=rideRepository.save(ride);
        return convertToDto(saveride);
    }
    public RideResponseDto cancelStatus(Long id ){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not existed with id "+id
        ));
        if(ride.getStatus()==RideStatus.COMPLETED){
            throw new IllegalArgumentException("cannot cancelled the ride once ride is completed");
        }
        ride.setStatus(RideStatus.CANCELLED);
        Driver driver=ride.getDriver();
        driver.setAvailable(true);
        driverRepository.save(driver);
        Ride saveride=rideRepository.save(ride);
        return convertToDto(saveride);
    }
    public double calculateFare(double distance){
        double baseFare=50;
        double ridePerKm=12;
        return baseFare+(ridePerKm*distance);

    }
}
