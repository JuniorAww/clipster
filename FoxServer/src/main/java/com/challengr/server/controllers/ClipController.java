package com.challengr.server.controllers;

import com.challengr.server.config.CdnProperties;
import com.challengr.server.dto.ClipDto;
import com.challengr.server.model.responses.BoolDao;
import com.challengr.server.model.responses.UploadDao;
import com.challengr.server.service.ClipService;
import com.challengr.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clips")
public class ClipController {
    @Value("${cdn.url}")
    private String CDNUrl;

    @Autowired private ClipService clipService;
    @Autowired private UserService userService;
    private final CdnProperties cdnProperties;

    public ClipController(ClipService clipService, CdnProperties cdnProperties) {
        this.clipService = clipService;
        this.cdnProperties = cdnProperties;
    }

    /*
    ВЗАИМОДЕЙСТВИЕ С ВИДЕО
     */

    @PostMapping("/preload")
    public ResponseEntity<Object> preload(@RequestBody Map<String, Object> payload) {
        Long size = Long.parseLong((String) payload.get("size"));
        System.out.println(" " + CDNUrl + " " + size);
        return ResponseEntity.ok(Map.of("confirmed",
                size,
                "url",
                CDNUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClipDto> getClip(@PathVariable Long id) {
        ClipDto clip = clipService.getClip(id);
        return ResponseEntity.ok(clip);
    }

    @PutMapping("/{id}/react")
    public ResponseEntity<BoolDao> setReactions(@PathVariable Long id) {
        ClipDto clip = clipService.getClip(id);



        return BoolDao.ok(true);
    }

    // Сохранение к себе (репост)
    // Добавить мнение (и мнение к мнению)
    // Отправка жалобы

    /*
    ЗАГРУЗКА ВИДЕО
     */

    // Для клиента (отправка параметров, создание сигнатуры)
    // Отвечает ссылкой с JWT токеном для Nginx
    // Должен быть тайм-аут 10 секунд и проверка логина

    // Для NGINX (подтверждение загрузки)
    // TODO проверка сигнатуры

    /*@PostMapping("/upload")
    public ResponseEntity<BoolEntity> upload() {

    }*/




    /*@GetMapping("/clip")
    public Clip clip(@RequestParam(required = true) long id) {
        System.out.println("> Requested GET for CLIP with ID " + id);
        // TODO integrate redis
        String title;
        Resolutions resolutions;
        return new Clip(id, title, resolutions);
    }*/
}