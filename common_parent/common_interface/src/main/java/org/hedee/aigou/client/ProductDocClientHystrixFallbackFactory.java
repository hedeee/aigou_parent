package org.hedee.aigou.client;


import feign.hystrix.FallbackFactory;
import org.hedee.aigou.index.ProductDoc;
import org.hedee.aigou.util.AjaxResult;
import org.hedee.aigou.util.PageList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductDocClientHystrixFallbackFactory implements FallbackFactory<ProductDocClient> {


    @Override
    public ProductDocClient create(Throwable throwable) {
        return new ProductDocClient() {
            @Override
            public AjaxResult save(ProductDoc productDoc) {
                return null;
            }

            @Override
            public AjaxResult del(Long id) {
                return null;
            }

            @Override
            public ProductDoc get(Long id) {
                return null;
            }

            @Override
            public AjaxResult batchSave(List<ProductDoc> productDocs) {
                return null;
            }

            @Override
            public AjaxResult batchDel(List<Long> ids) {
                return null;
            }

            @Override
            public PageList<ProductDoc> search(Map<String, Object> params) {
                return null;
            }
        };
    }
}