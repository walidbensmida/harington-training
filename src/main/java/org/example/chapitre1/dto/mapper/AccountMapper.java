package org.example.chapitre1.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.entity.Account;
import org.example.chapitre1.entity.User;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountMapper {

    private final UserRepository userRepository;

    public Account dtoToEntity(AccountDto accountDto) throws UserNotFoundException {
        User user = userRepository.findById(accountDto.getUserId()).orElseThrow(UserNotFoundException::new);
        return Account.builder()
                .user(user)
                .balance(accountDto.getBalance())
                .build();
    }

    public AccountDto entityToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .userId(account.getUser().getId())
                .balance(account.getBalance())
                .build();
    }
}
