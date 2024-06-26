package br.com.pacto.collaborator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pacto.collaborator.configuration.web.response.ResponseBase;
import br.com.pacto.collaborator.records.employee.EmployeeRecord;
import br.com.pacto.collaborator.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;

@Tag(name = "Employees", description = "Endpoints for Managing employees")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Adds a new Employees", description = "Add a new employee", tags = {
            "Employees" }, responses = {
                    @ApiResponse(description = "Create", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeRecord.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseBase<EmployeeRecord>> create(
            @RequestHeader("Authorization") final String token,
            @RequestBody final EmployeeRecord record) {
        final var response = this.employeeService.create(EmployeeRecord.toEntity(record), token);
        final var base = ResponseBase.of(EmployeeRecord.toRecord(response));
        return ResponseEntity.ok(base);
    }

}
