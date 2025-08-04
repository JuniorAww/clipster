package com.challengr.server.service;

import com.challengr.server.dto.CredentialDto;
import com.challengr.server.dto.UserDto;
import com.challengr.server.dto.auth.JwtAuthResponse;
import com.challengr.server.model.user.Credential;
import com.challengr.server.model.user.User;
import com.challengr.server.repositories.RefreshTokenRepository;
import com.challengr.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired private final RefreshTokenRepository refreshTokenRepository;
    @Autowired private final CredentialService credentialService;
    @Autowired private final UserRepository userRepository;
    @Autowired private final UserService userService;
    @Autowired private final JwtService jwtService;

    @PostMapping("/signup")
    public JwtAuthResponse signUp(String username, CredentialDto credential) {
        // TODO базовые рейт лимиты
        // TODO проверка капчи
        // TODO проверка гугл ключа
        // TODO отправка и проверка СМС кода

        User user = User.builder()
                .username(username)
                .build();

        Credential dbCred = Credential.builder()
                .user(user)
                .method(credential.getMethod())
                .metadata(credential.getMetadata())
                .build();

        user.setCredentials(List.of(dbCred));

        userService.create(user);
        credentialService.save(dbCred);

        UserDto dto = UserDto.from(user);

        String access = jwtService.generateToken(dto, Duration.ofMinutes(15));
        String refresh = jwtService.generateToken(dto, Duration.ofDays(21));

        return new JwtAuthResponse(access, refresh);
    }

    @PostMapping("/login")
    public JwtAuthResponse login(String username, List<CredentialDto> credentialsDto) {
        // При входе по паролю username - обязателен

        User user = null;

        if(Credential.Method.PASSWORD == credentialsDto.getFirst().getMethod() && username != null) {
            user = userRepository.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("Wrong username"));

            System.out.println(user.getCredentials().getFirst().getMetadata() + " " + user.getCredentials().getFirst().getMethod());

            if(user.getCredentials().stream().noneMatch(cred ->
                    cred.getMethod() == credentialsDto.getFirst().getMethod() && cred.getMetadata().equals(credentialsDto.getFirst().getMetadata())
            )) {
                throw new RuntimeException("Wrong credentials 1");
            };
        } else {
            throw new RuntimeException("Wrong login method");
        }

        if(user == null) return new JwtAuthResponse();

        UserDto dto = UserDto.from(user);

        String access = jwtService.generateToken(dto, Duration.ofMinutes(15));
        String refresh = jwtService.generateToken(dto, Duration.ofDays(21));

        refreshTokenRepository.storeRefreshToken(dto.getId(), refresh, Duration.ofDays(7));

        return new JwtAuthResponse(access, refresh);
    }
}
