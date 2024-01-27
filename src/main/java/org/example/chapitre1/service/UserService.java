package org.example.chapitre1.service;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.FilterType;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.dto.mapper.UserMapper;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.UnsupportedFilterTypeException;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
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

    public List<UserDto> findAll(Specification specification) {
        List<User> users = userRepository.findAll(specification);
        return users.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    public List<UserDto> findAllByFilter(FilterType filter, String value) {
        UserFilter userFilter = getUserFilter(filter, value);
        List<User> findedUsers = userRepository.findAll()
                .stream()
                .filter(userFilter::isExist)
                .toList();

        return findedUsers.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    private UserFilter getUserFilter(FilterType filter, String value) {
        return switch (filter) {
            case Mail -> filterByMail(value);
            case Role -> filterByRole(value);
            case Firstname -> filterByFirstname(value);
            case Lastname -> filterByLastname(value);
            default -> throw new RuntimeException(new UnsupportedFilterTypeException());
        };
    }

    private UserFilter filterByRole(String role) {
        return user -> user.getRole() == RoleEnum.valueOf(role);
    }

    private UserFilter filterByFirstname(String firstname) {
        return user -> Objects.equals(user.getFirstName(), firstname);
    }

    private UserFilter filterByLastname(String lastname) {
        return user -> Objects.equals(user.getLastName(), lastname);
    }

    private UserFilter filterByMail(String mail) {
        return user -> Objects.equals(user.getEmail(), mail);
    }

}
