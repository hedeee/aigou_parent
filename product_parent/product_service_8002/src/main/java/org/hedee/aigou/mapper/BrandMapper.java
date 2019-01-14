package org.hedee.aigou.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.hedee.aigou.domain.Brand;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.hedee.aigou.query.BrandQuery;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
public interface BrandMapper extends BaseMapper<Brand> {
    //分页查询，联表
    List<Brand> selectPageList(Page<Brand> page,BrandQuery query);
}
