package org.example.chapitre1.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chapitre1.dto.UserDto;
import org.example.chapitre1.exception.UserNotFoundException;
import org.example.chapitre1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("create new user")
    @PostMapping(path = "/v1/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("create new user");
        UserDto userSaved = userService.save(userDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/users/{id}").build().expand(userSaved.getId()).toUri();
        return ResponseEntity.created(location).body(userSaved);
    }

    @ApiOperation("get all users")
    @GetMapping(path = "/v1/users")
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("get all users");
        List<UserDto> usersDto = userService.findAll();
        return ResponseEntity.ok().body(usersDto);
    }

    @ApiOperation("get user by id")
    @GetMapping(path = "/v1/users/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) throws UserNotFoundException {
        log.info("get user by id : {} ", id);
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok().body(userDto);
    }

    @ApiOperation("delete user by id")
    @DeleteMapping(path = "/v1/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
