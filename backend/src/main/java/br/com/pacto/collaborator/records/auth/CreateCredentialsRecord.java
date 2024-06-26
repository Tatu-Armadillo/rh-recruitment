package br.com.pacto.collaborator.records.auth;

import java.util.List;

public record CreateCredentialsRecord(
        String fullName,
        String password,
        List<String> permissions) {

}
