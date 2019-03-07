package com.jingrui.jrap.task.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.util.SqlMapper;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.controllers.sys.ParameterConfigController;
import com.jingrui.jrap.system.dto.ParameterConfig;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.task.dto.TaskDetail;
import com.jingrui.jrap.task.info.TaskDataInfo;
import com.jingrui.jrap.task.service.ITaskDetailService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 任务/任务组控制器.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@RestController
@RequestMapping(value = {"/sys/task/detail", "/api/sys/task/detail"})
public class TaskDetailController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(TaskDetailController.class);

    private static final String UPPER_VALUE = "VALUE";
    private static final String LOWER_VALUE = "value";
    private static final String UPPER_TEXT = "TEXT";
    private static final String LOWER_TEXT = "text";

    @Autowired
    private ITaskDetailService service;

    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(TaskDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @PostMapping(value = "/submit")
    public ResponseData update(@RequestBody List<TaskDetail> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.batchUpdate(iRequest, dto));
    }

    @PostMapping(value = "/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<TaskDetail> dto) {
        service.remove(dto);
        return new ResponseData();
    }

    @PostMapping(value = "/getById")
    public ResponseData getById(HttpServletRequest request, @RequestBody TaskDetail dto) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(Arrays.asList(service.selectByPrimaryKey(iRequest, dto)));
    }

    @PostMapping(value = "/getGroupById")
    public ResponseData getGroupById(HttpServletRequest request, @RequestBody TaskDetail dto) {
        IRequest iRequest = createRequestContext(request);
        TaskDetail group = service.getGroupById(iRequest, dto);
        return new ResponseData(Arrays.asList(group));
    }

    @PostMapping(value = "/selectUnboundTasks")
    public ResponseData queryUnboundTasks(TaskDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<String> idList = new ArrayList<>();
        if (dto.getIds() != null) {
            idList = Arrays.asList(dto.getIds().split(","));
        }
        return new ResponseData(service.queryUnboundTasks(iRequest, dto, idList, page, pageSize));
    }

    @PostMapping(value = "/executeQuery")
    public ResponseData executeQuery(TaskDetail taskDetail, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.executeQuery(iRequest, taskDetail, page, pageSize));
    }


    @PostMapping(value = "/execute")
    public ResponseData execute(@RequestBody TaskDataInfo taskDataInfo, HttpServletRequest request) throws Exception {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.execute(iRequest, taskDataInfo));
    }

    @PostMapping(value = "/updateChildrenTasks")
    public ResponseData updateChildrenTasks(@RequestBody TaskDetail data, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        service.updateChildrenTasks(iRequest, data);
        return new ResponseData();
    }

    @PostMapping(value = "/detail")
    public ResponseData detailByTId(@RequestBody TaskDetail taskDetail, HttpServletRequest request) throws Exception {
        IRequest iRequest = createRequestContext(request);
        List<TaskDetail> list = service.queryTaskDetail(iRequest, taskDetail);
        for (TaskDetail task:list) {
            parseParameter(iRequest, task.getParameterConfigs());
        }
        return new ResponseData(list);
    }

    private void parseParameter(IRequest request, List<ParameterConfig> parameterConfigs) {
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
                            logger.error("参数[" + parameterConfig.getTableFieldName() + "]  默认值sql出错:<br><br>" + e.getMessage());
                        }
                    }
                } else if ("currentDate".equalsIgnoreCase(parameterConfig.getDefaultType())) {
                    Date today = new Date();
                    parameterConfig.setDefaultValue(new SimpleDateFormat("yyyy-MM-dd").format(today));
                }
            }
        }
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