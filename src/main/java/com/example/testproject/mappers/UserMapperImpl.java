package com.example.testproject.mappers;

import com.example.testproject.dto.UserDto;
import com.example.testproject.entities.User;
import org.springframework.stereotype.Component;

/**
 * Class provides mapping between user dto and user entity. We can use MapStruct or
 * it's analogues, but it's a simple project, and we can implement simple mapping.
 */
@Component
public class UserMapperImpl implements UserMapper {

    public UserDto userToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreationDate()
        );
    }

    public User dtoToUser(UserDto userDto, String hashedPassword) {
        var user = new User();
        user.setEmail(userDto.email());
        user.setName(userDto.name());
        user.setPassword(hashedPassword);
        return user;
    }
}
