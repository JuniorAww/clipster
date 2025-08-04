package com.challengr.server.controllers;

import com.challengr.server.dto.CredentialDto;
import com.challengr.server.dto.auth.JwtAuthResponse;
import com.challengr.server.model.user.Credential;
import com.challengr.server.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public JwtAuthResponse signup(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");

        var rawCredential = payload.get("credential");

        if(rawCredential instanceof LinkedHashMap<?,?> mapCredential) {
            Credential.Method method = Credential.Method.of(mapCredential.get("method"));
            String metadata = (String) mapCredential.get("metadata");

            if(method == Credential.Method.PASSWORD) {
                if(metadata.length() < 8 || metadata.length() > 40) throw new RuntimeException("Wrong password length");
            }

            return authService.signUp(username, new CredentialDto(method, metadata));
        }

        throw new RuntimeException();
    }

    @PostMapping("/login")
    public JwtAuthResponse login(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");

        var rawCredentials = payload.get("credential");

        if(rawCredentials instanceof LinkedHashMap<?,?> mapCredentials) {
            Credential.Method method = Credential.Method.of(mapCredentials.get("method"));
            String metadata = (String) mapCredentials.get("metadata");

            return authService.login(username, List.of(new CredentialDto(method, metadata)));
        }

        throw new RuntimeException();
    }
}
