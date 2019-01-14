package org.hedee.aigou.service.impl;

import com.mysql.jdbc.util.PropertiesDocGenerator;
import org.hedee.aigou.domain.ProductType;
import org.hedee.aigou.mapper.ProductTypeMapper;
import org.hedee.aigou.service.IProductTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> treeData() {
        return getTreeDataLoop(0L);
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
