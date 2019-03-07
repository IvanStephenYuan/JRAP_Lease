package com.jingrui.jrap.system.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.cache.impl.HotkeyCache;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.ILanguageProvider;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.DefaultTlTableNameProvider;
import com.jingrui.jrap.core.util.RequestUtil;
import com.jingrui.jrap.message.components.DefaultPromptListener;
import com.jingrui.jrap.mybatis.entity.EntityField;
import com.jingrui.jrap.system.dto.*;
import com.jingrui.jrap.system.mapper.MultiLanguageMapper;
import com.jingrui.jrap.system.service.ICodeService;
import com.jingrui.jrap.system.service.ILovService;
import com.jingrui.jrap.system.service.IProfileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 通用的 Controller,用来获取公共数据.
 *
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
@RestController
public class CommonController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private MultiLanguageMapper multiLanguageMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private DefaultPromptListener promptListener;

    @Autowired
    private ILanguageProvider languageProvider;

    @Autowired
    private ILovService commonLovService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private ICodeService codeService;

    public static final String PROMPTS = "prompts";
    public static final String LANGUAGE = "language";
    public static final String HOTKEY = "hotkey";
    public static final String CODE = "code";
    public static final String CTRL = "Ctrl";
    public static final String ALT = "Alt";
    public static final String SHIFT = "Shift";

    /**
     * 获取通用数据.
     *
     * @param resource 资源类型
     * @param params   参数
     * @param request  HttpServletRequest
     * @return json值
     * @throws JsonProcessingException 对象转 JSON 可能出现的异常
     */
    @RequestMapping(value = "/common/{resource}", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getCommonData(@PathVariable String resource, @RequestParam Map<String, String> params,
                                HttpServletRequest request) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        if (PROMPTS.equalsIgnoreCase(resource)) {
            Locale locale = RequestContextUtils.getLocale(request);
            String lang = locale == null ? null : locale.toString();
            return getCommonPrompts(lang);
        } else if (CODE.equalsIgnoreCase(resource)) {
            params.forEach((k, v) -> {
                List<CodeValue> enabledCodeValues = codeService.getCodeValuesByCode(createRequestContext(request), v);
                try {
                    if (enabledCodeValues == null) {
                        toJson(sb, k, Collections.emptyList());
                    } else {
                        toJson(sb, k, enabledCodeValues);
                    }
                    sb.append("\n");
                } catch (JsonProcessingException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
        } else if (LANGUAGE.equalsIgnoreCase(resource)) {
            List<?> data = languageProvider.getSupportedLanguages();
            String var = params.get("var");
            toJson(sb, var, data);
        } else if (HOTKEY.equalsIgnoreCase(resource)) {
            IRequest iRequest = createRequestContext(request);
            Cache<?> cache = cacheManager.getCache(resource);
            List<HotkeyData> data = getCommonHotkeys(iRequest, (HotkeyCache) cache);
            toJson(sb, "hotKeys", data);
        }
        return sb.toString();
    }


    /**
     * 处理多语言字段.
     *
     * @param request HttpServletRequest
     * @param id      主键值
     * @param dto     dto全名
     * @param field   多语言字段名称(dto中的属性名)
     * @return 视图
     */
    @RequestMapping(value = "sys/sys_multilanguage_editor.html")
    public ModelAndView loadMultiLanguageFields(HttpServletRequest request, @RequestParam String id,
                                                @RequestParam String dto, @RequestParam String field) {
        ModelAndView view = new ModelAndView(getViewPath() + "/sys/sys_multilanguage_editor");
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(dto) && StringUtils.isNotEmpty(field)) {
            Class<?> clazz;
            try {
                clazz = Class.forName(dto);
                Table table = clazz.getAnnotation(Table.class);
                EntityField idField = DTOClassInfo.getIdFields(clazz)[0];
                EntityField tlField = DTOClassInfo.getEntityField(clazz, field);
                if (table != null && idField != null && tlField != null) {
                    Map<String, String> map = new HashMap<>(4);
                    map.put("table", DefaultTlTableNameProvider.getInstance().getTlTableName(table.name()));
                    map.put("idName", DTOClassInfo.getColumnName(idField));
                    map.put("tlName", DTOClassInfo.getColumnName(tlField));
                    map.put("id", id);
                    List list = multiLanguageMapper.select(map);
                    view.addObject("list", list);
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            List<Language> list = languageProvider.getSupportedLanguages();
            list.sort((a, b) -> a.getLangCode().compareTo(b.getLangCode()));
            view.addObject("list", list);
        }
        return view;
    }

    /**
     * 处理多语言字段.
     *
     * @param request HttpServletRequest
     * @param id      主键值
     * @param dto     dto全名
     * @param field   多语言字段名称(dto中的属性名)
     * @return Map
     */
    @RequestMapping(value = "/sys/multiLanguage", method = RequestMethod.GET)
    public Map<String, Object> loadMultiLanguageFields2(HttpServletRequest request, @RequestParam String id,
                                                        @RequestParam String dto, @RequestParam String field) {
        Map<String, Object> result = new HashMap<>(1);
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(dto) && StringUtils.isNotEmpty(field)) {
            Class<?> clazz;
            try {
                clazz = Class.forName(dto);
                Table table = clazz.getAnnotation(Table.class);
                EntityField idField = DTOClassInfo.getIdFields(clazz)[0];
                EntityField tlField = DTOClassInfo.getEntityField(clazz, field);
                if (table != null && idField != null && tlField != null) {
                    Map<String, String> map = new HashMap<>(4);
                    map.put("table", DefaultTlTableNameProvider.getInstance().getTlTableName(table.name()));
                    map.put("idName", DTOClassInfo.getColumnName(idField));
                    map.put("tlName", DTOClassInfo.getColumnName(tlField));
                    map.put("id", id);
                    result.put("multiLanguages", multiLanguageMapper.select(map));
                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            List<Language> list = languageProvider.getSupportedLanguages();
            list.sort((a, b) -> a.getLangCode().compareTo(b.getLangCode()));
            result.put("multiLanguages", list);
        }
        return result;
    }

    /**
     * 获取配置文件.
     *
     * @param params  参数
     * @param request HttpServletRequest
     * @return json值
     */
    @RequestMapping(value = "/common/profile", produces = "application/javascript;charset=utf8")
    @ResponseBody
    public String getProfile(@RequestParam Map<String, String> params, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        StringBuilder sb = new StringBuilder();
        params.forEach((k, v) -> {
            String value = profileService.getProfileValue(requestContext, v);
            try {
                toJson(sb, k, value);
                sb.append("\n");
            } catch (JsonProcessingException e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

        return sb.toString();
    }

    /**
     * 请求超时
     *
     * @param request  request
     * @param response response
     * @return 超时信息
     */
    @RequestMapping(value = "/timeout")
    public Object sessionTimeout(HttpServletRequest request, HttpServletResponse response) {
        if (RequestUtil.isAjaxRequest(request)) {
            ResponseData res = new ResponseData(false);
            res.setCode(BaseConstants.ERROR_CODE_SESSION_TIMEOUT);
            return res;
        } else {
            return new ModelAndView("timeout");
        }
    }

    /**
     * 取 快码 专用(仅限一个). <p>
     * 仅返回code 的内容(作为数组),没有额外的内容.
     *
     * @param code    快码 code
     * @param request request
     * @return json array
     * @throws JsonProcessingException 对象转 JSON 可能出现的异常
     */
    @RequestMapping(value = "/common/code/{code}/")
    @ResponseBody
    public List<CodeValue> getCommonCode(@PathVariable String code, HttpServletRequest request) throws JsonProcessingException {
        return codeService.getCodeValuesByCode(createRequestContext(request), code);
    }

    /**
     * 取 快码 专用(仅限一个). <p>
     * 仅返回code 的内容(作为数组),没有额外的内容.
     *
     * @param parentCode    父Code
     * @param  value 父CodeValue
     * @param request request
     * @return json array
     * @throws JsonProcessingException 对象转 JSON 可能出现的异常
     */
    @RequestMapping(value = "/common/childCode")
    @ResponseBody
    public List<CodeValue> getCommonChildCode(@RequestParam String parentCode,
                                              @RequestParam String value,
                                              HttpServletRequest request) throws JsonProcessingException {
        IRequest requestContext = createRequestContext(request);
        return codeService.getChildCodeValue(requestContext,parentCode,value);
    }

    /**
     * 通用LOV的查询url.
     *
     * @param id       lovId
     * @param page     起始页
     * @param pagesize 分页大小
     * @param params   参数
     * @param request  HttpServletRequest
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/common/lov/{id}")
    @ResponseBody
    public ResponseData getLovDatas(@PathVariable String id, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, @RequestParam Map<String, String> params,
                                    HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(commonLovService.selectDatas(requestContext, id, params, page, pagesize));
    }

    /**
     * 拒绝访问
     *
     * @param request  request
     * @param response response
     * @return
     */
    @RequestMapping(value = "/access-denied")
    public Object accessDenied(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) == null) {
            return sessionTimeout(request, response);
        } else {
            if (RequestUtil.isAjaxRequest(request)) {
                ResponseData res = new ResponseData(false);
                res.setCode(BaseConstants.ERROR_CODE_ACCESS_DENIED);
                return res;
            } else {
                return new ModelAndView("403");
            }
        }
    }

    @RequestMapping(value = "/kendo/export", method = RequestMethod.POST)
    public @ResponseBody
    void save(String fileName, String base64, String contentType, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType(contentType);
        byte[] data = DatatypeConverter.parseBase64Binary(base64);
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
        response.flushBuffer();
    }

    @RequestMapping(value = {"/{folder1}/{name}.html", "/{folder1}/{name}.view"})
    public ModelAndView renderFolder1View(@PathVariable String folder1, @PathVariable String name, Model model) {
        return new ModelAndView(
                new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{folder1}/{folder2}/{name}.html", "/{folder1}/{folder2}/{name}.view"})
    public ModelAndView renderFolder2View(@PathVariable String folder1, @PathVariable String folder2,
                                          @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{folder1}/{folder2}/{folder3}/{name}.html",
            "/{folder1}/{folder2}/{folder3}/{name}.view"})
    public ModelAndView renderFolder3View(@PathVariable String folder1, @PathVariable String folder2,
                                          @PathVariable String folder3, @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(folder3).append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{folder1}/{folder2}/{folder3}/{folder4}/{name}.html",
            "/{folder1}/{folder2}/{folder3}/{folder4}/{name}.view"})
    public ModelAndView renderFolder4View(@PathVariable String folder1, @PathVariable String folder2,
                                          @PathVariable String folder3, @PathVariable String folder4, @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(folder3).append("/").append(folder4).append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{name}.html", "/{name}.view"})
    public ModelAndView renderView(@PathVariable String name, Model model) {
        return new ModelAndView(name);
    }

    /**
     * 封装热键数据
     *
     * @param hotkey
     * @return
     */
    private HotkeyData getHotkeyData(Hotkey hotkey) {
        HotkeyData hotkeyData = new HotkeyData();
        HotkeyValue hotkeyValue = new HotkeyValue();
        String key = hotkey.getHotkey();
        hotkeyData.setCode(hotkey.getCode());
        hotkeyData.setHotkey(hotkeyValue.initValue(key, hotkeyValue));
        return hotkeyData;

    }

    /**
     * 获取热键数据
     *
     * @param iRequest IRequest
     * @param cache    HotkeyCache
     * @return 热键List
     */
    private List<HotkeyData> getCommonHotkeys(IRequest iRequest, HotkeyCache cache) {
        Map<String, HotkeyData> hotkeyDatas = new HashMap<>(2);
        List<HotkeyData> data = new ArrayList<>();
        Hotkey[] hotkeysSys = cache.getValue("system_0");
        Hotkey[] hotkeyUser = cache.getValue("user_" + iRequest.getUserId());
        if (hotkeysSys != null) {
            for (Hotkey hotkey : hotkeysSys) {
                hotkeyDatas.put(hotkey.getCode(), getHotkeyData(hotkey));
            }
        }
        if (hotkeyUser != null) {
            for (Hotkey hotkey : hotkeyUser) {
                HotkeyData value = hotkeyDatas.get(hotkey.getCode());
                if (value != null) {
                    hotkeyDatas.put(hotkey.getCode(), getHotkeyData(hotkey));
                }
            }
        }
        data.addAll(hotkeyDatas.values());
        return data;
    }


    /**
     * 获取Hap开头系统级别的描述
     *
     * @param lang 当前语言
     * @return 描述的字符串
     */
    private String getCommonPrompts(String lang) {
        List<Prompt> list = promptListener.getDefaultPrompt(lang);
        if (list == null) {
            return "//null";
        }
        StringBuilder sb = new StringBuilder();
        for (Prompt prompt : list) {
            sb.append("$l('").append(prompt.getPromptCode().toLowerCase()).append("','").append(prompt.getDescription())
                    .append("');\n");
        }
        sb.append("//").append(list.size());
        return sb.toString();
    }

    /**
     * 基础数据转json格式字符串
     *
     * @param sb
     * @param var
     * @param data
     * @throws JsonProcessingException
     */
    private void toJson(StringBuilder sb, String var, Object data) throws JsonProcessingException {
        boolean hasVar = var != null && var.length() > 0;
        if (hasVar) {
            sb.append("var ").append(var).append('=');
        }
        sb.append(objectMapper.writeValueAsString(data));
        if (hasVar) {
            sb.append(';');
        }
    }

    private class HotkeyValue {
        private boolean altKey = false;

        private boolean shiftKey = false;

        private boolean ctrlKey = false;

        private String keyValue = "";

        public String getKeyValue() {
            return keyValue;
        }

        public void setKeyValue(String keyValue) {
            this.keyValue = keyValue;
        }

        public boolean isAltKey() {
            return altKey;
        }

        public void setAltKey(boolean altKey) {
            this.altKey = altKey;
        }

        public boolean isShiftKey() {
            return shiftKey;
        }

        public void setShiftKey(boolean shiftKey) {
            this.shiftKey = shiftKey;
        }

        public boolean isCtrlKey() {
            return ctrlKey;
        }

        public void setCtrlKey(boolean ctrlKey) {
            this.ctrlKey = ctrlKey;
        }

        public HotkeyValue initValue(String key, HotkeyValue value) {
            if (key.indexOf(CTRL) != -1) {
                value.setCtrlKey(true);
                key = key.replace(CTRL, "");
            }
            if (key.indexOf(ALT) != -1) {
                value.setAltKey(true);
                key = key.replace(ALT, "");
            }
            if (key.indexOf(SHIFT) != -1) {
                value.setShiftKey(true);
                key = key.replace(SHIFT, "");
            }
            key = key.replace("+", "");
            value.setKeyValue(key);
            return value;
        }
    }

    private class HotkeyData {
        private HotkeyValue hotkey;

        private String code;

        public HotkeyValue getHotkey() {
            return hotkey;
        }

        public void setHotkey(HotkeyValue hotkey) {
            this.hotkey = hotkey;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    @PostMapping(value = "/common/generator/uuid")
    public String generatorUUID() {
        return UUID.randomUUID().toString();
    }
}
