package org.hedee.aigou.service.impl;

import org.hedee.aigou.domain.User;
import org.hedee.aigou.mapper.UserMapper;
import org.hedee.aigou.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yhptest
 * @since 2019-01-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
