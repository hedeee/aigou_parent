package org.hedee.aigou.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.hedee.aigou.client.PageClient;
import org.hedee.aigou.client.RedisClient;
import org.hedee.aigou.domain.ProductType;
import org.hedee.aigou.mapper.ProductTypeMapper;
import org.hedee.aigou.service.IProductTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {
    @Autowired
    private PageClient pageClient;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> treeData() {
        String productTypeRedis = redisClient.getRedis("productType_redis");
        //redis已经有值，直接拿即可
        if(StringUtils.isNotBlank(productTypeRedis)){
            System.out.println("================Cache=============");
            return JSONArray.parseArray(productTypeRedis, ProductType.class);
        }else{
            //redis没值，查询之后设置进去
            System.out.println("================First=============");
            List<ProductType> productTypes = getTreeDataLoop(0L);
            redisClient.setRedis("productType_redis", JSONArray.toJSONString(productTypes, SerializerFeature.WriteMapNullValue));
            return productTypes;
        }
    }

    //执行增删改时缓存就要更新，这三个方法覆写
    //要更新缓存的同时使得页面静态化完成，方案二重新实现创建方法使得同步synchronizeOpr()
    @Override
    public boolean insert(ProductType entity) {
        super.insert(entity);
        // 采用方案2同步方法
        //redisClient.setRedis("productType_redis", "");
        synchronizeOpr();
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        System.out.println("开始");
        super.deleteById(id);
        //redisClient.setRedis("productType_redis", "");
        synchronizeOpr();
        System.out.println("完毕");
        return true;
    }

    @Override
    public boolean updateById(ProductType entity) {
        super.updateById(entity);
        // redisClient.setRedis("productType_redis", "");
        synchronizeOpr();
        return true;
    }

    //创建缓存和页面静态化的同步方法
    private void synchronizeOpr(){
        //先拿到所有类型，再缓存
        List<ProductType> productTypes = getTreeDataLoop(0L);
        //每次做完增删改后缓存必更新
        redisClient.setRedis("productType_redis", JSONArray.toJSONString(productTypes));
        //先静态化类型
        System.out.println("缓存完毕");
        Map<String,Object> paramsProductTypes = new HashMap<>();
        System.out.println("========================="+productTypes);
        Object model=null;
        paramsProductTypes.put("model",productTypes);
        //主页的模板路径
        paramsProductTypes.put("templatePath","F:\\JavaStudy\\IdeaProjects\\aigou_parent\\product_parent\\product_service_8002\\src\\main\\resources\\template\\productType\\product.type.vm");
        //静态页面的生成路径
        paramsProductTypes.put("staticPagePath","F:\\JavaStudy\\IdeaProjects\\aigou_parent\\product_parent\\product_service_8002\\src\\main\\resources\\template\\productType\\product.type.vm.html");
        //静态化
        pageClient.genStaticPage(paramsProductTypes);
        System.out.println("静态化类型");
        //再静态化主页
        Map<String,Object> staticPage = new HashMap<>();
        //根据主页模板上看，staticRoot是template之前的路径，与后面的拼成绝对路径
        Map<String, Object> modelMap = new HashMap<>();
        //一直到template包以前的路径
        modelMap.put("staticRoot","F:\\JavaStudy\\IdeaProjects\\aigou_parent\\product_parent\\product_service_8002\\src\\main\\resources\\");

        staticPage.put("model",modelMap);
        //主页模板路径
        staticPage.put("templatePath","F:\\JavaStudy\\IdeaProjects\\aigou_parent\\product_parent\\product_service_8002\\src\\main\\resources\\template\\home.vm");
        //主页生成路径
        staticPage.put("staticPagePath","F:\\JavaStudy\\IdeaProjects\\aigou_web_parent\\aigou_shopping\\home.html");
        //静态化
        pageClient.genStaticPage(staticPage);
        System.out.println("静态化主页");
    }



    private List<ProductType> getTreeDataLoop(Long id){
        //用来装返回数据
        List<ProductType> result = new ArrayList<>();
        //获取所有的类型
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        //map建立一个id和productType关系
        Map<Long,ProductType> productTypesMap = new HashMap<>();
        for (ProductType productType : productTypes) {
            productTypesMap.put(productType.getId(), productType);
        }
        //判断有父亲和没有父亲的状态下要执行的
        for (ProductType productType : productTypes) {
            Long pid = productType.getPid();
            //当pid为0时没有父亲，直接装入集合
            if (pid.longValue()==0){
                result.add(productType);
                //有父亲
            }else{
                //拿到父亲类型
                ProductType parent = productTypesMap.get(pid);
                //装入到父类型的children下
                parent.getChildren().add(productType);
            }
        }
        return result;
    }
}
