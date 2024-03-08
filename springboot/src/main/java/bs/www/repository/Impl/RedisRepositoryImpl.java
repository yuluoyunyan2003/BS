package bs.www.repository.Impl;

import bs.www.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void setJWT(String jwt) {
        redisTemplate.opsForValue().set(jwt, jwt, 60*60*24*2, TimeUnit.SECONDS);
    }

    @Override
    public boolean isJWTExist(String jwt) {
        return redisTemplate.hasKey(jwt);
    }

    @Override
    public void upDateJWTExpire(String jwt) {
        redisTemplate.delete(jwt);
        this.setJWT(jwt);
    }

    @Override
    public Long getExpire(String jwt) {
        return redisTemplate.getExpire(jwt, TimeUnit.SECONDS);
    }
}
