package org.hedee.aigou.client;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisClientHystrixFallbackFactory implements FallbackFactory<RedisClient> {

    @Override
    public RedisClient create(Throwable throwable) {
        return new RedisClient() {
            @Override
            public String getRedis(String key) {
                return null;
            }

            @Override
            public void setRedis(String key, String value) {

            }
        };
    }
}