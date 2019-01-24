package org.hedee.aigou.controller;

import org.hedee.aigou.domain.Specification;
import org.hedee.aigou.service.IProductService;
import org.hedee.aigou.domain.Product;
import org.hedee.aigou.query.ProductQuery;
import org.hedee.aigou.util.AjaxResult;
import org.hedee.aigou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.insert(product);
            }
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            productService.deleteById(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id)
    {
        return productService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){
        return productService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query) {
            return productService.selectPageList(query);
    }


    @RequestMapping(value="/addViewProperties",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Map<String,Object> params){
        try {
            Integer tmp = (Integer) params.get("productId"); //Integer
            Long productId = Long.parseLong(tmp.toString());
            List<Specification> specifications = (List<Specification>) params.get("specifications");
            productService.addViewProperties(productId,specifications);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("保存显示属性失败！"+e.getMessage());
        }
    }
    //商品上架
    @RequestMapping(value="/onSale",method= RequestMethod.POST)
    public AjaxResult onSale(@RequestBody Map<String,Object> params){
        try {
            String ids = (String) params.get("ids"); //Integer
            Integer onSale = Integer.valueOf(params.get("onSale").toString());
            productService.onSale(ids,onSale);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("商品上架失败！"+e.getMessage());
        }
    }
}
