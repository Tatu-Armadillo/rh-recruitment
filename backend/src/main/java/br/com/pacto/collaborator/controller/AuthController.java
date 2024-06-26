package br.com.pacto.collaborator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pacto.collaborator.configuration.web.response.ResponseBase;
import br.com.pacto.collaborator.exception.BusinessException;
import br.com.pacto.collaborator.records.auth.AccountCredentialsRecord;
import br.com.pacto.collaborator.records.auth.CreateCredentialsRecord;
import br.com.pacto.collaborator.records.auth.TokenRecord;
import br.com.pacto.collaborator.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Endpoints for Managing token")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private static final String message = "Invalid client request!";

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Create a new users", tags = { "Authentication" })
    @PostMapping("/create")
    public ResponseEntity<ResponseBase<Void>> create(
            @RequestBody final CreateCredentialsRecord data) {
        if (data == null
                || data.password() == null || data.password().isBlank()) {
            throw new BusinessException(message);
        }
        this.authService.create(data);
        return ResponseEntity.ok(ResponseBase.success());
    }

    @Operation(summary = "Authenticates a user and returns a token", tags = { "Authentication" })
    @PostMapping("/signin")
    public ResponseEntity<ResponseBase<TokenRecord>> signin(@RequestBody AccountCredentialsRecord data) {
        if (data == null
                || data.userName() == null || data.userName().isBlank()
                || data.password() == null || data.password().isBlank()) {
            throw new BusinessException(message);
        }

        final var token = this.authService.signin(data);
        final var base = ResponseBase.of(token);
        return ResponseEntity.ok(base);
    }

}
