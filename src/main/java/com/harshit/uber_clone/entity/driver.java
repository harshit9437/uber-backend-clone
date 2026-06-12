package com.harshit.uber_clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="driver")
@NoArgsConstructor
@AllArgsConstructor
public class driver {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String vehicleNo;
    private String phoneNO;
    private double rating;

}
