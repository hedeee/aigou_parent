package org.hedee.aigou.service;

import org.hedee.aigou.domain.Product;
import com.baomidou.mybatisplus.service.IService;
import org.hedee.aigou.domain.Specification;
import org.hedee.aigou.query.ProductQuery;
import org.hedee.aigou.util.PageList;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-18
 */
public interface IProductService extends IService<Product> {

    PageList<Product> selectPageList(ProductQuery query);

    void addViewProperties(Long productId, List<Specification> specifications);

    void onSale(String ids, Integer onSale);


}
