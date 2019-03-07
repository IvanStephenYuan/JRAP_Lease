/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.controllers;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jingrui.jrap.core.exception.IBaseException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.ognl.OgnlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.core.exception.TokenException;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.util.RequestUtil;
import com.jingrui.jrap.core.validator.FieldErrorWithBean;
import com.jingrui.jrap.security.DefaultConfiguration;
import com.jingrui.jrap.security.TokenUtils;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.ResponseData;

/**
 * BaseController.
 *
 * @author njq.niu@jingrui.com
 *         <p>
 *         2016年1月5日
 */
@RestController
public class BaseController {

    protected static final String DEFAULT_PAGE = "1";
    protected static final String DEFAULT_PAGE_SIZE = "10";

    protected static final String SYS_VALIDATION_PREFIX = "jrap.validation.";

    protected static final String DEFAULT_VIEW_HOME = "";

    @Autowired
    private DefaultConfiguration configuration;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Validator validator;

    protected String getViewPath() {
        if (configuration != null) {
            return configuration.getDefaultViewPath();
        }
        return DEFAULT_VIEW_HOME;
    }

    protected Validator getValidator() {
        return validator;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder, HttpServletRequest request) {
//        Object target = dataBinder.getTarget();
//        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    protected IRequest createRequestContext(HttpServletRequest request) {
        return RequestHelper.createServiceRequest(request);
    }

    /**
     * token validation.
     * <p>
     * {@link #checkToken(HttpSession, Collection)}
     * 
     * @param request
     *            http servlet request
     * @param dtos
     *            dto collection
     * @throws TokenException
     *             token failed
     */
    protected void checkToken(HttpServletRequest request, Collection<? extends BaseDTO> dtos) throws TokenException {
        if (request != null) {
            checkToken(request.getSession(false), dtos);
        }
    }

    /**
     * token validation.
     * 
     * @param session
     *            http session
     * @param dtos
     *            dto collection
     * @throws TokenException
     *             token failed
     */
    protected void checkToken(HttpSession session, Collection<? extends BaseDTO> dtos) throws TokenException {
        TokenUtils.checkToken(session, dtos);
    }

    /**
     * token validation.
     * <p>
     * {@link #checkToken(HttpSession, BaseDTO)}
     *
     * @param request
     *            http servlet request
     * @param baseDTO
     *            dto
     * @throws TokenException
     *             token failed
     */
    protected void checkToken(HttpServletRequest request, BaseDTO baseDTO) throws TokenException {
        if (request != null) {
            checkToken(request.getSession(false), baseDTO);
        }
    }

    /**
     * token validation.
     *
     * @param session
     *            http session
     * @param baseDTO
     *            dto
     * @throws TokenException
     *             token failed
     */
    protected void checkToken(HttpSession session, BaseDTO baseDTO) throws TokenException {
        TokenUtils.checkToken(session, baseDTO);
    }

    /**
     * 处理控制层所有异常.
     *
     * @param exception
     *            未捕获的异常
     * @param request
     *            HttpServletRequest
     * @return ResponseData(BaseException 被处理) 或者 ModelAndView(其他 Exception
     *         ,500错误)
     */
    @ExceptionHandler(value = { Exception.class })
    public Object exceptionHandler(Exception exception, HttpServletRequest request) {
        logger.error(exception.getMessage(), exception);
        Throwable thr = getRootCause(exception);
        if (RequestUtil.isAjaxRequest(request) || RequestUtil.isAPIRequest(request) || ServletFileUpload.isMultipartContent(request)) {
            ResponseData res = new ResponseData(false);
            if (thr instanceof IBaseException) {
                IBaseException be = (IBaseException) thr;
                Locale locale = RequestContextUtils.getLocale(request);
                String messageKey = be.getDescriptionKey();
                String message = messageSource.getMessage(messageKey, be.getParameters(), messageKey, locale);
                res.setCode(be.getCode());
                res.setMessage(message);
            } else {
                res.setMessage(thr.toString());
            }
            return res;
        } else {
            ModelAndView view = new ModelAndView("500");
            if (thr instanceof IBaseException) {
                IBaseException be = (IBaseException) thr;
                Locale locale = RequestContextUtils.getLocale(request);
                String messageKey = be.getDescriptionKey();
                String message = messageSource.getMessage(messageKey, be.getParameters(), messageKey, locale);
                view.addObject("message", message);
            }
            return view;
        }
    }

