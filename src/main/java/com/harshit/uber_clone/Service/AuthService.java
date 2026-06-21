package com.harshit.uber_clone.Service;

import com.harshit.uber_clone.Dto.LoginRequestDto;
import com.harshit.uber_clone.Dto.LoginResponseDto;
import com.harshit.uber_clone.Entity.User;
import com.harshit.uber_clone.Exception.ResourceNotFoundException;
import com.harshit.uber_clone.Repository.UserRepository;
import com.harshit.uber_clone.Security.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }
    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        User user =userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new ResourceNotFoundException(
                "invalid user or password "
        ));
        boolean matches=passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!matches) {
            throw new ResourceNotFoundException("Invalid Email or Password");
        }
        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDto(token);
    }
}
