package org.example.chapitre1.dto.mapper;

import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User dtoToEntity(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }

    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
