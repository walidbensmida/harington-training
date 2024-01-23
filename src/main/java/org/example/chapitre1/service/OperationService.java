package org.example.chapitre1.service;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.dto.mapper.OperationMapper;
import org.example.chapitre1.entity.Account;
import org.example.chapitre1.entity.Operation;
import org.example.chapitre1.entity.OperationTypeEnum;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.OperationNotFoundException;
import org.example.chapitre1.exception.UnsupportedOperationTypeException;
import org.example.chapitre1.repository.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationService {

    private final OperationMapper operationMapper;
    private final OperationRepository operationRepository;

    public List<OperationDto> findAll() {
        return operationRepository.findAll().stream().map(operationMapper::entityToDto).collect(Collectors.toList());
    }

    public OperationDto findById(Long id) throws OperationNotFoundException {
        return operationMapper.entityToDto(operationRepository.findById(id).orElseThrow(OperationNotFoundException::new));
    }


    public OperationDto save(OperationDto operationDto) throws AccountNotFoundException, UnsupportedOperationTypeException {
        Operation operation = operationRepository.save(operationMapper.dtoToEntity(operationDto));
        verifyOperation(operation);
        processOperation(operation);
        return operationMapper.entityToDto(operation);
    }

    public void verifyOperation(Operation operation) throws UnsupportedOperationTypeException {
        if ((operation.getOperationType().equals(OperationTypeEnum.DEPOSIT) && operation.getAmount() < 0) ||
                (operation.getOperationType().equals(OperationTypeEnum.WITHDRAWAL) && operation.getAmount() > 0)) {
            throw new UnsupportedOperationTypeException();
        }

    }

    public void deleteById(Long id) throws OperationNotFoundException {
        Operation operation = operationRepository.findById(id).orElseThrow(OperationNotFoundException::new);
        operationRepository.deleteById(operation.getId());
    }

    private void processOperation(Operation operation) throws UnsupportedOperationTypeException {
        switch (operation.getOperationType()) {
            case DEPOSIT:
                processDeposit(operation);
                break;
            case WITHDRAWAL:
                processWithdrawal(operation);
                break;
            default:
                throw new UnsupportedOperationTypeException();
        }
    }

    public Account processDeposit(Operation operation) {
        Account account = operation.getAccount();
        account.setBalance(account.getBalance() + operation.getAmount());
        return account;
    }

    public Account processWithdrawal(Operation operation) {
        Account account = operation.getAccount();
        account.setBalance(account.getBalance() - operation.getAmount());
        return account;
    }
}