    /**
     * 标准校验,错误消息获取.
     * 
     * @param errors
     *            包含错误的对象
     * @param request
     *            HttpServletRequest
     * @return 经过翻译的错误消息
     */
    protected String getErrorMessage(Errors errors, HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        String errorMessage = null;
        for (ObjectError error : errors.getAllErrors()) {
            if (error.getDefaultMessage() != null) {
                if (error instanceof FieldErrorWithBean) {
                    errorMessage = getStandardFieldErrorMessage((FieldErrorWithBean) error, locale);
                    break;
                }
                errorMessage = messageSource.getMessage(error.getDefaultMessage(), null, locale);
                break;
            } else {
                errorMessage = error.getCode();
            }
        }
        return errorMessage;
    }

    /**
     * translate code to message.
     * 
     * @param request
     *            http servlet request
     * @param code
     *            code
     * @param args
     *            params used in message
     * @return translated message or original code
     */
    protected String nls(HttpServletRequest request, String code, Object[] args) {
        Locale locale = RequestContextUtils.getLocale(request);
        return messageSource.getMessage(code, args, code, locale);
    }

    /**
     * translate code to message.
     * 
     * @param request
     *            http servlet request
     * @param code
     *            code
     * @return translated message or original code
     */
    protected String nls(HttpServletRequest request, String code) {
        Locale locale = RequestContextUtils.getLocale(request);
        return messageSource.getMessage(code, null, code, locale);
    }

    /**
     * 取得字段校验的标准错误消息.
     * <p>
     * 诸如 NotEmpty 之类的标准错误,可以同过次方法取得错误消息
     *
     * @param fieldError
     *            可以取到 targetBean
     * @param locale
     *            当前语言环境
     * @return 与当前语言环境相符的错误描述
     */
    protected String getStandardFieldErrorMessage(FieldErrorWithBean fieldError, Locale locale) {
        String field = fieldError.getField();
        Class clazz = fieldError.getTargetBean().getClass();
        clazz = findDeclareClass(clazz, field);
        String fieldPromptMessageKey = clazz.getSimpleName() + "." + field;
        String fieldPrompt = messageSource.getMessage(fieldPromptMessageKey.toLowerCase(), null, locale);

        String code = SYS_VALIDATION_PREFIX + fieldError.getCode().toLowerCase();
        String msg = messageSource.getMessage(code, new Object[] { fieldPrompt }, fieldError.getDefaultMessage(), locale);
        if(code.equalsIgnoreCase(msg) && fieldError.getDefaultMessage() != null){
            msg =  fieldPrompt + " : " + fieldError.getDefaultMessage();
        }
        return msg;
    }

    /**
     * 找到是哪一个父类定义了属性.
     * 
     * @param fromClass
     *            从哪一个类开始查找
     * @param fieldName
     *            属性名
     * @return 定义了指定属性的类,找不到的话,返回fromClass
     */
    private Class findDeclareClass(Class fromClass, String fieldName) {
        Class clazz = fromClass;
        while (clazz.getSuperclass() != null) {
            try {
                clazz.getDeclaredField(fieldName);
                return clazz;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return fromClass;
    }

    private Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof OgnlException && ((OgnlException) throwable).getReason() != null) {
            return getRootCause(((OgnlException) throwable).getReason());
        }
        return throwable;
    }

    /**
     * 返回用户ID.
     *
     * @param request
     *            HttpServletRequest
     * @return userId
     */
    protected Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Long) session.getAttribute(User.FIELD_USER_ID);
    }

    /**
     * 返回用户角色ID.
     *
     * @param request
     *            HttpServletRequest
     * @return roleId
     */
    protected Long getRoleId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Long) session.getAttribute(Role.FIELD_ROLE_ID);

    }

    /**
     * 返回当前语言编码.
     *
     * @param request
     *            HttpServletRequest
     * @return locale
     */
    protected String getLanguage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(IRequest.FIELD_LOCALE);
    }

}
