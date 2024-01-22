package org.example.chapitre1.service;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.dto.mapper.UserMapper;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    public UserDto findById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.entityToDto(user);
    }

    public void deleteById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(user.getId());
    }

    public UserDto save(UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        return userMapper.entityToDto(userRepository.save(user));
    }

}
