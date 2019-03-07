/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.attachment.exception.StoragePathNotExsitException;
import com.jingrui.jrap.attachment.exception.UniqueFileMutiException;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.components.SysConfigManager;
import com.jingrui.jrap.core.exception.TokenException;
import com.jingrui.jrap.core.util.FormatUtil;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.SysConfig;
import com.jingrui.jrap.system.service.ISysConfigService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Locale;

/**
 * 系统配置信息.
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@Controller
@RequestMapping(value ={"/sys/config","/api/sys/config"})
public class SysConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysConfigController.class);

    private static final String SYS_LOGO_FILE_NAME = "logo.png";
    private static final String SYS_FAVICON_FILE_NAME = "favicon.png";

    private static final String MSG_ALERT_UPLOAD_FILE_TYPE_MISMATCH = "jrap.upload.file.type.mismatch";
    private static final String MSG_ALERT_UPLOAD_FILE_SIZE_LIMIT_EXCEEDED = "jrap.upload.file.size.limit.exceeded";
    private static final String MSG_ALERT_UPLOAD_SUCCESS = "jrap.upload.success";
    private static final String MSG_ALERT_UPLOAD_ERROR = "jrap.upload.error";

    @Autowired
    private ISysConfigService configService;


    /**
     * 配置信息查询.
     *
     * @return ResponseData
     */
    @PostMapping(value = "/query")
    @ResponseBody
    public ResponseData getConfig(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(configService.selectAll(iRequest));
    }



    /**
     * 配置信息保存.
     * 
     * @param config
     *            config
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     * @throws TokenException
     */
    @PostMapping(value = "/submit")
    public ResponseData submitConfig(@RequestBody List<SysConfig> config, BindingResult result, HttpServletRequest request) throws TokenException {
        checkToken(request, config);
        getValidator().validate(config, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        for(int i=0;i<config.size();i++)
        {
        	if(config.get(i).getConfigCode().equals("PASSWORD_MIN_LENGTH")){
        		
        		if(Integer.parseInt(config.get(i).getConfigValue())<6){
        			config.get(i).setConfigValue("6");
        		}else if(Integer.parseInt(config.get(i).getConfigValue())>16){
        			config.get(i).setConfigValue("16");
        		}
        	}
        }

        return new ResponseData(configService.batchUpdate(requestCtx, config));
    }


    @RequestMapping(value = "/logo/upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String uploadSystemLogo(HttpServletRequest request) {
        return uploadSystemImage(request, 500, SYS_LOGO_FILE_NAME, SysConfigManager.KEY_SYS_LOGO_VERSION);
    }

    @RequestMapping(value = "/favicon/upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String uploadFaviconLogo(HttpServletRequest request) {
        return uploadSystemImage(request, 100, SYS_FAVICON_FILE_NAME, SysConfigManager.KEY_SYS_FAVICON_VERSION);
    }

    private String uploadSystemImage(HttpServletRequest request, int fileSizeWithKB, String fileName, String type){
        File uploadFolder = new File(request.getServletContext().getRealPath("/") + "/resources/upload");
        Locale locale = RequestContextUtils.getLocale(request);
        long fileSize = fileSizeWithKB *  1024;
        try {
            FileUtils.forceMkdir(uploadFolder);
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(fileSize);
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem fi : items) {
                if (!fi.isFormField()) {
                    String contentType = fi.getContentType();
                    if(StringUtils.contains(contentType,"image/")) {
                        try (InputStream is = fi.getInputStream(); FileOutputStream os = new FileOutputStream(new File(uploadFolder, fileName))) {
                            IOUtils.copy(is, os);
                        }
                        String logoVersion = configService.updateSystemImageVersion(type);
                        WebUtils.setSessionAttribute(request, SysConfigManager.SYS_LOGO_VERSION, logoVersion);

                        return "<script>window.parent.uploadSuccess('"+getMessageSource().getMessage(MSG_ALERT_UPLOAD_SUCCESS, null, locale)+"')</script>";
                    } else {
                        return "<script>window.parent.showUploadMessage('"+getMessageSource().getMessage(MSG_ALERT_UPLOAD_FILE_TYPE_MISMATCH, null, locale)+"')</script>";
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("upload error", e);
            }
            if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
                return "<script>window.parent.showUploadMessage('" + getMessageSource().getMessage(MSG_ALERT_UPLOAD_FILE_SIZE_LIMIT_EXCEEDED, new String[]{FormatUtil.formatFileSize(fileSize)}, locale) + "')</script>";
            }
        }
        return "<script>window.parent.showUploadMessage('"+getMessageSource().getMessage(MSG_ALERT_UPLOAD_ERROR, null, locale)+"')</script>";
    }
}
