package org.hedee.aigou.service;

import org.hedee.aigou.ProductService_8002;
import org.hedee.aigou.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductService_8002.class)
public class IProductServiceTest {

    @Autowired
    private IProductService productService;
    @Test
    public void test(){
        productService.deleteById(53L);
    }
}