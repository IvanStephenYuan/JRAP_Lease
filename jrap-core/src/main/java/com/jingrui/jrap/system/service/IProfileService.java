
package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.dto.Profile;
import com.jingrui.jrap.system.dto.ProfileValue;

import java.util.List;

/**
 * 配置文件Service
 *
 * @author frank.li
 * @date 2016/6/9.
 */
public interface IProfileService extends ProxySelf<IProfileService> {

    int LEVEL_USER = 30;
    int LEVEL_ROLE = 20;
    int LEVEL_GLOBAL = 10;

    /**
     * 查找ProfileValue 列表
     * @param value
     * @param page 当前页
     * @param pagesize 页大小
     * @return
     */
    List<ProfileValue> selectLevelValues(ProfileValue value,Long levelId, int page, int pagesize);

    /**
     * 查找Profile 列表
     * @param profile
     * @param page 当前页
     * @param pagesize 页大小
     * @return
     */
    List<Profile> selectProfiles(Profile profile, int page, int pagesize);

    /**
     * 查找ProfileValue 列表
     * @param value
     * @return
     */
    List<ProfileValue> selectProfileValues(ProfileValue value);

    /**
     * 插入头行
     * @param request
     * @param profile
     * @return
     */
    Profile createProfile(IRequest request, @StdWho Profile profile);

    /**
     * 批量删除头行
     * @param request
     * @param Profiles
     * @return
     */
    boolean batchDelete(IRequest request, List<Profile> Profiles);

    /**
     * 批量删除行ProfileValue
     * @param requestContext
     * @param values
     * @return
     */
    boolean batchDeleteValues(IRequest requestContext, List<ProfileValue> values);

    /**
     * 更新头行
     * @param requestContext
     * @param profile
     * @return
     */
    Profile updateProfile(IRequest requestContext, @StdWho Profile profile);

    /**
     * 批量更新头行
     * @param request
     * @param Profiles
     * @return
     */
    List<Profile> batchUpdate(IRequest request, @StdWho List<Profile> Profiles);

    /**
     * 根据配置文件的名字/用户，查找用户在该配置文件下的值. 优先顺序 用户>角色>全局 若当前用户 在 用户、角色、全局三层 均没有值，返回 null
     *
     * @param userId 用戶Id
     * @param profileName 配置文件名字
     * @return 配置文件值
     */
    String getValueByUserIdAndName(Long userId, String profileName);

    /**
     * 根据request和profileName按优先级获取配置文件值.
     * 
     * @param request 请求上下文
     * @param profileName 配置文件
     * @return 配置文件值
     */
    String getProfileValue(IRequest request, String profileName);
}
