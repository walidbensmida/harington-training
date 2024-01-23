package org.example.chapitre1.service;

import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.dto.mapper.UserMapper;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;


    @InjectMocks
    private UserService userService;


    @Test
    void whenFindAll_thenRetrieveAllUsers() {

        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");

        List<User> usersMockData = List.of(user1);

        when(userRepository.findAll()).thenReturn(usersMockData);

        when(userMapper.entityToDto(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    return new UserDto(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
                });

        List<UserDto> result = userService.findAll();
        assertAll("Grouped Assertions of findAll Users",
                () -> assertNotNull(result),
                () -> assertEquals(usersMockData.size(), result.size()),
                () -> assertEquals(usersMockData.get(0).hashCode(), result.get(0).hashCode()));
    }

    @Test
    void givenExistingUserById_whenFindById_thenRetrieveUser() throws UserNotFoundException {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(userMapper.entityToDto(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    return new UserDto(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
                });
        UserDto actualUser = userService.findById(1L);
        assertEquals(user1.hashCode(), actualUser.hashCode());
    }

    @Test
    void givenUserDto_whenSave_ThenRetrieveUserSaved() {
        UserDto userDto = new UserDto(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        User user = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.entityToDto(any(User.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return userDto;
                });
        when(userMapper.dtoToEntity(any(UserDto.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return user;
                });
        userService.save(userDto);
    }

    @Test
    void givenNoUserIdExist_whenFindById_thenThrowUserNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.findById(2L));
        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenNoUserIdExist_whenDeleteById_thenThrowUserNotFoundException() {
        Long userIdToDelete = 7000L;
        userRepository.deleteById(userIdToDelete);
        assertThrows(UserNotFoundException.class, () -> userService.deleteById(userIdToDelete));
        verify(userRepository, times(1)).deleteById(userIdToDelete);
    }

    @Test
    void givenUserId_whenDeleteById_thenDeleteUser() throws UserNotFoundException {
        Long userIdToDelete = 101L;
        doReturn(Optional.of(getUser())).when(userRepository).findById(userIdToDelete);
        userService.deleteById(userIdToDelete);
        verify(userRepository, times(1)).deleteById(userIdToDelete);
    }

    private User getUser() {
        return User.builder().id(101L).firstName("firstname").lastName("lastname").role(RoleEnum.CLIENT).email("test@gmail.com").password("test").build();
    }

}