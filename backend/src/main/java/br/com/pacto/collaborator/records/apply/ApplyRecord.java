package br.com.pacto.collaborator.records.apply;

import br.com.pacto.collaborator.model.Apply;
import br.com.pacto.collaborator.model.enumerator.ApplyStatus;
import br.com.pacto.collaborator.records.employee.EmployeeRecord;
import br.com.pacto.collaborator.records.requestjob.RequestJobRecord;

public record ApplyRecord(
        String status,
        EmployeeRecord requestEmployee,
        RequestJobRecord requestJob) {

    public static Apply toEntity(final ApplyRecord record) {
        final var entity = new Apply();

        entity.setStatus(ApplyStatus.valueOf(record.status));
        entity.setEmployeeRequest(EmployeeRecord.toEntity(record.requestEmployee));
        entity.setRequestJob(RequestJobRecord.toEntity(record.requestJob));

        return entity;
    }

    public static ApplyRecord toRecord(final Apply entity) {
        return new ApplyRecord(entity.getStatus().name(),
                EmployeeRecord.toRecord(entity.getEmployeeRequest()),
                RequestJobRecord.toRecord(entity.getRequestJob()));
    }

}
