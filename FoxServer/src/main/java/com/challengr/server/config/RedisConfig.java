package com.challengr.server.config;

import com.challengr.server.dto.ClipDto;
import com.challengr.server.dto.UserDto;
import com.challengr.server.model.clip.Clip;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // JSON -> redis -> JSON
    @Bean
    public RedisTemplate<String, ClipDto> clipRedisCache(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ClipDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        Jackson2JsonRedisSerializer<ClipDto> serializer = new Jackson2JsonRedisSerializer<>(mapper, ClipDto.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisTemplate<String, UserDto> userRedisCache(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);

        Jackson2JsonRedisSerializer<UserDto> serializer = new Jackson2JsonRedisSerializer<>(mapper, UserDto.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();

        return template;
    }

}
