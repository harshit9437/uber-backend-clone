package com.harshit.uber_clone.Controller;

import com.harshit.uber_clone.Dto.DriveDto;
import com.harshit.uber_clone.Entity.Driver;
import com.harshit.uber_clone.Service.DriverService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    //Creating new  user
    @PostMapping
    public Driver createDriver(@Valid @RequestBody DriveDto driveDto){
        return  driverService.createDriver(driveDto);
    }
    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable Long id){
        return driverService.getDriverById(id);
    }
    @GetMapping
    public List<Driver> getAllDrivers(){
        return driverService.getAllDriver();
    }
    @PutMapping("/{id}")
    public Driver updateDriver(@PathVariable Long id,@RequestBody DriveDto driveDto ){
        return driverService.updateDriver(id,driveDto);
    }
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
    }
}
