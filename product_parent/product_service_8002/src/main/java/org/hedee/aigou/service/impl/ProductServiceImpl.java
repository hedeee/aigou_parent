package org.hedee.aigou.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.hedee.aigou.client.ProductDocClient;
import org.hedee.aigou.domain.*;
import org.hedee.aigou.index.ProductDoc;
import org.hedee.aigou.mapper.*;
import org.hedee.aigou.query.ProductQuery;
import org.hedee.aigou.service.IProductService;
import org.hedee.aigou.util.PageList;
import org.hedee.aigou.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;


/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @since 2019-01-18
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductExtMapper productExtMapper;

    @Autowired
    private ProductDocClient productDocClient;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public PageList<Product> selectPageList(ProductQuery query) {

        Page<Product> page = new Page<>(query.getPage(),query.getRows());
        List<Product> data =  productMapper.selectPageList(page,query);
        long total = page.getTotal();
        return new PageList<>(total,data);
    }


    @Override
    public boolean insert(Product entity) {
        //添加本表信息以外,还要存放关联表
        entity.setCreateTime(new Date().getTime());
        productMapper.insert(entity);

        System.out.println(entity.getId());
        if (entity.getProductExt() != null) {
            entity.getProductExt().setProductId(entity.getId());
            productExtMapper.insert(entity.getProductExt());
        }
        return true;
    }
    @Override
    public boolean updateById(Product entity) {
        //添加本表信息以外,还要存放关联表
        entity.setUpdateTime(new Date().getTime());
        productMapper.updateById(entity);


        //通过productId查询productExt
        Wrapper<ProductExt> wrapper = new EntityWrapper<ProductExt>()
                .eq("productId", entity.getId());
        ProductExt productExt = productExtMapper.selectList(wrapper).get(0);

        //把前台传递进来值设置给数据库查询出来值,并且把它修改进去
        ProductExt tmp = entity.getProductExt();
        if (tmp!= null) {
            productExt.setDescription(tmp.getDescription());
            productExt.setRichContent(tmp.getRichContent());
            productExtMapper.updateById(productExt);
        }

        //上架状态修改es库
        if(1==entity.getState()){
            ProductDoc productDoc = product2ProductDoc(entity);
            productDocClient.save(productDoc);
        }
        return true;
    }

    private ProductDoc product2ProductDoc(Product product) {
        ProductDoc productDoc = new ProductDoc();
        productDoc.setId(product.getId());
        productDoc.setName(product.getName());
        productDoc.setProuductTypeId(product.getProductTypeId());
        productDoc.setBrandId(product.getBrandId());
        //从某个商品sku中获取最大或最小
        List<Sku> skus = skuMapper.selectList(new EntityWrapper<Sku>()
                .eq("productId", product.getId()));

        Integer minPrice  = skus.get(0).getPrice();
        Integer maxPrice  = skus.get(0).getPrice();
        for (Sku sku : skus) {
            if (sku.getPrice()<minPrice) minPrice=sku.getPrice();
            if (sku.getPrice()>maxPrice) maxPrice = sku.getPrice();
        }
        productDoc.setMinPrice(minPrice);
        productDoc.setMaxPrice(maxPrice);
        productDoc.setSaleCount(product.getSaleCount());
        productDoc.setOnSaleTime(product.getOnSaleTime().intValue());
        productDoc.setCommentCount(product.getCommentCount());
        productDoc.setViewCount(product.getViewCount());
        String medias = product.getMedias();
        if (StringUtils.isNotBlank(medias)) {
            productDoc.setImages(Arrays
                    .asList(medias.split(",")));
        }
        Brand brand = brandMapper.selectById(product.getBrandId());
        ProductType productType = productTypeMapper.selectById(product.getProductTypeId());
        //投机-有空格就会分词
        String all = product.getName()+" "
                +product.getSubName()+" "+brand.getName()+" "+productType.getName();

        productDoc.setAll(all);
        productDoc.setViewProperties(product.getViewProperties());
        productDoc.setSkuProperties(product.getSkuTemplate());
        //设置值
        return productDoc;
    }

    //删除的时候不止删除数据库内容，同时把es库的数据进行删除
    @Override
    public boolean deleteById(Serializable id) {
        Product product = productMapper.selectById(id);
        super.deleteById(id);
        Integer state = product.getState();
        if(1== state){
            productDocClient.del(Long.valueOf(id.toString()));
        }
        return true;
    }

    @Override
    public void addViewProperties(Long productId, List<Specification> specifications) {
        String viewSpec = JSONArray.toJSONString(specifications);
        Product product = productMapper.selectById(productId);
        product.setViewProperties(viewSpec);
        productMapper.updateById(product);
    }

    @Override
    public void onSale(String ids, Integer onSale) {
        List<Long> idsLong = StrUtils.splitStr2LongArr(ids);
        //上架状态，数据库的数据和上架时间修改
        if(1==onSale.intValue()){
            Map<String,Object> parmas = new HashMap<>();
            parmas.put("ids", idsLong);//数据修改
            parmas.put("timeStamp", new Date().getTime());//时间修改
            productMapper.onSale(parmas);
            //存到es库中
            List<ProductDoc> productDocs = product2ProductDocs(idsLong);
            productDocClient.batchSave(productDocs);
        }else{
            //下架
            //数据库数据和时间进行修改
            Map<String,Object> parmas = new HashMap<>();
            parmas.put("ids", idsLong);//数据修改
            parmas.put("timeStamp", new Date().getTime());//时间修改
            productMapper.offSale(parmas);
            //es库删除索引数据
            productDocClient.batchDel(idsLong);
        }
    }
    //多个数据es转换
    private List<ProductDoc> product2ProductDocs(List<Long> ids) {
        List<ProductDoc> list=new ArrayList<>();
        for (Long id : ids) {
            Product product = productMapper.selectById(id);
            ProductDoc productDoc = product2ProductDoc(product);
            list.add(productDoc);
        }
        return list;
    }


}
