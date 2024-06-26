package br.com.pacto.collaborator.records.employee;

import br.com.pacto.collaborator.model.Employee;

public record EmployeeRecord(
        String name,
        String cpf,
        String phone,
        String email) {

    public static Employee toEntity(final EmployeeRecord record) {
        final var enity = new Employee();
        enity.setName(record.name);
        enity.setCpf(record.cpf);
        enity.setPhone(record.phone);
        enity.setEmail(record.email);
        return enity;
    }

    public static EmployeeRecord toRecord(final Employee entity) {
        return new EmployeeRecord(
                entity.getName(),
                entity.getCpf(),
                entity.getPhone(),
                entity.getEmail());
    }
}
