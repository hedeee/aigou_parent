package org.hedee.aigou.service;

import org.hedee.aigou.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
public interface IProductTypeService extends IService<ProductType> {
//    返回产品类型的菜单
    List<ProductType> treeData();
}
