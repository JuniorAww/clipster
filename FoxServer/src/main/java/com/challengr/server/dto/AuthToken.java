package com.challengr.server.dto;

import com.challengr.server.model.user.Credential;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;



public class AuthToken extends AbstractAuthenticationToken {
    private final UserDto userDto;

    public AuthToken(UserDto userDto, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userDto = userDto;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDto;
    }
}
