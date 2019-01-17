package org.hedee.aigou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "AIGOU-COMMON-DEV",fallbackFactory = RedisClientHystrixFallbackFactory.class)
public interface RedisClient {
    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    String getRedis(@RequestParam("key")String key);

    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    void setRedis(@RequestParam("key")String key,@RequestParam("value")String value);
}