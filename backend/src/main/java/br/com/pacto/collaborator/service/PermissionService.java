package br.com.pacto.collaborator.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.exception.BusinessException;
import br.com.pacto.collaborator.model.Permission;
import br.com.pacto.collaborator.model.enumerator.PermissionEnum;
import br.com.pacto.collaborator.repository.PermissionRepository;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Set<Permission> getPermissions(final List<String> permissionRequest) {
        permissionRequest.add(PermissionEnum.EMPLOYEE.name());
        final var permissions = new HashSet<Permission>();
        permissionRequest.forEach(p -> permissions.add(this.permissionRepository.getPermissionByDescription(p)));
        return permissions;
    }

    public boolean verifyPermissions(final Set<String> roles, final String requestRole) {
        for (var permission : roles) {
            if (permission.equals(requestRole)) {
                return true;
            }
        }
        throw new BusinessException("User don't have requireds permissions");
    }

}
