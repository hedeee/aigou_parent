package org.hedee.aigou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "AIGOU-COMMON-DEV",fallbackFactory = PageListClientHystrixFallbackFactory.class)
public interface PageClient {

    //map中要放三个参数，
    // model:数据对象
    // templatePath模板路径
    // staticPagePath:静态页面输出路径
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    void genStaticPage(@RequestBody Map<String,Object> params);
}
