package org.example.chapitre1.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.entity.Account;
import org.example.chapitre1.entity.Operation;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationMapper {

    private final AccountRepository accountRepository;

    public Operation dtoToEntity(OperationDto operationDto) throws AccountNotFoundException {
        Account account = accountRepository.findById(operationDto.getAccountId()).orElseThrow(AccountNotFoundException::new);

        return Operation.builder()
                .operationType(operationDto.getOperationType())
                .amount(operationDto.getAmount())
                .account(account)
                .build();
    }

    public OperationDto entityToDto(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .operationType(operation.getOperationType())
                .accountId(operation.getAccount().getId())
                .amount(operation.getAmount())
                .build();
    }
}
