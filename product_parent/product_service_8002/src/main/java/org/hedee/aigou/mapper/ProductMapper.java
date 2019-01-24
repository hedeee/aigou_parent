package org.hedee.aigou.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.hedee.aigou.domain.Product;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.hedee.aigou.query.ProductQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author yhptest
 * @since 2019-01-18
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> selectPageList(Page<Product> page, ProductQuery query);

    void onSale(Map<String, Object> parmas);

    void offSale(Map<String, Object> parmas);
}
