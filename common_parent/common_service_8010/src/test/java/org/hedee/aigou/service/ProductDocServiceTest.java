package org.hedee.aigou.service;

import org.hedee.aigou.CommonServiceApplication_8010;
import org.hedee.aigou.index.ProductDoc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonServiceApplication_8010.class)
public class ProductDocServiceTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void testInit() throws Exception{
        template.createIndex(ProductDoc.class);
        template.putMapping(ProductDoc.class);
    }
}