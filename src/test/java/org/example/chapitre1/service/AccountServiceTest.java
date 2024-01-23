package org.example.chapitre1.service;

import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.dto.mapper.AccountMapper;
import org.example.chapitre1.entity.Account;
import org.example.chapitre1.entity.Operation;
import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.AccountRepository;
import org.example.chapitre1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private UserRepository userRepository;

    @Test
    void whenFindAll_thenRetrieveAllAccounts() {
        List<Operation> operationList = new ArrayList<>();
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");

        Account account1 = new Account(1L, 50.0F, user1, operationList);
        List<Account> accountsMockData = Arrays.asList(account1);

        when(accountRepository.findAll()).thenReturn(accountsMockData);

        when(accountMapper.entityToDto(any(Account.class)))
                .thenAnswer(invocation -> {
                    Account account = invocation.getArgument(0);
                    return new AccountDto(1L, 1L, 50.0F);
                });

        List<AccountDto> result = accountService.findAll();
        assertAll("Grouped Assertions of findAll Accounts",
                () -> assertNotNull(result),
                () -> assertEquals(accountsMockData.size(), result.size()),
                () -> assertEquals(accountsMockData.get(0).getId(), result.get(0).getId()),
                () -> assertEquals(accountsMockData.get(0).getBalance(), result.get(0).getBalance()),
                () -> assertEquals(accountsMockData.get(0).getUser().getId(), result.get(0).getUserId()));

    }

    @Test
    void givenExistingAccountId_whenFindById_thenRetrieveAccount() throws AccountNotFoundException {
        List<Operation> operationList = new ArrayList<>();
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        Account account1 = new Account(1L, 50.0F, user1, operationList);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account1));

        when(accountMapper.entityToDto(any(Account.class)))
                .thenAnswer(invocation -> {
                    Account account = invocation.getArgument(0);
                    return new AccountDto(1L, 1L, 50.0F);
                });
        AccountDto accountDto = accountService.findById(1L);
        assertAll("Grouped Assertions of Account",
                () -> assertNotNull(accountDto),
                () -> assertEquals(account1.getUser().getId(), accountDto.getUserId()),
                () -> assertEquals(account1.getBalance(), accountDto.getBalance()),
                () -> assertEquals(account1.getBalance(), accountDto.getBalance()),
                () -> assertEquals(account1.getId(), accountDto.getId()));

    }

    @Test
    void givenAccountNotFound_whenFetchingAccount_thenThrowException() throws AccountNotFoundException {
        // GIVEN
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty()); // Simule un retour vide pour un ID donné
        // WHEN
        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.findById(2L));
        // THEN
        String expectedMessage = "Account not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenAccountDto_whenSave_thenRetrieveAccountSaved() throws UserNotFoundException {
        AccountDto accountDto = new AccountDto(1L, 1L, 50.0F);
        List<Operation> operationList = new ArrayList<>();
        User user = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        Account account = new Account(1L, 50.0F, user, operationList);
        when(accountMapper.entityToDto(any(Account.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return new AccountDto(1L, 1L, 50.0F);
                });

        when(accountMapper.dtoToEntity(accountDto)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(userRepository.save(user)).thenReturn(user);

        AccountDto accountResultDto = accountService.save(accountDto);
        assertAll("Grouped Assertions of Account",
                () -> assertNotNull(accountResultDto),
                () -> assertEquals(account.getUser().getId(), accountDto.getUserId()),
                () -> assertEquals(account.getBalance(), accountResultDto.getBalance()),
                () -> assertEquals(account.getId(), accountResultDto.getId()));

    }

    @Test
    void givenAccountDto_whenSave_thenThrowUserNotFoundException() throws UserNotFoundException {
        AccountDto accountDto = new AccountDto(1L, 1L, 50.0F);
        when(accountMapper.dtoToEntity(accountDto)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> accountService.save(accountDto));
    }

    @Test
    void givenAccountId_whenDeleteById_thenDeleteAccount() throws AccountNotFoundException {
        Long accountIdToDelete = 101L;
        doReturn(Optional.of(getAccount())).when(accountRepository).findById(accountIdToDelete);
        accountService.deleteById(accountIdToDelete);
        verify(accountRepository, times(1)).deleteById(accountIdToDelete);
    }

    @Test
    void givenAccountId_whenDeleteById_thenDeleteAccountThrowAccountNotFoundException() {
        Long accountIdToDelete = 7000L;
        accountRepository.deleteById(accountIdToDelete);
        assertThrows(AccountNotFoundException.class, () -> accountService.deleteById(accountIdToDelete));
        verify(accountRepository, times(1)).deleteById(accountIdToDelete);
    }

    private Account getAccount() {
        User user = User.builder().id(1L).firstName("firstname").lastName("lastname").role(RoleEnum.CLIENT).email("test@gmail.com").password("test").build();
        return new Account(101L, 50.0F, user, new ArrayList<>());
    }

}