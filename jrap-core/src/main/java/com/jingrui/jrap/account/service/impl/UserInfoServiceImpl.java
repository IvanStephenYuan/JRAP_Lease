package com.jingrui.jrap.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.account.constants.UserConstants;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.account.mapper.UserMapper;
import com.jingrui.jrap.account.service.IUserInfoService;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.cache.impl.UserCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.core.util.ValidateUtils;
import com.jingrui.jrap.mybatis.common.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息服务接口 - 实现.
 *
 * @author shengyang.zhou@jingrui.com
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    private final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private static final Long BASE_MENBER_EIGHT = 8L;

    @Autowired
    private IUserService userService;

  /*  @Autowired
    private IMessageService messageService;
*/
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCache userCache;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User create(IRequest request, User user) throws Exception {

        // 调用程序生成随机口令
        String tmpPassword = generateRandomPassword();
        user.setPassword(tmpPassword);
        user = userService.insertSelective(request, user);
        if (user == null) {
            throw new UserException(UserException.USER_INSERT_FAIL, null);
        }
        // 成功创建USER之后
        if (logger.isDebugEnabled()) {
            logger.debug("create user {}", user.toString());
        }
       /* // 调用接口发送短信通知用户
        List<MessageReceiver> receiverlist = new ArrayList<>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<>(3);
        receiver.setMessageAddress(user.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(user.getUserId());
        receiverlist.add(receiver);
        // 设置邮件模板里的数据
        data.put("tmpPassword", tmpPassword);
        // 临时口令有效期为1个小时
        data.put("limit", UserConstants.DEFAULT_PAGE);
        data.put("userName", user.getUserName());
        messageService.sendEmailMessage(-1L, null, UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, "PASSWORD", data,
                receiverlist, null);*/

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User update(IRequest request, User user) throws BaseException {
        userService.validateEmail(user.getEmail());
        userService.validatePhone(user.getPhone());
        user.setUserId(request.getUserId());
        Criteria criteria = new Criteria(user);
        criteria.update(User.FIELD_EMAIL, User.FIELD_PHONE);
        criteria.updateExtensionAttribute();
        userCache.remove(user.getUserName());
        return userService.updateByPrimaryKeyOptions(request, user, criteria);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> getUsers(IRequest request, User user, int page, int pagesize) throws UserException {
        PageHelper.startPage(page, pagesize);
        return userMapper.select(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException {
        User user = new User();
        user.setUserId(userId);
        User checkUser = userService.selectByPrimaryKey(request,user);
        if (checkUser == null) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        if (!UserConstants.USER_STATUS_ACTIVE.equals(checkUser.getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, null);
        }
        return checkUser;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectUserByName(IRequest request, String userName) throws UserException {
        User user = new User();
        user.setUserName(userName);
        User checkUser = userMapper.selectByUserName(userName);
        if (checkUser == null) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        if (!UserConstants.USER_STATUS_ACTIVE.equals(checkUser.getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, null);
        }
        return checkUser;
    }

    /**
     * 用户名校验通用方法.
     *
     * @param request 统一上下文
     * @param user    包含用户名信息
     * @throws UserException 抛出验证用户非空的业务异常
     */
    private List<User> validateUser(IRequest request, User user) throws UserException {
        // 验格式证
        if (!ValidateUtils.validateUserName(user.getUserName())) {
            throw new UserException(UserException.USER_FORMAT, new Object[]{});
        }
        User checkUser = new User();
        checkUser.setUserName(user.getUserName());
        return userMapper.select(checkUser);
    }

    /**
     * 邮箱校验通用方法.
     *
     * @param request 统一上下文
     * @param user    查询依据-email
     * @throws UserException 抛出邮箱格式错误的业务异常
     */
    private List<User> validateEmail(IRequest request, User user) throws UserException {
        // 格式验证
        userService.validateEmail(user.getEmail());
        List<User> list = new ArrayList<User>();
        if (UserConstants.USER_TYPE_INNER.equals(user.getUserType())) {
            User checkUser = new User();
            checkUser.setEmail(user.getEmail());
            list = userMapper.select(checkUser);
        }
        return list;
    }

    /**
     * 手机号码校验通用方法.
     *
     * @param request 统一上下文
     * @param user    查询依据-phone
     * @throws UserException 抛出手机未通过验证的业务异常
     */
    private List<User> validatePhone(IRequest request, User user) throws UserException {
        // 格式验证
        userService.validatePhone(user.getPhone());
        List<User> list = new ArrayList<>();
        if (UserConstants.USER_TYPE_INNER.equals(user.getUserType())) {
            User checkUser = new User();
            checkUser.setPhone(user.getPhone());
            list = userMapper.select(checkUser);
        }
        return list;
    }

    /**
     * 找回用户-生成八位随机密码.
     *
     * @return
     */
    private String generateRandomPassword() {
        // 验证码
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < BASE_MENBER_EIGHT; i++) {
            password.append((int) (Math.random() * UserConstants.NUMBER_9));
        }
        return password.toString();
    }
}
