package br.com.pacto.collaborator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pacto.collaborator.exception.BusinessException;
import br.com.pacto.collaborator.model.Job;
import br.com.pacto.collaborator.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(final JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job findById(final Long id) {
        return this.jobRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Job Not Found"));
    }

    public Job create(final Job entity) {
        return this.jobRepository.save(entity);
    }

    public Page<Job> findPageJobs(final Pageable pageable, final String title) {
        return this.jobRepository.findPageJobs(pageable, title);
    }

}
