package br.com.pacto.collaborator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.repository.UserRepository;
import br.com.pacto.collaborator.configuration.security.TokenService;
import br.com.pacto.collaborator.model.User;
import br.com.pacto.collaborator.records.auth.CreateCredentialsRecord;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final TokenService tokenService;

    @Autowired
    public UserService(
            final UserRepository userRepository,
            final PermissionService permissionService,
            final TokenService tokenService) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }

    public void create(final CreateCredentialsRecord data) {

        final var permissions = this.permissionService.getPermissions(data.permissions());

        final var user = new User(
                data.fullName(),
                data.fullName(),
                data.password(),
                true,
                true,
                true,
                true);
        user.setPermissions(permissions);
        this.userRepository.save(user);
    }

    public User findByUsername(final String username) {
        return this.userRepository.findByUsername(username);
    }

    public User findUserByToken(final String token) {
        final var subjetc = tokenService.getSubject(token);
        return this.userRepository.findByUsername(subjetc);
    }

    public void verifyUserPermissions(final String token, final String role) {
        final var user = findUserByToken(token);
        this.permissionService.verifyPermissions(user.getRoles(), role);
    }

}
