package br.com.pacto.collaborator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.configuration.security.TokenService;
import br.com.pacto.collaborator.model.User;
import br.com.pacto.collaborator.records.auth.AccountCredentialsRecord;
import br.com.pacto.collaborator.records.auth.CreateCredentialsRecord;
import br.com.pacto.collaborator.records.auth.TokenRecord;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthService(
            final AuthenticationManager authenticationManager,
            final PasswordEncoder passwordEncoder,
            final UserService userService,
            final TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenRecord signin(final AccountCredentialsRecord data) {
        final var username = data.userName();
        final var password = data.password();
        final var user = this.userService.findByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("Invalid username/password");
        }

        final var authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        final var token = new TokenRecord(username, tokenService.createToken((User) authentication.getPrincipal()));

        return token;
    }

    public void create(final CreateCredentialsRecord data) {
        final var password = passwordEncoder.encode(data.password());
        this.userService
                .create(new CreateCredentialsRecord(data.fullName(), password, data.permissions()));
    }

}
