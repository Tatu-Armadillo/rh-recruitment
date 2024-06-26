package br.com.pacto.collaborator.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.exception.BusinessException;
import br.com.pacto.collaborator.model.Apply;
import br.com.pacto.collaborator.model.Employee;
import br.com.pacto.collaborator.model.enumerator.ApplyStatus;
import br.com.pacto.collaborator.repository.ApplyRepository;

@Service
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final EmployeeService employeeService;
    private final RequestJobService requestJobsJobService;
    private final EmailService emailService;

    @Autowired
    public ApplyService(
            final ApplyRepository applyRepository,
            final EmployeeService employeeService,
            final RequestJobService requestJobsJobService,
            final EmailService emailService) {
        this.applyRepository = applyRepository;
        this.employeeService = employeeService;
        this.requestJobsJobService = requestJobsJobService;
        this.emailService = emailService;
    }

    public Apply findById(final Long idApply) {
        return this.applyRepository.findById(idApply)
                .orElseThrow(() -> new BusinessException("Not found Apply"));
    }

    public Apply create(final Apply entity, final String token) {
        final var employeeRequest = this.employeeService.findEmployeeByUser(token);
        entity.setEmployeeRequest(employeeRequest);

        final var requestJob = this.requestJobsJobService.findById(entity.getRequestJob().getIdRequestJob());
        entity.setRequestJob(requestJob);

        entity.setStatus(ApplyStatus.REQUEST);
        entity.setApplyedDate(LocalDateTime.now());

        this.senEmailByApplyJob(employeeRequest, "Apply for a new position job");
        this.senEmailByApplyJob(requestJob.getResponsible(), "Apply for a new position job");

        return this.applyRepository.save(entity);
    }

    public Page<Apply> findApplyByUser(final Pageable pageable, final String token) {
        final var employeeRequest = this.employeeService.findEmployeeByUser(token);

        return this.applyRepository.findApplyByUser(pageable, employeeRequest.getIdEmployee());
    }

    public Apply modifyApplyStatus(final Long applyId, final String status, final String reason, final String token) {
        final var responsible = this.employeeService.findEmployeeByUser(token);
        final var apply = this.findById(applyId);

        if (!responsible.equals(apply.getRequestJob().getResponsible())) {
            throw new BusinessException("User isn't Responsible by Request Job");
        }

        apply.setReason(reason);
        apply.setStatus(ApplyStatus.valueOf(status));

        return this.applyRepository.save(apply);
    }

    private void senEmailByApplyJob(final Employee employee, final String text) {
        emailService.sendSimpleEmail(
                employee.getEmail(),
                text,
                employee.getName() + text);
    }

}
