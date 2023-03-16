package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.UserAddDto;
import com.mydddyh.domain.dto.UserGetDto;
import com.mydddyh.domain.dto.UserStatusDto;
import com.mydddyh.domain.entity.User;
import com.mydddyh.domain.entity.UserRole;
import com.mydddyh.domain.vo.*;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.UserMapper;
import com.mydddyh.service.RoleService;
import com.mydddyh.service.UserRoleService;
import com.mydddyh.service.UserService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.SecurityUtils;
import com.mydddyh.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        // 根据id查用户信息
        // 信息可能会反复修改, 不应该从SecurityContextHolder或Redis中取数据
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);

        // 封装成vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserInfoVo userInfoVo) {
        // 检查用户id是否匹配
        Long userId = SecurityUtils.getUserId();
        if (!userId.equals(userInfoVo.getId())) {
            throw new SystemException(AppHttpCodeEnum.WRONG_USER_ID);
        }

        // 生成User
        User user = BeanCopyUtils.copyBean(userInfoVo, User.class);
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        // 数据非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NULL);
        }

        // 数据唯一性判断
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        queryWrapper.clear();
        queryWrapper.eq(User::getEmail, user.getEmail());
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        // 加密密码
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        // 设置id和权限
        user.setId(null);
        user.setType(SystemConstants.NORMAL_USER_TYPE_FIELD);

        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageListUser(Integer pageNum, Integer pageSize, UserGetDto userGetDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.like(StringUtils.hasText(userGetDto.getUserName()), User::getUserName, ServiceUtils.addWildcard(userGetDto.getUserName()));
        queryWrapper.like(StringUtils.hasText(userGetDto.getPhoneNumber()), User::getPhoneNumber, ServiceUtils.addWildcard(userGetDto.getPhoneNumber()));
        queryWrapper.like(StringUtils.hasText(userGetDto.getStatus()), User::getStatus, ServiceUtils.addWildcard(userGetDto.getStatus()));
        Page<User> page = new Page<User>(pageNum, pageSize);
        page(page, queryWrapper);
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        PageVo pageVo = new PageVo(userListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional
    public ResponseResult addUser(UserAddDto userAddDto) {
        // 检查用户名
        String userName = userAddDto.getUserName();
        if (!StringUtils.hasText(userName)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NULL);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, userName);
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 检查手机号
        String phoneNumber = userAddDto.getPhoneNumber();
        queryWrapper.clear();
        queryWrapper.eq(User::getPhoneNumber, phoneNumber);
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        // 检查邮箱
        String email = userAddDto.getEmail();
        queryWrapper.clear();
        queryWrapper.eq(User::getEmail, email);
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 转换密码
        String password = userAddDto.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        userAddDto.setPassword(encodePassword);
        // 存储User
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        save(user);
        // 更新User-Role关联表
        Long userId = user.getId();
        List<Long> roleIds = userAddDto.getRoleIds();
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> new UserRole(userId, roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeUser(String id) {
        // 剔除多行删除
        Integer userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new SystemException(AppHttpCodeEnum.DELETE_MULTI_ROWS);
        }
        // 不能删除超级管理员
        if (SystemConstants.SUPER_ADMIN_USER_ID.equals(userId.longValue())) {
            throw new SystemException(AppHttpCodeEnum.DELETE_SUPER_ADMIN);
        }
        if (!removeById(userId)) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult getUser(Integer id) {
        // 获取User信息
        User user = getById(id);
        UserAdminInfoVo userVo = BeanCopyUtils.copyBean(user, UserAdminInfoVo.class);
        // 获取所有可用Role
        List<RoleListVo> allRoles = roleService.getAllStatusNormalRoles();
        // 获取User对应Role
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        // 封装
        UserRoleListVo userRoleListVo = new UserRoleListVo(userVo, allRoles, roleIds);
        return ResponseResult.okResult(userRoleListVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserUpdateVo userUpdateVo) {
        Long userId = userUpdateVo.getId();
        // 不能修改超级管理员
        if (SystemConstants.SUPER_ADMIN_USER_ID.equals(userId.longValue())) {
            throw new SystemException(AppHttpCodeEnum.UPDATE_SUPER_ADMIN);
        }
        // 修改User
        User user = BeanCopyUtils.copyBean(userUpdateVo, User.class);
        if (!updateById(user)) {
            throw new SystemException(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        // 修改User-Role关联表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>();
        queryWrapper.eq(UserRole::getUserId, userId);
        userRoleService.remove(queryWrapper);
        List<Long> roleIds = userUpdateVo.getRoleIds();
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> new UserRole(userId, roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(UserStatusDto userStatusDto) {
        User user = BeanCopyUtils.copyBean(userStatusDto, User.class);
        user.setId(userStatusDto.getUserId());  // 手动拷贝
        if (!updateById(user)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        return ResponseResult.okResult();
    }
}

