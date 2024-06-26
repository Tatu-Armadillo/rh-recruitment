package br.com.pacto.collaborator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pacto.collaborator.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(" SELECT employee From Employee employee " +
            " INNER JOIN employee.user user" +
            " WHERE user.userName = :userName ")
    Employee findEmployeeByUser(@Param("userName") String userName);

}
