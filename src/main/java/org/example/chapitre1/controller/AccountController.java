package org.example.chapitre1.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chapitre1.dto.AccountDto;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @ApiOperation("create new account")
    @PostMapping(path = "/v1/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) throws UserNotFoundException {
        log.info("create new account");
        AccountDto accountDtoSaved = accountService.save(accountDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/accounts/{id}").build().expand(accountDtoSaved.getId()).toUri();
        return ResponseEntity.created(location).body(accountDtoSaved);
    }

    @ApiOperation("get all accounts")
    @GetMapping(path = "/v1/accounts")
    public ResponseEntity<List<AccountDto>> findAll() {
        log.info("get all accounts");
        List<AccountDto> accountsDto = accountService.findAll();
        return ResponseEntity.ok().body(accountsDto);
    }

    @ApiOperation("get account by id")
    @GetMapping(path = "/v1/accounts/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id) throws AccountNotFoundException {
        log.info("get account by id : {} ", id);
        AccountDto accountDto = accountService.findById(id);
        return ResponseEntity.ok().body(accountDto);
    }


    @ApiOperation("delete account by id")
    @DeleteMapping(path = "/v1/accounts/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws AccountNotFoundException {
        log.info("delete account with id : {} ", id);
        accountService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
