package org.hedee.aigou.client;

import org.hedee.aigou.index.ProductDoc;
import org.hedee.aigou.util.AjaxResult;
import org.hedee.aigou.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(value = "AIGOU-COMMON-DEV",fallbackFactory = ProductDocClientHystrixFallbackFactory.class,path ="/productDoc" )
//@RequestMapping("/productDoc")
public interface ProductDocClient {
    //添加和修改
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    AjaxResult save(ProductDoc productDoc);
    //删除
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    AjaxResult del(@PathVariable("id") Long id);
    //查询
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    ProductDoc get(@PathVariable("id") Long id); //获取一个
    //批量添加
    @RequestMapping(value = "/batchSave",method = RequestMethod.POST)
    AjaxResult batchSave(@RequestBody List<ProductDoc> productDocs);
    //批量删除
    @RequestMapping(value = "/batchDel",method = RequestMethod.DELETE)
    AjaxResult batchDel(@RequestBody List<Long> ids);
    //分页查询
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    PageList<ProductDoc> search(@RequestBody Map<String, Object> params);

}
