package org.example.chapitre1.dto.mapper;

import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.entity.*;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationMapperTest {

    @InjectMocks
    private OperationMapper operationMapper;

    @Mock
    private AccountRepository accountRepository;


    @Test
    void givenOperation_whenEntityToDto_thenRetrieveOperationDto() {
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        List<Operation> operationList = new ArrayList<>();
        Account account = new Account(1L, 50.0F, user1, operationList);
        Operation operation = new Operation(1L, OperationTypeEnum.DEPOSIT, 500F, account);
        OperationDto operationDto = operationMapper.entityToDto(operation);
        assertAll("Grouped Assertions of OperationMapper",
                () -> assertEquals(operationDto.getOperationType(), operation.getOperationType()),
                () -> assertEquals(operationDto.getAmount(), operation.getAmount()),
                () -> assertEquals(operationDto.getId(), operation.getId()),
                () -> assertEquals(operationDto.getAccountId(), operation.getAccount().getId()));
    }

    @Test
    void givenOperationDto_whenDtoToEntity_thenRetrieveOperation() throws AccountNotFoundException {
        List<Operation> operationList = new ArrayList<>();
        User user1 = new User(1L, "firtname1", "lastname1", RoleEnum.CLIENT, "test@gmail.com", "test");
        OperationDto operationDto = new OperationDto(1L, OperationTypeEnum.DEPOSIT, 1L, 500F);
        Account account = new Account(1L, 50.0F, user1, operationList);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account)); // Simule un retour vide pour un ID donné
        Operation actualOperation = operationMapper.dtoToEntity(operationDto);
        assertAll("Grouped Assertions of OperationMapper",
                () -> assertEquals(operationDto.getOperationType(), actualOperation.getOperationType()),
                () -> assertEquals(operationDto.getAmount(), actualOperation.getAmount()),
                () -> assertEquals(operationDto.getAccountId(), actualOperation.getAccount().getId()));
    }

    @Test
    void givenOperationDtoWithNotFoundAccountId_whenDtoToEntity_thenThrowAccountNotFoundException() throws AccountNotFoundException {
        OperationDto operationDto = new OperationDto(4L, OperationTypeEnum.DEPOSIT, 1L, 500F);
        when(accountRepository.findById(anyLong())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> operationMapper.dtoToEntity(operationDto));

    }


}