package com.challengr.server.dto;

import com.challengr.server.model.user.Credential;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CredentialDto {
    private Credential.Method method;
    private String metadata;

    public CredentialDto(Credential.Method method, String metadata) {
        this.method = method;
        this.metadata = metadata;
    }
}
