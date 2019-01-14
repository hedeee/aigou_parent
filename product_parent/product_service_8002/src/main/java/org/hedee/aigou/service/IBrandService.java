package org.hedee.aigou.service;

import org.hedee.aigou.domain.Brand;
import com.baomidou.mybatisplus.service.IService;
import org.hedee.aigou.query.BrandQuery;
import org.hedee.aigou.util.PageList;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
public interface IBrandService extends IService<Brand> {

    PageList<Brand> selectPageList(BrandQuery query);
}
