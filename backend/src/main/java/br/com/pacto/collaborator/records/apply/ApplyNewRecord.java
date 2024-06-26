package br.com.pacto.collaborator.records.apply;

import br.com.pacto.collaborator.model.Apply;
import br.com.pacto.collaborator.model.RequestJob;

public record ApplyNewRecord(
        Long idRequestJob) {

    public static Apply toEntity(final ApplyNewRecord record) {
        final var entity = new Apply();
        final var requestJob = new RequestJob();
        requestJob.setIdRequestJob(record.idRequestJob);
        entity.setRequestJob(requestJob);
        return entity;
    }

}
