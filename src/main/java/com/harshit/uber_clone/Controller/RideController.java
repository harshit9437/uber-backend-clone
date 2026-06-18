package com.harshit.uber_clone.Controller;

import com.harshit.uber_clone.Dto.RideDto;
import com.harshit.uber_clone.Dto.RideResponseDto;
import com.harshit.uber_clone.Service.RideService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{ride}")
public class RideController {
private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }
    @PostMapping
    public RideResponseDto createRide(@Valid@RequestBody RideDto rideDto){
        return rideService.createRide(rideDto);

    }
    @GetMapping("/{id}")
    public RideResponseDto getRideById(@PathVariable Long id){
        return rideService.getRide(id);
    }
    @GetMapping
    public List<RideResponseDto> getAllRides(){
        return rideService.getAllRides();
    }
    @DeleteMapping("/{id}")
    public void deleteRideById(@PathVariable Long id){
        rideService.deleteRideById(id);
    }
    @PutMapping("/{id}/accept")
    public RideResponseDto acceptStatus(@PathVariable Long id){
        return rideService.acceptStatus(id);
    }
    @PutMapping("/{id}/start")
    public RideResponseDto startRide(@PathVariable Long id){
        return rideService.startStatus(id);
    }
    @PutMapping("/{id}/complete")
    public RideResponseDto completeRide(@PathVariable Long id){
        return rideService.completeStatus(id);
    }
    @PutMapping("/{id}/cancel")
    public RideResponseDto cancelRide(@PathVariable Long id){
        return rideService.cancelStatus(id);
    }
}
