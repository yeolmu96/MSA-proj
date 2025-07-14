package redis_cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    private final StringRedisTemplate redisTemplate;

    public RedisCacheServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> void setKeyAndValue(K key, V value) {
        setKeyAndValue(key, value, Duration.ofMinutes(720));
    }

    @Override
    public <K, V> void setKeyAndValue(K key, V value, Duration ttl) {
        String keyAsString = String.valueOf(key);
        String valueAsString = String.valueOf(value);

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(keyAsString, valueAsString, ttl);
    }
}
