package org.example.chapitre1.service;

import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.dto.UserRequest;
import org.example.chapitre1.dto.mapper.UserMapper;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.UserRepository;
import org.example.chapitre1.repository.user.criteria.IUserJdbcRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Mock
    private UserPredicate userPredicate;

    @Mock
    IUserJdbcRepository iUserJdbcRepository;

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

    @Test
    void should_return_empty_list_when_find_stream_dynamic_users_with_not_existing_users() {
        List<User> usersMockData = List.of(getUser());
        doReturn(usersMockData).when(userRepository).findAll();
        doReturn(buildPredicate(getNotExistingUserRequest())).when(userPredicate).buildPredicate(getNotExistingUserRequest());
        List<UserDto> usersDto = userService.dynamicSearchWithStream(getNotExistingUserRequest());
        assertThat(usersDto).isEmpty();
    }

    @Test
    void should_return_user_when_find_stream_dynamic_users_with_existing_users() {
        List<User> usersMockData = List.of(getUser());
        doReturn(usersMockData).when(userRepository).findAll();
        doReturn(getUserDto()).when(userMapper).entityToDto(getUser());
        doReturn(buildPredicate(getExistingUserRequest())).when(userPredicate).buildPredicate(getExistingUserRequest());
        List<UserDto> usersDto = userService.dynamicSearchWithStream(getExistingUserRequest());
        assertThat(usersDto).isNotEmpty().hasSize(1);
    }

    @Test
    void should_return_empty_list_when_find_jdbc_dynamic_users_with_not_existing_users() {
        doReturn(new ArrayList<User>()).when(iUserJdbcRepository).dynamicSearch(getNotExistingUserRequest());
        List<UserDto> usersDto = userService.dynamicSearchWithJdbc(getNotExistingUserRequest());
        assertThat(usersDto).isEmpty();
    }

    @Test
    void should_return_empty_list_when_find_jdbc_dynamic_users_with_existing_users() {
        List<User> usersMockData = List.of(getUser());
        doReturn(usersMockData).when(iUserJdbcRepository).dynamicSearch(getExistingUserRequest());
        doReturn(getUserDto()).when(userMapper).entityToDto(getUser());
        List<UserDto> usersDto = userService.dynamicSearchWithJdbc(getExistingUserRequest());
        assertThat(usersDto).isNotEmpty().hasSize(1);
    }

    private Predicate<User> buildPredicate(UserRequest userRequest) {
        return user -> user.getFirstName().contains(userRequest.getFirstName())
                && user.getLastName().contains(userRequest.getLastName())
                && user.getEmail().contains(userRequest.getEmail());
    }

    private UserRequest getExistingUserRequest() {
        return UserRequest.builder()
                .firstName("wael")
                .lastName("amara")
                .email("wael.amara@example.com")
                .build();
    }

    private UserRequest getNotExistingUserRequest() {
        return UserRequest.builder()
                .firstName("not_existing")
                .lastName("not_existing")
                .email("not_existing")
                .build();
    }

    private User getUser() {
        return User.builder().id(101L).firstName("wael").lastName("amara").role(RoleEnum.CLIENT).email("wael.amara@example.com").password("test").build();
    }

    private UserDto getUserDto() {
        return UserDto.builder().id(101L).firstName("wael").lastName("amara").role(RoleEnum.CLIENT).email("wael.amara@example.com").password("test").build();
    }

}