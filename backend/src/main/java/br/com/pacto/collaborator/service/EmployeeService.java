package br.com.pacto.collaborator.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.model.Employee;
import br.com.pacto.collaborator.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    @Autowired
    public EmployeeService(final EmployeeRepository employeeRepository, final UserService userService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    public Employee create(final Employee employee, final String token) {
        final var user = this.userService.findUserByToken(token);

        employee.setUser(user);
        employee.setStartDate(LocalDateTime.now());

        return this.employeeRepository.save(employee);
    }

    public Employee findEmployeeByUser(final String token) {
        final var user = this.userService.findUserByToken(token);

        return this.employeeRepository.findEmployeeByUser(user.getUsername());
    }

}
