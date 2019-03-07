///*
// * #{copyright}#
// */
//package com.jingrui.jrap.system.controllers.sys;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.jingrui.jrap.attachment.exception.UploadException;
//import com.jingrui.jrap.core.BaseConstants;
//import com.jingrui.jrap.system.controllers.BaseController;
//import com.jingrui.jrap.attachment.dto.AttachCategory;
//import com.jingrui.jrap.attachment.exception.StoragePathNotExsitException;
//import com.jingrui.jrap.core.exception.TokenException;
//import com.jingrui.jrap.attachment.exception.UniqueFileMutiException;
//import com.jingrui.jrap.security.TokenUtils;
//import com.jingrui.jrap.attachment.service.IAttachCategoryService;
//import com.jingrui.jrap.attachment.FileInfo;
//import com.jingrui.jrap.attachment.UpConstants;
//import com.jingrui.jrap.attachment.Uploader;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.jingrui.jrap.core.IRequest;
//import com.jingrui.jrap.attachment.dto.Attachment;
//import com.jingrui.jrap.attachment.dto.SysFile;
//import com.jingrui.jrap.attachment.exception.FileReadIOException;
//import com.jingrui.jrap.attachment.service.ISysFileService;
//import com.jingrui.jrap.attachment.UploaderFactory;
//import com.jingrui.jrap.core.util.UploadUtil;
//
//import net.coobird.thumbnailator.Thumbnails;
//
///**
// * 图片上传.
// *
// * @author wangc
// *
// */
//@Controller
//public class ImageController extends BaseController {
//
//    /** 日志记录. **/
//    private static Logger logger = LoggerFactory.getLogger(ImageController.class);
//
//    @Autowired
//    private IAttachCategoryService categoryService;
//    @Autowired
//    private ISysFileService fileService;
//
//    /**
//     * 文件不存在提示.
//     */
//    private static final String FILE_NOT_EXSIT = "file_not_exsit";
//    /**
//     * 提示信息名.
//     */
//    private static final String MESSAGE_NAME = "message";
//    /**
//     * 提示信息 name.
//     **/
//    private static final String INFO_NAME = "info";
//    /**
//     * 附件上传存储目录未分配.
//     **/
//    private static final String TYPEORKEY_EMPTY = "TYPEORKEY_EMPTY";
//    /**
//     * sourceType 错误.
//     */
//    private static final String TYPE_ERROR = "SOURCETYPE_ERROR";
//    /**
//     * 数据库 错误.
//     */
//    private static final String DATABASE_ERROR = "DATABASE_ERROR";
//    /**
//     * 图片压缩 错误.
//     */
//    private static final String COMPRESS_ERROR = "COMPRESS_ERROR";
//    /**
//     * 提示成功.
//     */
//    private static final String MESG_SUCCESS = "success";
//    /**
//     * file对象名.
//     */
//    private static final String FILE_NAME = "file";
//    /**
//     * buffer 大小.
//     */
//    private static final Integer BUFFER_SIZE = 1024;
//
//    /**
//     * 图片分辨率-高.
//     */
//    public static final int H_WIDTH = 480;
//    public static final int H_HEIGHT = 480;
//
//    /**
//     * 图片分辨率-中.
//     */
//    public static final int M_WIDTH = 360;
//    public static final int M_HEIGHT = 360;
//
//    /**
//     * 图片分辨率-低.
//     */
//    public static final int L_WIDTH = 78;
//    public static final int L_HEIGHT = 78;
//
//    /**
//     * 压缩分辨率-高
//     */
//    public static final String COMPRESS_H = "H";
//
//    /**
//     * 压缩分辨率-中
//     */
//    public static final String COMPRESS_M = "M";
//
//    /**
//     * 压缩分辨率-低
//     */
//    public static final String COMPRESS_L = "L";
//
//
//    @RequestMapping(value = "/sys/image/upload", method = RequestMethod.POST)
//    public Map<String, Object> upload(HttpServletRequest request) throws StoragePathNotExsitException, UniqueFileMutiException, UploadException {
//        Map<String, Object> response = new HashMap<>();
//        Uploader uploader = UploaderFactory.getMutiUploader();
//        uploader.init(request);
//        String sourceType = uploader.getParams("sourceType");
//        String sourceKey = uploader.getParams("sourceKey");
//        String isCompress = uploader.getParams("isCompress");
//        if (StringUtils.isBlank(sourceType) || StringUtils.isBlank(sourceKey)) {
//            response.put(MESSAGE_NAME, TYPEORKEY_EMPTY);
//            response.put(INFO_NAME, TYPEORKEY_EMPTY);
//            return response;
//        }
//        IRequest requestContext = createRequestContext(request);
//        AttachCategory category = categoryService.selectAttachByCode(requestContext, sourceType);
//        // 设置上传参数
//        UploadUtil.initUploaderParams(uploader, category);
//        // DbType 设置不对
//        if (category == null) {
//            response.put(MESSAGE_NAME, TYPE_ERROR);
//            response.put(INFO_NAME, TYPE_ERROR);
//            return response;
//        }
//        List<FileInfo> fileInfos = uploader.upload();
////        response.put(MESSAGE_NAME, uploader.getStatus());
//        // 出错了
////        if (!UpConstants.SUCCESS.equals(uploader.getStatus())) {
////            response.put(INFO_NAME, fileInfos.get(0).getStatus());
////            return response;
////        }
//        // WebUploader 每次只上传一个文件
//        FileInfo f = fileInfos.get(0);
//
//        try {
//            SysFile sysFile = UploadUtil.genSysFile(f, requestContext.getUserId(), requestContext.getUserId());
//            Attachment attach = UploadUtil.genAttachment(category, sourceKey, requestContext.getUserId(), requestContext.getUserId());
//            // 分类如果是唯一类型
//            if (BaseConstants.YES.equals(category.getIsUnique())) {
//                sysFile = fileService.updateOrInsertFile(requestContext, attach, sysFile);
//            } else {
//                fileService.insertFileAndAttach(requestContext, attach, sysFile);
//            }
//            sysFile.setFilePath(null);
//
//            TokenUtils.generateAndSetToken(TokenUtils.getSecurityKey(request.getSession(false)), sysFile);
//            response.put(FILE_NAME, sysFile);
//        } catch (Exception e) {
//            if (logger.isDebugEnabled()) {
//                logger.error("database error", e);
//            }
//            File file = f.getFile();
//            if (file.exists()) {
//                file.delete();
//            }
//            response.put(MESSAGE_NAME, DATABASE_ERROR);
//            response.put(INFO_NAME, DATABASE_ERROR);
//        }
//        if (BaseConstants.YES.equals(isCompress)) {
//            try {
//                genCompressImg(f, H_WIDTH, H_HEIGHT);
//                genCompressImg(f, M_WIDTH, M_HEIGHT);
//                genCompressImg(f, L_WIDTH, L_HEIGHT);
//            } catch (FileReadIOException e) {
//                if (logger.isErrorEnabled()) {
//                    logger.error(e.getMessage(), e);
//                }
//                response.put(MESSAGE_NAME, COMPRESS_ERROR);
//                response.put(INFO_NAME, COMPRESS_ERROR);
//            }
//        }
//        return response;
//    }
//
//    /**
//     * 查看某个图片.
//     *
//     * @param request
//     *            请求上下文
//     * @param response
//     *            返回结果
//     * @param fileId
//     *            文件id
//     * @param compressLevel
//     *            压缩级别
//     * @throws FileReadIOException
//     *             IO exception
//     */
//    @RequestMapping(value = "/sys/image/view")
//    public void view(HttpServletRequest request, HttpServletResponse response, String fileId, String compressLevel)
//            throws FileReadIOException {
//        IRequest requestContext = createRequestContext(request);
//        SysFile sysFile = fileService.selectByPrimaryKey(requestContext, Long.valueOf(fileId));
//        try {
//            if (sysFile != null && StringUtils.isNotBlank(sysFile.getFilePath())) {
//                // 在contextpath和path之间多一个File.separator
//                File file = new File(sysFile.getFilePath());
//                if (file.exists()) {
//                    response.setHeader("cache-control", "must-revalidate");
//                    response.setHeader("pragma", "public");
//                    response.setHeader("Content-Type", sysFile.getFileType());
//                    response.setHeader("Accept-Ranges", "bytes");
//                    response.setHeader("Content-disposition",
//                            "attachment;" + processFileName(request, sysFile.getFileName()));
//
//                    String originalPath = file.getPath();
//                    if (COMPRESS_H.equals(compressLevel)) {
//                        String hPath = originalPath + "_" + H_WIDTH + "_" + H_HEIGHT;
//                        File hFile = new File(hPath);
//                        writeFileToResp(response, hFile);
//                    } else if (COMPRESS_M.equals(compressLevel)) {
//                        String mPath = originalPath + "_" + M_WIDTH + "_" + M_HEIGHT;
//                        File mFile = new File(mPath);
//                        writeFileToResp(response, mFile);
//                    } else if (COMPRESS_L.equals(compressLevel)) {
//                        String lPath = originalPath + "_" + L_WIDTH + "_" + L_HEIGHT;
//                        File lFile = new File(lPath);
//                        writeFileToResp(response, lFile);
//                    } else {
//                        writeFileToResp(response, file);
//                    }
//                } else {
//                    response.getWriter().write(FILE_NOT_EXSIT);
//                }
//            } else {
//                response.getWriter().write(FILE_NOT_EXSIT);
//            }
//        } catch (IOException e) {
//            throw new FileReadIOException();
//        }
//    }
//
//    /**
//     * 图片删除.
//     *
//     * @param request
//     *            HttpServletRequest
//     * @param fileId
//     *            文件id
//     * @param token
//     *            token
//     * @return Map 结果对象
//     * @throws TokenException
//     */
//    @RequestMapping(value = "/sys/image/remove")
//    public Map<String, Object> removeImage(HttpServletRequest request, String fileId, String token)
//            throws TokenException {
//        Map<String, Object> response = new HashMap<>();
//        IRequest requestContext = createRequestContext(request);
//        SysFile file = new SysFile();
//        file.setFileId(Long.valueOf(fileId));
//        file.set_token(token);
//        TokenUtils.checkToken(request.getSession(false), file);
//        file = fileService.deleteImage(requestContext, file);
//        response.put(MESSAGE_NAME, MESG_SUCCESS);
//        return response;
//    }
//
//    /**
//     * 压缩图片
//     *
//     * @param f
//     *            图片文件
//     * @param wigth
//     *            宽
//     * @param height
//     *            高
//     * @throws FileReadIOException
//     *             IOException
//     */
//    private void genCompressImg(FileInfo f, int wigth, int height) throws FileReadIOException {
//        File newFile = new File(f.getUrl() + "_" + wigth + "_" + height);
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(newFile);
//            Thumbnails.of(f.getFile()).forceSize(wigth, height).toOutputStream(os);
//        } catch (IOException e) {
//            if (logger.isErrorEnabled()) {
//                logger.error(e.getMessage(), e);
//            }
//            throw new FileReadIOException();
//        } finally {
//            try {
//                os.close();
//            } catch (IOException e) {
//                if (logger.isErrorEnabled()) {
//                    logger.error(e.getMessage(), e);
//                }
//                throw new FileReadIOException();
//            }
//        }
//    }
//
//    /**
//     * 生成文件名
//     *
//     * @param request
//     *            请求上下文
//     * @param filename
//     *            文件名
//     * @return 文件名
//     * @throws UnsupportedEncodingException
//     *             编码异常
//     */
//    private String processFileName(HttpServletRequest request, String filename) throws UnsupportedEncodingException {
//        String userAgent = request.getHeader("User-Agent");
//        String new_filename = URLEncoder.encode(filename, "UTF8");
//        String rtn = "filename=\"" + new_filename + "\"";
//        if (userAgent != null) {
//            userAgent = userAgent.toLowerCase();
//            if (userAgent.indexOf("msie") != -1) {
//                rtn = "filename=\"" + new String(filename.getBytes("GB2312"), "ISO-8859-1") + "\"";
//            } else if (userAgent.indexOf("safari") != -1 || userAgent.indexOf("applewebkit") != -1) {
//                rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
//            } else if (userAgent.indexOf("opera") != -1 || userAgent.indexOf("mozilla") != -1) {
//                rtn = "filename*=UTF-8''" + new_filename;
//            }
//        }
//        return rtn;
//    }
//
//    /**
//     * 将文件对象的流写入Responsne对象.
//     *
//     * @param response
//     *            请求结果
//     * @param file
//     *            文件
//     * @throws FileNotFoundException
//     *             找不到文件异常
//     * @throws IOException
//     *             IO异常
//     */
//    private void writeFileToResp(HttpServletResponse response, File file) throws FileNotFoundException, IOException {
//        byte[] buf = new byte[BUFFER_SIZE];
//        try (InputStream inStream = new FileInputStream(file);
//                ServletOutputStream outputStream = response.getOutputStream()) {
//            int readLength;
//            while (((readLength = inStream.read(buf)) != -1)) {
//                outputStream.write(buf, 0, readLength);
//            }
//            outputStream.flush();
//        }
//    }
//}
