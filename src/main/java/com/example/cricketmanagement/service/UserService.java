package com.example.cricketmanagement.service;

import com.example.cricketmanagement.dto.UserDTO;
import com.example.cricketmanagement.model.Users;
import com.example.cricketmanagement.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO userToUserDTO(Users user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        return userDTO;
    }

    public UserDTO createUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userToUserDTO(userRepository.save(user));
    }

}
