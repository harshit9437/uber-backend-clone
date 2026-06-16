package com.harshit.uber_clone.Dto;

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
public class RideDto {
@NotBlank(message = "pickup location cannot be empty")
private String pickupLocation;
@NotBlank(message = "drop location cannot be empty")
private String dropLocation;
@NotNull(message = "required userId")
private Long userId;
@NotNull(message = "required driverId")
private Long driverId;
}
