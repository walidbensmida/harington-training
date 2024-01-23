package org.example.chapitre1.service;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.dto.mapper.AccountMapper;
import org.example.chapitre1.entity.Account;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.AccountRepository;
import org.example.chapitre1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    public List<AccountDto> findAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountMapper::entityToDto).collect(Collectors.toList());
    }

    public AccountDto findById(Long id) throws AccountNotFoundException {
        return accountMapper.entityToDto(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }

    public AccountDto save(AccountDto accountDto) throws UserNotFoundException {
        Account accountToSave = accountMapper.dtoToEntity(accountDto);
        Account accountSaved = accountRepository.save(accountToSave);
        User user = accountSaved.getUser();
        user.setAccount(accountSaved);
        userRepository.save(user);
        return accountMapper.entityToDto(accountSaved);
    }

    public void deleteById(Long id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        accountRepository.deleteById(account.getId());
    }
}
