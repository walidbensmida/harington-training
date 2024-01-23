package org.example.chapitre1.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chapitre1.dto.OperationDto;
import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.OperationNotFoundException;
import org.example.chapitre1.exception.UnsupportedOperationTypeException;
import org.example.chapitre1.service.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @ApiOperation("create new operation")
    @PostMapping(path = "/v1/operations")
    public ResponseEntity<OperationDto> createOperation(@RequestBody OperationDto operationDto) throws AccountNotFoundException, UnsupportedOperationTypeException {
        log.info("create new operation");
        OperationDto operationDtoSaved = operationService.save(operationDto);
        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/operations/{id}").build().expand(operationDtoSaved.getId()).toUri();
        return ResponseEntity.created(location).body(operationDtoSaved);
    }

    @ApiOperation("get all operations")
    @GetMapping(path = "/v1/operations")
    public ResponseEntity<List<OperationDto>> findAll() {
        log.info("get all operations");
        List<OperationDto> operationsDto = operationService.findAll();
        return ResponseEntity.ok().body(operationsDto);
    }

    @ApiOperation("get operation by id")
    @GetMapping(path = "/v1/operations/{id}")
    public ResponseEntity<OperationDto> findById(@PathVariable Long id) throws OperationNotFoundException {
        log.info("get operation by id : {} ", id);
        OperationDto operationDto = operationService.findById(id);
        return ResponseEntity.ok().body(operationDto);

    }

    @ApiOperation("delete operation by id")
    @DeleteMapping(path = "/v1/operations/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws OperationNotFoundException {
        operationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
