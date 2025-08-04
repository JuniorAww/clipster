package com.challengr.server.service;

import com.challengr.server.repositories.ClipRepository;
import com.challengr.server.dto.ClipDto;
import com.challengr.server.model.clip.Clip;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClipService {
    @Autowired private ClipRepository clipRepo;
    private final RedisTemplate<String, ClipDto> clipRedisCache;

    @Transactional(readOnly = true)
    public ClipDto getClip(Long id) {
        // Redis key
        String key = "clip:" + id;
        ClipDto dto = clipRedisCache.opsForValue().get(key);
        System.out.println(clipRedisCache.opsForValue().get("clip:" + id));
        if(dto == null) {
            Clip clip = clipRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("not found"));
            // Lazy loading (how performant it is?)
            clip.getResolutions().size();
            dto = ClipDto.from(clip);
            clipRedisCache.opsForValue().set(key, dto);
        }

        return dto;
    }

    /*@Cacheable(value = "clips", key = "#id")
    public Clip getClip(Long id) {


        return clipRepo.findById(id).orElseThrow(
                () -> new RuntimeException("not found")
        );
    }*/
}
