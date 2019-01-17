package org.hedee.aigou.controller;

import org.hedee.aigou.client.PageClient;
import org.hedee.aigou.client.RedisClient;
import org.hedee.aigou.util.RedisJedisPoolUtil;
import org.hedee.aigou.util.VelocityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StaticPageController implements PageClient{
    //map中要放三个参数，
    // model:数据对象
    // templatePath模板路径
    // staticPagePath:静态页面输出路径
    @Override
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public void genStaticPage(@RequestBody Map<String, Object> params) {
        Object model = params.get("model");
        String templatePath = (String) params.get("templatePath");
        String  staticPagePath = (String) params.get("staticPagePath");
        //调用util中的volocityUtils的方法
        VelocityUtils.staticByTemplate(model, templatePath, staticPagePath);
    }
}
