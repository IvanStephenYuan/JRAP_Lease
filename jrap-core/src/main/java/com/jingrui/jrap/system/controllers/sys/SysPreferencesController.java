/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.ILanguageProvider;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.Language;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.SysPreferences;
import com.jingrui.jrap.system.service.ISysPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统首选项Controller.
 * 
 * @author zhangYang
 *
 */
@Controller
public class SysPreferencesController extends BaseController {

    @Autowired
    private ISysPreferencesService sysPreferencesService;
    
    @Autowired
    private ILanguageProvider languageProvider;


    @RequestMapping(value = "/sys/um/sys_preferences.html", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView sysPreferences(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(getViewPath() + "/sys/um/sys_preferences");
        List<Language> languages = languageProvider.getSupportedLanguages();
        mv.addObject("languages", languages);
        return mv;
    }

    @RequestMapping(value = "/sys/preferences/savePreferences", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData savePreferences(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SysPreferences> sysPreferences, BindingResult result) {
        getValidator().validate(sysPreferences, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        List<SysPreferences> lists = sysPreferencesService.saveSysPreferences(createRequestContext(request), sysPreferences);

        /**
         * 在数据库操作成功后
         * 更新用户session中的首选项信息
         */
        for(SysPreferences preference: lists ) {
            if(BaseConstants.PREFERENCE_LOCALE.equalsIgnoreCase(preference.getPreferences())){
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if(localeResolver != null){
                    localeResolver.setLocale(request, response, StringUtils.parseLocaleString(preference.getPreferencesValue()));
                }
            } else if(BaseConstants.PREFERENCE_THEME.equalsIgnoreCase(preference.getPreferences())){
                ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);
                if (themeResolver != null) {
                    themeResolver.setThemeName(request, response, preference.getPreferencesValue());
                }
            } else if(BaseConstants.PREFERENCE_TIME_ZONE.equalsIgnoreCase(preference.getPreferences())){
                WebUtils.setSessionAttribute(request, SessionLocaleResolver.TIME_ZONE_SESSION_ATTRIBUTE_NAME,
                        org.springframework.util.StringUtils.parseTimeZoneString(preference.getPreferencesValue()));
                WebUtils.setSessionAttribute(request, BaseConstants.PREFERENCE_TIME_ZONE, preference.getPreferencesValue());
            }else if(BaseConstants.PREFERENCE_NAV.equalsIgnoreCase(preference.getPreferences())){
                WebUtils.setSessionAttribute(request,BaseConstants.PREFERENCE_NAV,preference.getPreferencesValue());
            }
        }

        return new ResponseData(lists);
    }

    @RequestMapping(value = "/sys/preferences/queryPreferences", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPreferences(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        SysPreferences preference = new SysPreferences();
        preference.setUserId(requestContext.getUserId());
        List<SysPreferences> lists = sysPreferencesService.querySysPreferencesByDb(requestContext, preference);
        return new ResponseData(lists);
    }
}
