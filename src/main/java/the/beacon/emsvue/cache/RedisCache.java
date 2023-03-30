package the.beacon.emsvue.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import the.beacon.emsvue.utils.ApplicationContextUtils;

/**
 * @author TheBeacon
 * 这个对象由mybatis实例化，没有办法使用springboot的自动注入，spring工厂拿不到
 */
public class RedisCache implements Cache {

    // 官方要求提供一个id
    private String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        // 放入redis缓存
        getRedisTemplate().opsForHash().put(id, key.toString(), value.toString());
    }

    @Override
    public Object getObject(Object key) {
        // 从redis缓存中获取
        return getRedisTemplate().opsForHash().get(id, key.toString());
    }

    @Override
    public Object removeObject(Object o) {
        // 删除指定的缓存信息
        return null;
    }

    @Override
    public void clear() {
        // 清除缓存
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        return getRedisTemplate().opsForHash().size(id).intValue();
    }

    // 封装获取redisTemplate的方法
    public RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        // 字符串序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
