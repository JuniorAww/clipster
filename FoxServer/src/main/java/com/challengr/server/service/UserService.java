package com.challengr.server.service;

import com.challengr.server.dto.UserDto;
import com.challengr.server.repositories.CredentialRepository;
import com.challengr.server.repositories.UserRepository;
import com.challengr.server.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private CredentialRepository credentialRepository;
    @Autowired private RedisTemplate<String, UserDto> userRedisCache;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        // Redis key
        String key = "user:" + id;
        UserDto dto = userRedisCache.opsForValue().get(key);
        System.out.println(userRedisCache.opsForValue().get("user:" + id));

        if(dto == null) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("not found"));
            user.getCredentials().size();
            //user.getAuthorities().size();
            dto = UserDto.from(user);
            userRedisCache.opsForValue().set(key, dto);
        }

        return dto;
    }

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Such username is already occupied!");
        }

        if (user.getCredentials().stream().anyMatch(x -> credentialRepository.existsByMethodAndMetadata(x.getMethod(), x.getMetadata()))) {
            throw new RuntimeException("Such email is already occupied!");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        //user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
