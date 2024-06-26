package br.com.pacto.collaborator.records.requestjob;

import br.com.pacto.collaborator.model.RequestJob;
import br.com.pacto.collaborator.records.job.JobRecord;

public record RequestJobRecord(
                Long id,
                Long opportunities,
                boolean closed,
                String responsible_name,
                JobRecord job) {

        public static RequestJob toEntity(final RequestJobRecord record) {
                final var entity = new RequestJob();
                entity.setClosed(record.closed);
                entity.setOpportunities(record.opportunities);
                entity.setJob(JobRecord.toEntity(record.job));
                return entity;
        }

        public static RequestJobRecord toRecord(final RequestJob entity) {
                return new RequestJobRecord(
                                entity.getIdRequestJob(),
                                entity.getOpportunities(),
                                entity.getClosed(),
                                entity.getResponsible().getName(),
                                JobRecord.toRecord(entity.getJob()));
        }
}
