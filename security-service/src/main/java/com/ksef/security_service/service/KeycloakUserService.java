package com.ksef.security_service.service;

import com.ksef.security_service.DTO.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {
    private final Keycloak keycloack;

    public void createUser(RegisterRequest request){
        CredentialRepresentation credential = new CredentialRepresentation();

        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(List.of(credential));

        keycloack.realm("ksef")
                .users()
                .create(userRepresentation);

    }
}
