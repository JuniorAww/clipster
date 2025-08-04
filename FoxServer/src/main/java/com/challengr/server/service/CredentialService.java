package com.challengr.server.service;

import com.challengr.server.dto.UserDto;
import com.challengr.server.model.user.Credential;
import com.challengr.server.model.user.User;
import com.challengr.server.repositories.CredentialRepository;
import com.challengr.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CredentialService {
    @Autowired private CredentialRepository credentialRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Credential save(Credential credential) {
        return credentialRepository.save(credential);
    }
}
