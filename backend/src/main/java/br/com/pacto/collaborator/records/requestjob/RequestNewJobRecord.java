package br.com.pacto.collaborator.records.requestjob;

import java.time.LocalDateTime;

import br.com.pacto.collaborator.model.Job;
import br.com.pacto.collaborator.model.RequestJob;

public record RequestNewJobRecord(
        Long opportunities,
        boolean isClosed,
        Long idJob) {

    public static RequestJob toEntity(final RequestNewJobRecord record) {
        final var entity = new RequestJob();
        entity.setOpportunities(record.opportunities);
        entity.setClosed(record.isClosed);
        entity.setCreateDate(LocalDateTime.now());
        final var job = new Job();
        job.setIdJob(record.idJob);
        entity.setJob(job);
        return entity;
    }

}
