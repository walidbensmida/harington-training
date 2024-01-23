package org.example.chapitre1.service;

import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.dto.mapper.OperationMapper;
import org.example.chapitre1.entity.*;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.OperationNotFoundException;
import org.example.chapitre1.exception.UnsupportedOperationTypeException;
import org.example.chapitre1.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

    @InjectMocks
    private OperationService operationService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private OperationMapper operationMapper;


    @Test
    void whenFindAll_thenRetrieveAllOperations() {
        List<Operation> operationList = new ArrayList<>();
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.DEPOSIT, 500F, account);
        operationList.add(operation);
        when(operationRepository.findAll()).thenReturn(operationList);

        when(operationMapper.entityToDto(any(Operation.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return new OperationDto(1L, OperationTypeEnum.DEPOSIT, 1L, 500F);
                });

        List<OperationDto> result = operationService.findAll();
        assertAll("Grouped Assertions of findAll operations",
                () -> assertNotNull(result),
                () -> assertEquals(operationList.size(), result.size()),
                () -> assertEquals(operationList.get(0).hashCode(), result.get(0).hashCode()));
    }

    @Test
    void givenExistingOperationId_whenFindById_thenRetrieveOperation() throws OperationNotFoundException {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = new ArrayList<>();
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.DEPOSIT, 500F, account);
        when(operationRepository.findById(anyLong())).thenReturn(Optional.of(operation));
        when(operationMapper.entityToDto(any(Operation.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return new OperationDto(1L, OperationTypeEnum.DEPOSIT, 1L, 500F);
                });
        OperationDto result = operationService.findById(1L);
        assertEquals(result.hashCode(), operation.hashCode());

    }

    @Test
    void givenExistingOperationId_whenFindById_thenThrowOperationNotFoundException() throws OperationNotFoundException {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(OperationNotFoundException.class, () -> operationService.findById(2L));
        String expectedMessage = "Operation not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void givenOperationDto_whenSave_thenRetrieveOperationSaved() throws UnsupportedOperationTypeException, AccountNotFoundException {
        List<Operation> operationList = new ArrayList<>();
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.DEPOSIT, 500F, account);
        OperationDto operationDto = new OperationDto(1L, OperationTypeEnum.DEPOSIT, 1L, 500F);
        when(operationMapper.entityToDto(any(Operation.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return new OperationDto(1L, OperationTypeEnum.DEPOSIT, 1L, 500F);
                });
        when(operationMapper.dtoToEntity(any(OperationDto.class)))
                .thenAnswer(invocation -> {
                    invocation.getArgument(0);
                    return new Operation(1L, OperationTypeEnum.DEPOSIT, 500F, account);
                });
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);

        OperationDto operationDtoSaved = operationService.save(operationDto);
        assertEquals(operationDtoSaved.hashCode(), operation.hashCode());
    }

    @Test
    void givenOperationDto_whenSave_thenThrowAccountNotFoundException() throws UnsupportedOperationTypeException, AccountNotFoundException {
        OperationDto operationDto = new OperationDto(1L, OperationTypeEnum.DEPOSIT, 1L, 500F);
        when(operationMapper.dtoToEntity(operationDto)).thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class, () -> operationService.save(operationDto));
    }


    @Test
    void givenOperationId_whenDeleteById_thenDeleteOperation() throws OperationNotFoundException {
        Long operationIdToDelete = 100L;
        doReturn(Optional.of(getOperation())).when(operationRepository).findById(operationIdToDelete);
        operationService.deleteById(operationIdToDelete);
        verify(operationRepository, times(1)).deleteById(operationIdToDelete);
    }

    @Test
    void givenNotExistOperationId_whenDeleteById_thenThrowOperationNotFoundException() {
        Long accountIdToDelete = 7000L;
        operationRepository.deleteById(accountIdToDelete);
        assertThrows(OperationNotFoundException.class, () -> operationService.deleteById(accountIdToDelete));
        verify(operationRepository, times(1)).deleteById(accountIdToDelete);
    }

    @Test
    void givenOperation_whenDepositAndAmountSubZero_thenThrowUnsupportedOperationException() {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = null;
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.DEPOSIT, -500F, account);
        Exception exception = assertThrows(UnsupportedOperationTypeException.class, () -> operationService.verifyOperation(operation));
        String expectedMessage = "Unsupported operation";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenOperation_whenWithdrawalAndAmountSupZero_thenThrowUnsupportedOperationException() {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = null;
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.WITHDRAWAL, 500F, account);
        Exception exception = assertThrows(UnsupportedOperationTypeException.class, () -> operationService.verifyOperation(operation));
        String expectedMessage = "Unsupported operation";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenOperation_whenDeposit_thenCalculate() {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = null;
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.DEPOSIT, 500F, account);
        float result = account.getBalance() + operation.getAmount();
        Account accountDepot = operationService.processDeposit(operation);
        assertEquals(accountDepot.getBalance(), result);
    }

    @Test
    void givenOperation_whenWithdrawal_thenCalculate() {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = null;
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.WITHDRAWAL, 10F, account);
        float result = account.getBalance() - operation.getAmount();
        Account accountWithdrawal = operationService.processWithdrawal(operation);
        assertEquals(accountWithdrawal.getBalance(), result);
    }

    private Operation getOperation() {
        User user = User.builder().id(1L).firstName("firstname").lastName("lastname").role(RoleEnum.CLIENT).email("test@gmail.com").password("test").build();
        Account account = Account.builder().id(1L).balance(50.0F).user(user).build();
        return Operation.builder().id(100L).operationType(OperationTypeEnum.DEPOSIT).amount(500f).account(account).build();
    }


}