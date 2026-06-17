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
        Driver driver=driverRepository.findById(rideDto.getDriverId()).orElseThrow(()->new ResourceNotFoundException(
                "driver not existed with id "+rideDto.getDriverId()
        ));
        Ride ride=new Ride();
        ride.setPickupLocation(rideDto.getPickupLocation());
        ride.setDropLocation(rideDto.getDropLocation());
        ride.setUser(user);
        ride.setDriver(driver);
        ride.setFare(0.0);
        ride.setStatus(ride.getStatus());
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

        Ride saveRide = rideRepository.save(ride);

        return convertToDto(saveRide);
    }
    public RideResponseDto startRide(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not existed with id "+id
        ));
        if(ride.getStatus() != RideStatus.ACCEPTED){
            throw new IllegalArgumentException(
                    "Only Accepted rides can be start"
            );

        }
        ride.setStatus(RideStatus.STARTED);
        Ride saveRide=rideRepository.save(ride);
        return convertToDto(saveRide);
    }
    public RideResponseDto rideComplete(Long id){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not existed with id "+id
        ));
        if(ride.getStatus()!=RideStatus.STARTED){ throw new IllegalArgumentException(
                "ride cannot complete which is not started");
        }
        ride.setStatus(RideStatus.COMPLETED);
        Ride saveride=rideRepository.save(ride);
        return convertToDto(saveride);
    }
    public RideResponseDto cancelRide(Long id ){
        Ride ride=rideRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "ride does not existed with id "+id
        ));
        if(ride.getStatus()==RideStatus.COMPLETED){
            throw new IllegalArgumentException("cannot cancelled the ride once ride is completed");
        }
        ride.setStatus(RideStatus.CANCELLED);
        Ride saveride=rideRepository.save(ride);
        return convertToDto(saveride);
    }
}
