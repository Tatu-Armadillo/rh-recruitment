package br.com.pacto.collaborator.records.auth;

import java.util.Set;

public record TokenRecord(
        String userName,
        String token,
        Set<String> permissions) {

}
