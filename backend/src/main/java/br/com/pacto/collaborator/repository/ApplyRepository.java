package br.com.pacto.collaborator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pacto.collaborator.model.Apply;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @Query(" SELECT apply FROM Apply apply " +
            " INNER JOIN apply.employeeRequest employee " +
            " INNER JOIN employee.user user " +
            " WHERE user.idUser = :idUser ")
    Page<Apply> findApplyByUser(Pageable pageable, @Param("idUser") Long idUser);

}
