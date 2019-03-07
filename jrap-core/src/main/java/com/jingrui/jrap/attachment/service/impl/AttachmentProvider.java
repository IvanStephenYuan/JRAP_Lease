package com.jingrui.jrap.attachment.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import com.jingrui.jrap.attachment.UpConstants;
import com.jingrui.jrap.attachment.exception.AttachmentException;
import com.jingrui.jrap.core.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.jingrui.jrap.attachment.dto.AttachCategory;
import com.jingrui.jrap.attachment.dto.SysFile;
import com.jingrui.jrap.attachment.service.IAttachCategoryService;
import com.jingrui.jrap.attachment.service.IAttachmentProvider;
import com.jingrui.jrap.attachment.service.ISysFileService;
import com.jingrui.jrap.core.impl.RequestHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author  jinqin.ma@jingrui.com
 */
@Component
public class AttachmentProvider implements IAttachmentProvider {

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IAttachCategoryService attachCategoryService;

    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 生成UUID
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param sourceType
     * 附件来源类型
     * @param sourceKey
     * 附件索引
     * @param locale
     * @param contextPath
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String getAttachListHtml(String sourceType, String sourceKey, Locale locale, String contextPath) throws Exception {
        return getAttachHtml(sourceType, sourceKey, locale,contextPath,true,true);
    }

    /**
     *
     * @param sourceType
     * @param sourceKey
     * @param locale
     * @param contextPath
     * @param enableRemove
     * 是否允许删除
     * @param enableUpload
     * 是否允许上传
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @Override
    public String getAttachHtml(String sourceType, String sourceKey, Locale locale, String contextPath, boolean enableRemove, boolean enableUpload) throws IOException, TemplateException {
        List<SysFile> files = sysFileService.queryFilesByTypeAndKey(RequestHelper.newEmptyRequest(), sourceType,sourceKey);
        AttachCategory category = attachCategoryService.selectAttachByCode(RequestHelper.newEmptyRequest(), sourceType);
        String html = "";
        if(category != null) {
            Template template = getConfiguration().getTemplate("Upload.ftl");

            files.forEach(f->{
                f.setFileSizeDesc(FormatUtil.formatFileSize(f.getFileSize()));
            });


            try (StringWriter out = new StringWriter()) {
                Map<String, Object> param = new HashMap<>();
                param.put("file", files);
                param.put("fid", uuid());
                param.put("enableRemove", enableRemove);
                param.put("enableUpload", enableUpload);
                param.put("sourceType", sourceType);
                param.put("sourceKey", sourceKey);
                param.put("type", category.getAllowedFileType());
                param.put("size", category.getAllowedFileSize());
                param.put("unique", category.getIsUnique());
                param.put("filename", messageSource.getMessage("sysfile.filename", null, locale));
                param.put("filetype", messageSource.getMessage("sysfile.filetype", null, locale));
                param.put("filesize", messageSource.getMessage("sysfile.filesize", null, locale));
                param.put("upload", messageSource.getMessage("sysfile.uploaddate", null, locale));
                param.put("delete", messageSource.getMessage("jrap.delete", null, locale));
                param.put("contextPath", contextPath);
                template.process(param, out);
                out.flush();
                html = out.toString();

            }
        } else {
            return messageSource.getMessage(UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, new String[]{sourceType}, locale);
        }
        return html;
    }

}
