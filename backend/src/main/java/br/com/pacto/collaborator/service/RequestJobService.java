package br.com.pacto.collaborator.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.exception.BusinessException;
import br.com.pacto.collaborator.model.RequestJob;
import br.com.pacto.collaborator.repository.RequestJobRepository;

@Service
public class RequestJobService {

    private final RequestJobRepository requestJobRepository;
    private final JobService jobService;
    private final EmployeeService employeeService;

    @Autowired
    public RequestJobService(
            final RequestJobRepository requestJobRepository,
            final JobService jobService,
            final EmployeeService employeeService,
            final UserService userService) {
        this.requestJobRepository = requestJobRepository;
        this.jobService = jobService;
        this.employeeService = employeeService;
    }

    public RequestJob findById(final Long idRequestJob) {
        return this.requestJobRepository.findById(idRequestJob)
                .orElseThrow(() -> new BusinessException("RequestJob Not Found"));
    }

    public Page<RequestJob> findPageJobs(final Pageable pageable, final String jobTitle) {
        return this.requestJobRepository.findByJobTitle(pageable, jobTitle);
    }

    public RequestJob create(final RequestJob entity, final String token) {
        final var job = this.jobService.findById(entity.getJob().getIdJob());
        entity.setJob(job);

        final var responsible = this.employeeService.findEmployeeByUser(token);
        entity.setResponsible(responsible);

        return this.requestJobRepository.save(entity);
    }

    public RequestJob update(final Long idRequestJob, final RequestJob updateEntity, final String token) {

        final var entity = this.findById(idRequestJob);

        entity.setOpportunities(updateEntity.getOpportunities());
        entity.setClosed(updateEntity.getClosed());
        entity.setClosedDate(updateEntity.getClosedDate());

        final var job = this.jobService.findById(entity.getJob().getIdJob());
        entity.setJob(job);

        final var responsible = this.employeeService.findEmployeeByUser(token);
        entity.setResponsible(responsible);

        return this.requestJobRepository.save(entity);
    }

    private void isResponsibleByJob(final RequestJob entity, final String token) {
        final var responsible = this.employeeService.findEmployeeByUser(token);
        if (!entity.getResponsible().equals(responsible)) {
            throw new BusinessException("You aren't resposible by request Job");
        }
    }

    public RequestJob closed(final Long idRequestJob, final String token) {
        final var entity = this.findById(idRequestJob);
        this.isResponsibleByJob(entity, token);

        entity.setClosed(true);
        entity.setClosedDate(LocalDateTime.now());
        this.requestJobRepository.save(entity);
        return entity;
    }

    public void delete(final Long idRequestJob, final String token) {
        final var entity = this.findById(idRequestJob);
        this.isResponsibleByJob(entity, token);

        this.requestJobRepository.delete(entity);
    }

}
