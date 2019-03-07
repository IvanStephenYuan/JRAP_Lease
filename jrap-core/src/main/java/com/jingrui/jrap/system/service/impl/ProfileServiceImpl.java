package com.jingrui.jrap.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.components.GlobalProfileSubscriber;
import com.jingrui.jrap.system.dto.GlobalProfile;
import com.jingrui.jrap.system.dto.Profile;
import com.jingrui.jrap.system.dto.ProfileValue;
import com.jingrui.jrap.system.mapper.ProfileMapper;
import com.jingrui.jrap.system.mapper.ProfileValueMapper;
import com.jingrui.jrap.system.service.IProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 配置文件ServiceImpl
 *
 * @author frank.li
 * @date 2016/6/9.
 */
@Service
public class ProfileServiceImpl extends BaseServiceImpl<Profile> implements IProfileService {

    private static ProfileValue GLOBAL = new ProfileValue();

    private Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileValueMapper profileValueMapper;

    @Autowired
    private IMessagePublisher messagePublisher;

    static {
        GLOBAL.setLevelId(10L);
        GLOBAL.setLevelName("GLOBAL");
        GLOBAL.setLevelValue("GLOBAL");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Profile> selectProfiles(Profile profile, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return profileMapper.select(profile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Profile createProfile(IRequest request, @StdWho Profile profile) {
        // 插入头行
        profileMapper.insertSelective(profile);
        // 判断如果行不为空，则迭代循环插入
        if (profile.getProfileValues() != null) {
            processProfileValues(profile);
        }
        return profile;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ProfileValue> selectProfileValues(ProfileValue value) {
        return profileValueMapper.selectProfileValues(value);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDelete(IRequest request, List<Profile> profiles) {
        // 删除头
        for (Profile profile : profiles) {
            ProfileValue profileValue = new ProfileValue();
            profileValue.setProfileId(profile.getProfileId());
            // delete profile value;
            int count = profileValueMapper.deleteByProfileId(profileValue);
            checkOvn(count, profileValue);
            // delete profile;
            int countH = profileMapper.deleteByPrimaryKey(profile);
            checkOvn(countH, profile);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteValues(IRequest requestContext, List<ProfileValue> values) {
        for (ProfileValue value : values) {
            int count = profileValueMapper.deleteByPrimaryKey(value);
            checkOvn(count, value);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Profile updateProfile(IRequest request, @StdWho Profile profile) {
        int count = profileMapper.updateByPrimaryKeySelective(profile);
        checkOvn(count, profile);
        // 判断如果行不为空，则迭代循环插入
        if (profile.getProfileValues() != null) {
            processProfileValues(profile);
        }
        return profile;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Profile> batchUpdate(IRequest request, @StdWho List<Profile> profiles) {
        for (Profile profile : profiles) {
            if (profile.getProfileId() == null) {
                self().createProfile(request, profile);
            } else if (profile.getProfileId() != null) {
                self().updateProfile(request, profile);
            }
        }
        return profiles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getValueByUserIdAndName(Long userId, String profileName) {
        Profile profile = profileMapper.selectByName(profileName);
        if (profile == null) {
            return null;
        }
        Long profileId = profile.getProfileId();
        List<ProfileValue> profileValues = profileValueMapper.selectByProfileIdAndUserId(profileId, userId);
        if (profileValues != null && profileValues.size() > 0) {
            return profileValues.get(0).getProfileValue();
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ProfileValue> selectLevelValues(ProfileValue value, Long levelId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("levelId:{}", levelId);
        }
        // Map map;
        value.getLevelId();
        if (levelId == null) {
            return null;
        } else if (levelId == LEVEL_USER) {
            return profileValueMapper.selectLevelValuesForUser(value);
        } else if (levelId == LEVEL_ROLE) {
            return profileValueMapper.selectLevelValuesForRole(value);
        } else if (levelId == LEVEL_GLOBAL) {
            return Arrays.asList(GLOBAL);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getProfileValue(IRequest request, String profileName) {
        List<ProfileValue> profileValues = profileValueMapper.selectPriorityValues(profileName);
        // 如果不为空，返回优先级最高的第一条记录值
        if (profileValues != null && profileValues.size() > 0) {
            return profileValues.get(0).getProfileValue();
        }
        return null;
    }

    /**
     * 批量操作快码行数据.
     *
     * @param profile 头行数据
     */
    private void processProfileValues(Profile profile) {
        for (ProfileValue profileValue : profile.getProfileValues()) {
            if (profileValue.getProfileValueId() == null) {
                // 设置头ID跟行ID一致
                profileValue.setProfileId(profile.getProfileId());
                profileValueMapper.insertSelective((profileValue));
            } else if (profileValue.getProfileValueId() != null) {
                int count = profileValueMapper.updateByPrimaryKeySelective(profileValue);
                checkOvn(count, profileValue);
            }
            if (IProfileService.LEVEL_GLOBAL == profileValue.getLevelId()) {
                // 当更改 global 级别的配置文件时, 发出一个消息, 通知其他监听者更新
                messagePublisher.publish(GlobalProfileSubscriber.PROFILE,
                        new GlobalProfile(profile.getProfileName(), profileValue.getProfileValue()));
            }
        }
    }

}