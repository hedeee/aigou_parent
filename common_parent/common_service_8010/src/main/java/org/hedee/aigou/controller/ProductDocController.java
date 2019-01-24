package org.hedee.aigou.controller;

import org.hedee.aigou.index.ProductDoc;
import org.hedee.aigou.service.IProductDocService;
import org.hedee.aigou.util.AjaxResult;
import org.hedee.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productDoc")
public class ProductDocController {
    @Autowired
    private IProductDocService productDocService;

    //添加和修改
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    AjaxResult save(@RequestBody ProductDoc productDoc){
        try {
            productDocService.add(productDoc);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
        }
    }

    //删除
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    AjaxResult del(@PathVariable("id") Long id){
        try {
            productDocService.del(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //查询,获取一个
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    ProductDoc get(@PathVariable("id") Long id){
        return productDocService.get(id);
    };

    //批量添加
    @RequestMapping(value = "/batchSave",method = RequestMethod.POST)
    AjaxResult batchSave(@RequestBody List<ProductDoc> productDocs){
            try {
                productDocService.batchSave(productDocs);
                return AjaxResult.getAjaxResult();
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxResult.getAjaxResult().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
            }
    };

    //批量删除
    @RequestMapping(value = "/batchDel",method = RequestMethod.DELETE)
    AjaxResult batchDel(@RequestBody List<Long> ids){
        try {
            productDocService.batchDel(ids);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    };

    //分页查询
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    PageList<ProductDoc> search(Map<String, Object> params){
        return productDocService.search(params);
    };
}
