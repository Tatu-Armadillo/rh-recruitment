package br.com.pacto.collaborator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pacto.collaborator.model.RequestJob;

@Repository
public interface RequestJobRepository extends JpaRepository<RequestJob, Long> {

    @Query(" SELECT requestJob FROM RequestJob requestJob" +
            " INNER JOIN requestJob.job job " +
            " WHERE UPPER(job.title) LIKE UPPER(CONCAT('%', :jobTitle, '%')) ")
    Page<RequestJob> findByJobTitle(Pageable pageable, @Param("jobTitle") String jobTitle);

}
