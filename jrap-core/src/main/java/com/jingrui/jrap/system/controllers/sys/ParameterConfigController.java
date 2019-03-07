package com.jingrui.jrap.system.controllers.sys;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.cache.impl.LovCache;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.mybatis.util.SqlMapper;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.CodeValue;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.dto.ParameterConfig;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.ICodeService;
import com.jingrui.jrap.system.service.ILovService;
import com.jingrui.jrap.system.service.IParameterConfigService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 对参数配置的操作.
 *
 * @author qiang.zeng
 * @date 2017/11/6
 */
@RestController
@RequestMapping(value = {"/sys/parameter/config", "/api/sys/parameter/config"})
public class ParameterConfigController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ParameterConfigController.class);

    private static final String UPPER_VALUE = "VALUE";
    private static final String LOWER_VALUE = "value";
    private static final String UPPER_TEXT = "TEXT";
    private static final String LOWER_TEXT = "text";

    @Autowired
    private ILovService commonLovService;
    @Autowired
    private LovCache lovCache;
    @Autowired
    private ICodeService codeService;
    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private IParameterConfigService parameterConfigService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(HttpServletRequest request, @RequestParam(required = false) String code, @RequestParam(required = false) Long targetId) {
        IRequest iRequest = RequestHelper.createServiceRequest(request);
        return new ResponseData(parameterConfigService.selectByCodeAndTargetId(code, targetId));
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<ParameterConfig> parameterConfigs, BindingResult result, HttpServletRequest request) {
        getValidator().validate(parameterConfigs, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(parameterConfigService.batchUpdate(requestCtx, parameterConfigs));
    }

    @PostMapping(value = "/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ParameterConfig> parameterConfigs) {
        parameterConfigService.batchDelete(parameterConfigs);
        return new ResponseData();
    }

    @RequestMapping(value = "/getLov")
    public ResponseData getLov(HttpServletRequest request, @RequestParam(required = false) String sourceCode) throws java.io.IOException {
        IRequest requestContext = createRequestContext(request);
        JSONArray lovData;
        Lov lov = lovCache.getValue(sourceCode);
        if (null != lov) {
            String textField = lov.getTextField();
            String valueField = lov.getValueField();
            if (StringUtils.isNotEmpty(lov.getCustomUrl())) {
                Cookie[] cookies = request.getCookies();
                StringBuilder sb = new StringBuilder();
                for (Cookie cookie : cookies) {
                    sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
                }
                String cookieValue = sb.toString();

                CloseableHttpClient httpclient = HttpClients.createDefault();
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                HttpGet httpget = new HttpGet(basePath + lov.getCustomUrl());
                httpget.addHeader(new BasicHeader("Cookie", cookieValue));
                HttpEntity entity;
                CloseableHttpResponse response = httpclient.execute(httpget);
                entity = response.getEntity();
                JSONObject jsonObject = JSONObject.fromObject(EntityUtils.toString(entity));
                String rows = String.valueOf(jsonObject.get("rows"));
                lovData = JSONArray.fromObject(rows);
            } else {
                List data = commonLovService.selectDatas(requestContext, sourceCode, null, 1, 0);
                if (CollectionUtils.isNotEmpty(data) && (data.get(0) instanceof HashMap)) {
                    for (int index = 0; index < data.size(); index++) {
                        Map map = (Map) data.get(index);
                        map.put("valueField", map.get(valueField));
                        map.put("textField", map.get(textField));
                    }
                    return new ResponseData(data);
                }
                lovData = JSONArray.fromObject(data);
            }
            for (int i = 0; i < lovData.size(); i++) {
                JSONObject jsonObject = (JSONObject) (lovData.get(i));
                jsonObject.put("valueField", jsonObject.get(valueField));
                jsonObject.put("textField", jsonObject.get(textField));
                initAttribute(jsonObject);
            }
            return new ResponseData(lovData);
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/getCode")
    public ResponseData getCode(HttpServletRequest request, @RequestParam(required = false) String sourceCode, @RequestParam(required = false) String codeValueField) {
        List<CodeValue> enabledCodeValues = codeService.getCodeValuesByCode(createRequestContext(request), sourceCode);
        JSONArray codeData = new JSONArray();
        if (StringUtils.isEmpty(codeValueField)) {
            codeValueField = "codeValueId";
        }
        if (CollectionUtils.isNotEmpty(enabledCodeValues)) {
            codeData = JSONArray.fromObject(enabledCodeValues);
            int size = codeData.size();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = (JSONObject) (codeData.get(i));
                jsonObject.put("valueField", jsonObject.get(codeValueField));
                jsonObject.put("textField", jsonObject.get("meaning"));
                initAttribute(jsonObject);
            }
        }
        return new ResponseData(codeData);
    }

    @RequestMapping(value = "/queryReportParameter", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryByReportCode(HttpServletRequest request, @RequestParam(required = false) String reportCode) {
        IRequest iRequest = createRequestContext(request);
        List<ParameterConfig> parameterConfigs = parameterConfigService.selectByReportCode(reportCode);
        return parseParameter(iRequest, parameterConfigs);
    }

    @RequestMapping(value = "/checkDefaultValue", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData checkDefaultValue(HttpServletRequest request, @RequestBody String sql) {
        ResponseData responseData = null;
        if (StringUtils.isNotEmpty(sql)) {
            IRequest requestContext = createRequestContext(request);
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                responseData = new ResponseData();
                Map<String, String> result = getValueAndText(requestContext, sqlSession, sql);
                responseData.setMessage("默认Value: " + result.get("defaultValue") + "<br><br>默认Text: " + result.get("defaultText"));
                return responseData;
            } catch (Throwable e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
                responseData = new ResponseData(false);
                responseData.setMessage(e.getMessage());
                return responseData;
            }
        }
        return responseData;
    }

    /**
     * 处理JSONObject值为空的情况.
     *
     * @param jsonObject JSONObject
     */
    private void initAttribute(JSONObject jsonObject) {
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = jsonObject.get(key);
            if (value.equals("null")) {
                jsonObject.replace(key, "");
            }
        }
    }

    private ResponseData parseParameter(IRequest request, List<ParameterConfig> parameterConfigs) {
        if (CollectionUtils.isNotEmpty(parameterConfigs)) {
            for (ParameterConfig parameterConfig : parameterConfigs) {
                if ("sql".equalsIgnoreCase(parameterConfig.getDefaultType()) && StringUtils.isNotEmpty(parameterConfig.getDefaultValue())) {
                    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                        Map<String, String> result = getValueAndText(request, sqlSession, parameterConfig.getDefaultValue());
                        parameterConfig.setDefaultText(result.get("defaultText"));
                        parameterConfig.setDefaultValue(result.get("defaultValue"));
                    } catch (Throwable e) {
                        if (logger.isErrorEnabled()) {
                            logger.error(e.getMessage(), e);
                        }
                        ResponseData responseData = new ResponseData(false);
                        responseData.setMessage("参数[" + parameterConfig.getTableFieldName() + "]  默认值sql出错:<br><br>" + e.getMessage());
                        return responseData;
                    }
                } else if ("currentDate".equalsIgnoreCase(parameterConfig.getDefaultType())) {
                    Date today = new Date();
                    parameterConfig.setDefaultValue(new SimpleDateFormat("yyyy-MM-dd").format(today));
                }
            }
        }
        return new ResponseData(parameterConfigs);
    }

    private Map<String, String> getValueAndText(IRequest request, SqlSession sqlSession, String sql) {
        Map<String, String> result = new HashMap<>(2);
        SqlMapper sqlMapper = new SqlMapper(sqlSession);
        List<HashMap> results = sqlMapper.selectList("<script>\n\t" + sql + "</script>", request, HashMap.class);
        String defaultValue = "";
        String defaultText = "";
        if (results.size() == 1) {
            defaultValue = getValue(results.get(0));
            defaultText = getText(results.get(0));
        } else if (results.size() > 1) {
            for (HashMap map : results) {
                defaultValue += getValue(map) + ",";
            }
        }
        result.put("defaultValue", defaultValue);
        result.put("defaultText", defaultText);
        return result;
    }

    private String getValue(HashMap map) {
        if (null == map.get(UPPER_VALUE) && null == map.get(LOWER_VALUE)) {
            return "";
        }
        if (map.get(UPPER_VALUE) != null) {
            return map.get(UPPER_VALUE).toString();
        }
        if (map.get(LOWER_VALUE) != null) {
            return map.get(LOWER_VALUE).toString();
        }
        return "";
    }

    private String getText(HashMap map) {
        if (null == map.get(UPPER_TEXT) && null == map.get(LOWER_TEXT)) {
            return "";
        }
        if (map.get(UPPER_TEXT) != null) {
            return map.get(UPPER_TEXT).toString();
        }
        if (map.get(LOWER_TEXT) != null) {
            return map.get(LOWER_TEXT).toString();
        }
        return "";
    }

}