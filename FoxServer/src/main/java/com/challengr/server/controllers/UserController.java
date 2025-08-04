package com.challengr.server.controllers;

import com.challengr.server.dto.auth.JwtAuthResponse;
import com.challengr.server.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private static final String template = "Hello, %s!";

    // Регистрация по номеру телефона и Google+VK + предварительная капча
    // Логин по номеру телефона, google, vk
    // Изменение имени, картинки профиля, описания, настроек приватности
    // Публикация мнений на странице
    // Личные сообщения

    private final AuthService authService;
}
