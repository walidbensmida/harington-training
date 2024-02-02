package org.example.chapitre1.service;

import org.example.chapitre1.dto.*;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
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

    @Test
    void givenRequestFilterDtoListAndOperationType_whenFindAllByFilterAndGlobalOperationIsAnd_thenFindUsers() throws UserNotFoundException {
        FilterType filterFirstname = FilterType.FIRSTNAME;
        FilterType filterLastname = FilterType.LASTNAME;

        RequestFilterDto filterDtoFirstname = RequestFilterDto.builder().filter(filterFirstname).value("firstname").build();
        RequestFilterDto filterDtoLastname = RequestFilterDto.builder().filter(filterLastname).value("lastname").build();
        RequestSpecificationDto.GlobalOperator or = RequestSpecificationDto.GlobalOperator.AND;

        List<RequestFilterDto> requestFilterDtoList = Arrays.asList(filterDtoFirstname, filterDtoLastname);
        User user = getUser();
        UserDto userDto = getUserDto(user);
        doReturn(getUsers()).when(userRepository).findAll();
        doReturn(userDto).when(userMapper).entityToDto(any());
        List<UserDto> usersFinded = userService.findAllByFilter(requestFilterDtoList, RequestSpecificationDto.GlobalOperator.AND);
        assertAll("Grouped Assertions of findAllByFilter Users with global operator is AND",
                () -> assertEquals(usersFinded.size(), 1),
                () -> assertEquals(usersFinded.get(0).getFirstName(), "firstname"),
                () -> assertEquals(usersFinded.get(0).getLastName(), "lastname"),
                () -> assertEquals(usersFinded.get(0).getRole(), RoleEnum.CLIENT),
                () -> assertEquals(usersFinded.get(0).getEmail(), "test@gmail.com"));
    }

    @Test
    void givenRequestFilterDtoListAndOperationType_whenFindAllByFilterAndGlobalOperationIsOr_thenFindUsers() throws UserNotFoundException {
        FilterType filterFirstname = FilterType.FIRSTNAME;
        FilterType filterLastname = FilterType.LASTNAME;

        RequestFilterDto filterDtoFirstname = RequestFilterDto.builder().filter(filterFirstname).value("firstname").build();
        RequestFilterDto filterDtoLastname = RequestFilterDto.builder().filter(filterLastname).value("lastname2").build();
        List<RequestFilterDto> requestFilterDtoList = Arrays.asList(filterDtoFirstname, filterDtoLastname);
        List<User> users = getUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        UserDto userDto1= getUserDto(user1);
        UserDto userDto2= getUserDto(user2);
        doReturn(getUsers()).when(userRepository).findAll();
        doReturn(userDto1).when(userMapper).entityToDto(user1);
        doReturn(userDto2).when(userMapper).entityToDto(user2);
        List<UserDto> usersFinded = userService.findAllByFilter(requestFilterDtoList, RequestSpecificationDto.GlobalOperator.OR);
        assertAll("Grouped Assertions of findAllByFilter Users with global operator is AND",
                () -> assertEquals(usersFinded.size(), 2),
                () -> assertEquals(usersFinded.get(0).getFirstName(), "firstname"),
                () -> assertEquals(usersFinded.get(0).getLastName(), "lastname"),
                () -> assertEquals(usersFinded.get(0).getRole(), RoleEnum.CLIENT),
                () -> assertEquals(usersFinded.get(0).getEmail(), "test@gmail.com"),
                () -> assertEquals(usersFinded.get(1).getFirstName(), "firstname1"),
                () -> assertEquals(usersFinded.get(1).getLastName(), "lastname2"),
                () -> assertEquals(usersFinded.get(1).getRole(), RoleEnum.CLIENT),
                () -> assertEquals(usersFinded.get(1).getEmail(), "test@gmail.com"));
    }

    @Test
    void givenRequestSpecificationDto_whenFindAllBySpecificationOperationIsOr_thenFindUsers() throws UserNotFoundException {
        SearchRequestDto searchRequestDto1 = new SearchRequestDto("firstName","firstnametest");
        SearchRequestDto searchRequestDto2 = new SearchRequestDto("lastName","lastnametest");
        List<SearchRequestDto> searchRequestDtos = Arrays.asList(searchRequestDto1,searchRequestDto2);
        RequestSpecificationDto.GlobalOperator and = RequestSpecificationDto.GlobalOperator.AND;
        RequestSpecificationDto.GlobalOperator or = RequestSpecificationDto.GlobalOperator.OR;
        RequestSpecificationDto requestSpecificationDtoAnd = new RequestSpecificationDto(searchRequestDtos,and);
        RequestSpecificationDto requestSpecificationDtoOr= new RequestSpecificationDto(searchRequestDtos,or);

        List<User> users = getUsers();
        User user1 = users.get(0);
        User user2 = users.get(1);
        UserDto userDto1= getUserDto(user1);
        UserDto userDto2= getUserDto(user2);
        when(userRepository.findAll(any(Specification.class))).thenReturn(users);
        doReturn(userDto1).when(userMapper).entityToDto(user1);
        doReturn(userDto2).when(userMapper).entityToDto(user2);
        List<UserDto> usersFinded = userService.findAllBySpecification(requestSpecificationDtoOr);
        assertAll("Grouped Assertions of findAllByFilter Users with global operator is AND",
                () -> assertEquals(usersFinded.size(), 2),
                () -> assertEquals(usersFinded.get(0).getFirstName(), "firstname"),
                () -> assertEquals(usersFinded.get(0).getLastName(), "lastname"),
                () -> assertEquals(usersFinded.get(0).getRole(), RoleEnum.CLIENT),
                () -> assertEquals(usersFinded.get(0).getEmail(), "test@gmail.com"),
                () -> assertEquals(usersFinded.get(1).getFirstName(), "firstname1"),
                () -> assertEquals(usersFinded.get(1).getLastName(), "lastname2"),
                () -> assertEquals(usersFinded.get(1).getRole(), RoleEnum.CLIENT),
                () -> assertEquals(usersFinded.get(1).getEmail(), "test@gmail.com"));
    }

    private User getUser() {
        return User.builder().id(101L).firstName("firstname").lastName("lastname").role(RoleEnum.CLIENT).email("test@gmail.com").password("test").build();
    }

    private List<User> getUsers() {
        User user1 = User.builder().id(101L).firstName("firstname").lastName("lastname").role(RoleEnum.CLIENT).email("test@gmail.com").password("test").build();
        User user2 = User.builder().id(101L).firstName("firstname1").lastName("lastname2").role(RoleEnum.CLIENT).email("test@gmail.com").password("test").build();
        return Arrays.asList(user1, user2);
    }

    private UserDto getUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}