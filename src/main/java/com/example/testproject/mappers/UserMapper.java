package com.example.testproject.mappers;

import com.example.testproject.dto.UserDto;
import com.example.testproject.entities.User;

public interface UserMapper {
    UserDto userToDto(User user);
    User dtoToUser(UserDto userDto, String hashedPassword);
}
