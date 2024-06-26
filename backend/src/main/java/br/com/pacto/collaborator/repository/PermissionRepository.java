package br.com.pacto.collaborator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pacto.collaborator.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT permission FROM Permission permission WHERE permission.description = :description ")
    public Permission getPermissionByDescription(@Param("description") String description);
}
