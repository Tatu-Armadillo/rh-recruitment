package br.com.pacto.collaborator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pacto.collaborator.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(" SELECT job FROM Job job " +
            " WHERE UPPER(job.title) LIKE UPPER(CONCAT('%', :title, '%')) ")
    Page<Job> findPageJobs(Pageable pageable, @Param("title") String title);

}
