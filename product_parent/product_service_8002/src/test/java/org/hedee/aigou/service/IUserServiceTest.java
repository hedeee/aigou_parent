package org.hedee.aigou.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.hedee.aigou.ProductService_8002;
import org.hedee.aigou.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductService_8002.class)
public class IUserServiceTest {

    @Autowired
    private IUserService userService;
    @Test
    public void testadd()throws Exception{
        for(int i =0;i<10;i++)
            userService.insert(new User("hfajsnf=="+i));

    }
    @Test
    public void testseletAll()throws Exception{
        List<User> users = userService.selectList(null);
        System.out.println(users);
    }
    @Test
    public void testseletOne()throws Exception{
        User user = userService.selectById(1L);
        System.out.println(user);
    }
    @Test
    public void testupdate()throws Exception{
        User user = userService.selectById(3L);
        user.setName("hhhhhhhhhhhhhhhhhhhhhhhhh-----");
        userService.updateById(user);
        System.out.println(user.selectById(2L));
    }
    @Test
    public void testdel()throws Exception{
        userService.deleteById(2L);
    }
    @Test
    public void testPage()throws Exception{
        Page<User> page = new Page<>(1,4);
        Wrapper wrapper =  new EntityWrapper();
        wrapper.like("name","h");
        userService.selectPage(page, wrapper);

        System.out.println(page.getTotal());
        System.out.println(page.getRecords());

    }
}