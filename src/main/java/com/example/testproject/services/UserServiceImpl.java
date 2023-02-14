package com.example.testproject.services;

import com.example.testproject.dto.UserDto;
import com.example.testproject.entities.User;
import com.example.testproject.exceptions.DataAlreadyExist;
import com.example.testproject.exceptions.DataNotFound;
import com.example.testproject.mappers.UserMapper;
import com.example.testproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.existsByName(userDto.name())) {
            throw new DataAlreadyExist("User with name '%s'".formatted(userDto.name()));
        }
        var encodedPassword = passwordEncoder.encode(userDto.password());
        var user = userMapper.dtoToUser(userDto, encodedPassword);
        return userMapper.userToDto(userRepository.save(user));
    }
}
