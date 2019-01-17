package org.hedee.aigou.client;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageListClientHystrixFallbackFactory implements FallbackFactory<PageClient> {


    @Override
    public PageClient create(Throwable throwable) {
        return new PageClient() {
            @Override
            public void genStaticPage(Map<String, Object> params) {

            }
        };
    }
}