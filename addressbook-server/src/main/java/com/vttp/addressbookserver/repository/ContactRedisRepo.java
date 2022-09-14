package com.vttp.addressbookserver.repository;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.vttp.addressbookserver.models.Contact;

@Repository
public class ContactRedisRepo {
    private Logger logger = Logger.getLogger(ContactRedisRepo.class.getName());

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String MAP_KEY = "contact_map";

    public long count() {
        long count = redisTemplate.opsForHash().keys(MAP_KEY).size();
        logger.info("REDIS >>>> Number of entries: %s".formatted(count));
        return count;
    }

    public void deleteById(String id) {
        redisTemplate.opsForHash().delete(MAP_KEY, id);
    }

    public void deleteAll() {
        redisTemplate.opsForHash().delete(MAP_KEY, findAll());
    }

    public boolean existsById(String id) {
        return redisTemplate.opsForHash().hasKey(MAP_KEY, id);
    }

    public Iterable<Contact> findAll() {
        return redisTemplate.opsForHash().entries(MAP_KEY).values().stream().map(contact -> (Contact) contact).toList();
    }

    public Optional<Contact> findById(String id) {
        Contact contact = (Contact) redisTemplate.opsForHash().get(MAP_KEY, id);
        return Optional.of(contact);
    }

    public <S extends Contact> S save(S entity) {
        redisTemplate.opsForHash().putIfAbsent("contact_map", entity.getId(), entity);
        return entity;
    }
}
