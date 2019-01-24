package org.hedee.aigou.service.impl;

import org.hedee.aigou.index.ProductDoc;
import org.hedee.aigou.repository.ProductDocRepository;
import org.hedee.aigou.service.IProductDocService;
import org.hedee.aigou.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductDocServiceImpl implements IProductDocService {
    @Autowired
    private ProductDocRepository repository;

    @Override
    public void add(ProductDoc productDoc) {
        repository.save(productDoc);
    }

    @Override
    public void del(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ProductDoc get(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void batchSave(List<ProductDoc> productDocs) {
        repository.saveAll(productDocs);
    }

    @Override
    public void batchDel(List<Long> ids) {
        for (Long id : ids) {
            repository.deleteById(id);
        }
    }

    @Override
    public PageList<ProductDoc> search(Map<String, Object> params) {
        return null;
    }
}
