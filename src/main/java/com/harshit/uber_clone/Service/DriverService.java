package com.harshit.uber_clone.Service;

import com.harshit.uber_clone.Dto.DriveDto;
import com.harshit.uber_clone.Entity.Driver;
import com.harshit.uber_clone.Exception.ResourceNotFoundException;
import com.harshit.uber_clone.Repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
    public Driver createDriver(DriveDto driverDto){
        Driver driver=new Driver();
        driver.setName(driverDto.getName());
        driver.setEmail(driverDto.getEmail());
        return driverRepository.save(driver);
    }

    public Driver getDriverById(Long id){
        return driverRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                "driver doesn't exist with id "+id
        ));
    }
    public List<Driver> getAllDriver(){
        return driverRepository.findAll();
    }
    public void deleteDriver(Long id){
        Driver driver=driverRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "driver doesn't exist with id "+id
        ));
        driverRepository.delete(driver);
    }
    public Driver updateDriver(Long id,DriveDto driveDto){
        Driver driver=new Driver();
        driver.setName(driveDto.getName());
        driver.setEmail(driveDto.getEmail());
        return driverRepository.save(driver);
    }


}
