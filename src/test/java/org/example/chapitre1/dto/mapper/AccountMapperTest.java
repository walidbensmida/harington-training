package org.example.chapitre1.dto.mapper;

import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.entity.Account;
import org.example.chapitre1.entity.Operation;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    @InjectMocks
    private AccountMapper accountMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenAccount_whenEntityToDto_thenRetrieveAccountDto() throws UserNotFoundException {
        User user = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = List.of(new Operation());
        Account account = new Account(1L, 50.0F,user, operationList);
        AccountDto expectedAccountDto = accountMapper.entityToDto(account);
        assertAll("Grouped Assertions of AccountMapper",
                () -> assertEquals(expectedAccountDto.getBalance(), account.getBalance()),
                () -> assertEquals(expectedAccountDto.getUserId(), account.getUser().getId()));

    }

    @Test
    void givenAccountDto_whenDtoToEntity_thenRetrieveAccount() throws UserNotFoundException {
        User user = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        AccountDto accountDto = new AccountDto(1L, 1L, 50.0F);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Account expectedAccount = accountMapper.dtoToEntity(accountDto);
        assertAll("Grouped Assertions of AccountMapper",
                () -> assertEquals(expectedAccount.getUser().getId(), accountDto.getUserId()),
                () -> assertEquals(expectedAccount.getBalance(), expectedAccount.getBalance()));
    }

    @Test
    void givenAccountDto_whenVerifyFields_thenRetrieveFieldsNumber() {
        Class<AccountDto> accountDtoClass = AccountDto.class;
        Field[] declaredFields = accountDtoClass.getDeclaredFields();
        List<String> fieldNames = Arrays.stream(declaredFields)
                .map(Field::getName)
                .toList();
        assertAll("Grouped Assertions of UserMapper",
                () -> assertEquals(fieldNames.size(), 3),
                () -> assertThat(fieldNames).containsExactly("id", "userId", "balance"));
    }
}