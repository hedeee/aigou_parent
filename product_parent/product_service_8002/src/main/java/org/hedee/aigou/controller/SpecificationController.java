package org.hedee.aigou.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang.StringUtils;
import org.hedee.aigou.domain.Product;
import org.hedee.aigou.service.IProductService;
import org.hedee.aigou.service.ISpecificationService;
import org.hedee.aigou.domain.Specification;
import org.hedee.aigou.query.SpecificationQuery;
import org.hedee.aigou.util.AjaxResult;
import org.hedee.aigou.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    private ISpecificationService specificationService;
    @Autowired
    private IProductService productService;

    /**
    * 保存和修改公用的
    * @param specification  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Specification specification){
        try {
            if(specification.getId()!=null){
                specificationService.updateById(specification);
            }else{
                specificationService.insert(specification);
            }
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setMessage("保存对象失败！"+e.getMessage());
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
            specificationService.deleteById(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.getAjaxResult().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Specification get(@PathVariable("id") Long id)
    {
        return specificationService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Specification> list(){

        return specificationService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Specification> json(@RequestBody SpecificationQuery query)
    {
        Page<Specification> page = new Page<Specification>(query.getPage(),query.getRows());
            page = specificationService.selectPage(page);
            return new PageList<Specification>(page.getTotal(),page.getRecords());
    }

    /**
     * 查询所有显示属性的值
     * @return
     */
    @RequestMapping(value = "/product/{productId}",method = RequestMethod.GET)
    public List<Specification> queryViewProperties(@PathVariable("productId")Long productId){
        Product product = productService.selectById(productId);
        String viewProperties = product.getViewProperties();
        if(StringUtils.isNotBlank(viewProperties)){
            return JSONArray.parseArray(viewProperties, Specification.class);
        }else {
            EntityWrapper<Specification> wapper = new EntityWrapper<>();
            wapper.eq("product_type_id", product.getProductTypeId());
            wapper.eq("is_sku", 0);
            return specificationService.selectList(wapper);
        }
    }
//    sku属性
    @RequestMapping(value = "/product/skusProperties/{productId}",method = RequestMethod.GET)
    public List<Specification> querySkusProperties(@PathVariable("productId") Long productId){
        //获取商品,尝试从里面获取sku_template
        Product product = productService.selectById(productId);
        String skuTemplate = product.getSkuTemplate();
        if (StringUtils.isNotBlank(skuTemplate)){
            return JSONArray.parseArray(skuTemplate, Specification.class);
        }
        //如果有直接转换返回,否则从属性表中查询
        //参数 类型ID 是否是sku
        EntityWrapper<Specification> w = new EntityWrapper<>();
        w.eq("product_type_id", product.getProductTypeId());
        w.eq("is_sku", 1);
        return  specificationService.selectList(w);
    }
}
