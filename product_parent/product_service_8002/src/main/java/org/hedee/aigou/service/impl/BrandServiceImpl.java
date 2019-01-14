package org.hedee.aigou.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.hedee.aigou.domain.Brand;
import org.hedee.aigou.mapper.BrandMapper;
import org.hedee.aigou.query.BrandQuery;
import org.hedee.aigou.service.IBrandService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.hedee.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public PageList<Brand> selectPageList(BrandQuery query) {
        Page<Brand> page = new Page<>(query.getPage(),query.getRows());
        List<Brand> rows = brandMapper.selectPageList(page,query);
        return new PageList<>(page.getTotal(),rows);
    }
}
