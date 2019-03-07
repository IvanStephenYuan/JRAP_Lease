package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.Profile;
import com.jingrui.jrap.system.dto.ProfileValue;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 配置文件Controller
 *
 * @author frank.li
 * @date 2016/6/9.
 */
@RestController
@RequestMapping(value = {"/sys", "/api/sys"})
public class ProfileController extends BaseController {

    @Autowired
    private IProfileService profileService;

    @RequestMapping(value = "/profile/query", method = {RequestMethod.GET, RequestMethod.POST} )
    public ResponseData query(Profile profile, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(profileService.selectProfiles(profile, page, pagesize));
    }

    @RequestMapping(value = "/profilevalue/query", method = {RequestMethod.GET, RequestMethod.POST} )
    public ResponseData queryProfileValues(ProfileValue value) {
        return new ResponseData(profileService.selectProfileValues(value));
    }

    @RequestMapping(value = "/profilevalue/querylevelvalues", method = {RequestMethod.GET, RequestMethod.POST} )
    public ResponseData queryLevelValues(ProfileValue value,Long levelId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(profileService.selectLevelValues(value,levelId, page, pagesize));
    }

    @PostMapping(value = "/profile/submit")
    public ResponseData submit(@RequestBody List<Profile> profiles, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(profiles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(profileService.batchUpdate(requestContext, profiles));
    }

    @PostMapping(value = "/profile/remove")
    public ResponseData remove(@RequestBody List<Profile> profiles, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        profileService.batchDelete(requestContext, profiles);
        return new ResponseData();
    }

    @PostMapping(value = "/profilevalue/remove")
    public ResponseData removeProfileValues(@RequestBody List<ProfileValue> profileValues, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        profileService.batchDeleteValues(requestContext, profileValues);
        return new ResponseData();
    }
}
