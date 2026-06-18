package com.harshit.uber_clone.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class DriveDto {
    @NotBlank(message = "empty name is not allowed")
    private String name;
    @Email(message = "email is invalid")
    @NotBlank(message = "empty email is not allowed")
    private String email;
    @NotBlank(message = "phone no is required")
    private String phoneNo;
    @NotBlank(message = "vehicle no is required")
    private String vehicleNo;


}
