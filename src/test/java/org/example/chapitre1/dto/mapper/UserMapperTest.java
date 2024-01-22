package org.example.chapitre1.dto.mapper;

import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserMapperTest {


    @InjectMocks
    private UserMapper userMapper;


    @Test
    void givenUserDto_whenDtoToEntity_thenRetrieveUser() {
        User userExpected = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        UserDto userDto = new UserDto(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        User actualUser = userMapper.dtoToEntity(userDto);
        assertAll("Grouped Assertions of UserMapper",
                () -> assertEquals(actualUser.getFirstName(), userExpected.getFirstName()),
                () -> assertEquals(actualUser.getLastName(), userExpected.getLastName()),
                () -> assertEquals(actualUser.getEmail(), userExpected.getEmail()),
                () -> assertEquals(actualUser.getPassword(), userExpected.getPassword()),
                () -> assertEquals(actualUser.getRole(), userExpected .getRole()));
    }

    @Test
    void givenUser_whenEntityToDto_thenRetrieveUserDto() {
        User user = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        UserDto userDtoExpected = new UserDto(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        UserDto actualUserDto = userMapper.entityToDto(user);
        assertAll("Grouped Assertions of UserMapper",
                () -> assertEquals(actualUserDto.getId(), userDtoExpected.getId()),
                () -> assertEquals(actualUserDto.getFirstName(), userDtoExpected.getFirstName()),
                () -> assertEquals(actualUserDto.getLastName(), userDtoExpected.getLastName()),
                () -> assertEquals(actualUserDto.getEmail(), userDtoExpected.getEmail()),
                () -> assertNull(actualUserDto.getPassword()),
                () -> assertEquals(actualUserDto.getRole(), userDtoExpected.getRole()));
    }

    @Test
    void givenUserDto_whenVerifyFields_thenRetrieveFieldsNumber() {
        Class<UserDto> userDtoClass = UserDto.class;
        Field[] declaredFields = userDtoClass.getDeclaredFields();
        List<String> fieldNames = Arrays.stream(declaredFields)
                .map(Field::getName)
                .toList();
        assertAll("Grouped Assertions of UserMapper",
                () -> assertEquals(fieldNames.size(), 6),
                () -> assertThat(fieldNames).containsExactly("id", "firstName", "lastName", "role", "email", "password"));
    }
}