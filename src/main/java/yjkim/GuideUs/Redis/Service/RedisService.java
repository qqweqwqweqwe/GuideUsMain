package yjkim.GuideUs.Redis.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private RedisTemplate<String,String> redisTemplate;




    public void save(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }
}
