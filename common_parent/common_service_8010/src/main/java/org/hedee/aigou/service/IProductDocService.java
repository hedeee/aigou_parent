package org.hedee.aigou.service;

import org.hedee.aigou.index.ProductDoc;
import org.hedee.aigou.util.PageList;

import java.util.List;
import java.util.Map;

public interface IProductDocService {
    void add(ProductDoc productDoc);

    void del(Long id);

    ProductDoc get(Long id);

    void batchSave(List<ProductDoc> productDocs);

    void batchDel(List<Long> ids);

    PageList<ProductDoc> search(Map<String, Object> params);
}
