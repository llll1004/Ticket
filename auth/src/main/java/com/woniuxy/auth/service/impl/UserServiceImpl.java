package com.woniuxy.auth.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.auth.entity.Perm;
import com.woniuxy.auth.entity.User;
import com.woniuxy.auth.exception.AccountExistedException;
import com.woniuxy.auth.exception.IntegrationLackException;
import com.woniuxy.auth.exception.TelExistedException;
import com.woniuxy.auth.mapper.PermMapper;
import com.woniuxy.auth.mapper.UserMapper;
import com.woniuxy.auth.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    PermMapper permMapper;

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    @Override
    public Boolean register(User user) {
        // 检查账号
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", user.getAccount());
        if (userMapper.selectCount(wrapper) > 0) throw new AccountExistedException();
        // 检查手机号
        wrapper = new QueryWrapper<>();
        wrapper.eq("tel",user.getTel());
        if (userMapper.selectCount(wrapper)>0) throw new TelExistedException();
        // 密码加密
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setRegTime(LocalDate.now());

        return userMapper.insert(user) == 1;
    }

    /**
     * 修改用户积分
     *
     * @param chgVal 积分变化值
     * @param id     用户ID
     * @return 修改结果
     */
    @Override
    public Boolean updateIntegrationById(Integer chgVal, Integer id) {
        // 减积分判断积分是否足够
        if (!userMapper.updateIntegrationById(chgVal, id))
            throw new IntegrationLackException();
        return true;
    }

    @Override
    public List<Perm> getMenu(Integer id) {
        return permMapper.selectMenuByUserId(id);
    }
}
