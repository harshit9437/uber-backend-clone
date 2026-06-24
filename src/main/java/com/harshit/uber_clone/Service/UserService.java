package com.harshit.uber_clone.Service;

import com.harshit.uber_clone.Dto.UserDto;
import com.harshit.uber_clone.Entity.User;
import com.harshit.uber_clone.Exception.ResourceNotFoundException;
import com.harshit.uber_clone.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper=modelMapper;

    }
    //creating user
    public User createUser(UserDto userDto){
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhoneNo());
        user.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );;
        return userRepository.save(user);
    }
    //getting user
    public User getuser(Long id){
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "user not found by this id"+id
        ));
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public void deleteUser(long id){
        User user =userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "user not found with id"+id
        ));
        userRepository.delete(user);
    }
    public User updateUser(Long id,UserDto userDto){
        User user= userRepository.findById(id).orElse(null);
        if(user==null){
            return null;
        }
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }
    public UserDto getUserByEmail(String email) {

        System.out.println("SEARCHING USER = " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        System.out.println("FOUND USER = " + user.getEmail());

        return modelMapper.map(user, UserDto.class);
    }

}
