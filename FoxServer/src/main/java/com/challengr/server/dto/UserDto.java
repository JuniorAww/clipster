package com.challengr.server.dto;

import com.challengr.server.model.clip.Clip;
import com.challengr.server.model.clip.Resolution;
import com.challengr.server.model.user.Credential;
import com.challengr.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    long id;
    private String username;
    private List<CredDto> credentials;
    private List<GrantedAuthority> authorities;

    public static UserDto from(User user) {
        List<CredDto> cred = user.getCredentials().stream().map(CredDto::new).toList();
        return new UserDto(user.getId(), user.getUsername(), cred, user.getAuthorities());
    }

    @NoArgsConstructor
    public static class CredDto {
        public Credential.Method method;
        public String metadata;

        public CredDto(Credential credential) {
            this.method = credential.getMethod();
            this.metadata = credential.getMetadata();
        }
    }
}

