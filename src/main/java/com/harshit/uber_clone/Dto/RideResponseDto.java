package com.harshit.uber_clone.Dto;

import com.harshit.uber_clone.Entity.RideStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
    public class RideResponseDto {

        private Long id;

        private String pickupLocation;

        private String dropLocation;

        private double fare;

        private RideStatus status;

        private Long userId;

        private Long driverId;
    }

