package org.hedee.aigou.repository;

import org.hedee.aigou.index.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocRepository extends ElasticsearchRepository<ProductDoc,Long> {
}
