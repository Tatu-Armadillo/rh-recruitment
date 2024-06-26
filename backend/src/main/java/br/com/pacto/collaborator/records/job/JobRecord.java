package br.com.pacto.collaborator.records.job;

import br.com.pacto.collaborator.model.Job;

public record JobRecord(
        String title,
        String description,
        String requirements) {

    public static Job toEntity(final JobRecord record) {
        final var enity = new Job();
        enity.setTitle(record.title);
        enity.setDescription(record.description);
        enity.setRequirements(record.requirements);
        return enity;
    }

    public static JobRecord toRecord(final Job entity) {
        return new JobRecord(
                entity.getTitle(),
                entity.getDescription(),
                entity.getRequirements());
    }

}
