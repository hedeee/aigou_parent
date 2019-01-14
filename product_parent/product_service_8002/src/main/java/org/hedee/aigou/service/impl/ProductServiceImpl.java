package org.hedee.aigou.service.impl;

import org.hedee.aigou.domain.Product;
import org.hedee.aigou.mapper.ProductMapper;
import org.hedee.aigou.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
