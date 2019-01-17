package org.hedee.aigou.controller;

import org.hedee.aigou.client.RedisClient;
import org.hedee.aigou.util.RedisJedisPoolUtil;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisController implements RedisClient{
    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    public String getRedis(@RequestParam("key")String key){
        return RedisJedisPoolUtil.INSTANCE.get(key);
    }

    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    public void setRedis(@RequestParam("key")String key,@RequestParam("value")String value){
        RedisJedisPoolUtil.INSTANCE.set(key, value);
    }
}
