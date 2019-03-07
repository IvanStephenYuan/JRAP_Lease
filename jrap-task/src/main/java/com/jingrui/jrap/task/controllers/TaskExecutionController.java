package com.jingrui.jrap.task.controllers;

import com.jingrui.jrap.attachment.UpConstants;
import com.jingrui.jrap.attachment.exception.AttachmentException;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.task.dto.TaskExecution;
import com.jingrui.jrap.task.dto.TaskExecutionDetail;
import com.jingrui.jrap.task.service.ITaskExecutionDetailService;
import com.jingrui.jrap.task.service.ITaskExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 任务执行记录控制器.
 *
 * @author lijian.yin@jingrui.com
 **/

@Controller
@RequestMapping({"/sys/task/execution", "api/sys/task/execution"})
public class TaskExecutionController extends BaseController {

    /**
     * buffer 大小.
     */
    private static final Integer BUFFER_SIZE = 1024;

    /**
     * 文件流编码
     */
    private static final String ENC = "UTF-8";

    @Autowired
    private ITaskExecutionService iTaskExecutionService;

    @Autowired
    private ITaskExecutionDetailService taskExecutionDetailService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(TaskExecution dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        boolean isAdmin = Boolean.valueOf(request.getParameter("isAdmin"));
        return new ResponseData(iTaskExecutionService.queryExecutions(iRequest, dto, isAdmin, page, pageSize));
    }

    @GetMapping(value = "/admin/task_execution.html")
    public ModelAndView adminQuery(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(getViewPath() + "/task/task_execution");
        mv.addObject("isAdmin", true);
        return mv;
    }

    @PostMapping(value = "/selectExecutionGroup")
    @ResponseBody
    public ResponseData queryExecutionGroup(@RequestBody TaskExecution taskExecution, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(iTaskExecutionService.queryExecutionGroup(iRequest, taskExecution));
    }

    @PostMapping(value = "/detail")
    @ResponseBody
    public ResponseData detailByEId(@RequestBody TaskExecution taskExecution, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(iTaskExecutionService.queryExecutionDetail(iRequest, taskExecution));
    }

    @PostMapping(value = "/getExecutionLog")
    @ResponseBody
    public ResponseData getExecutionLog(@RequestBody TaskExecutionDetail dto, HttpServletRequest request) {
        return new ResponseData(Arrays.asList(taskExecutionDetailService.getExecutionLog(dto)));
    }

    @PostMapping(value = "/cancleExecute")
    @ResponseBody
    public ResponseData cancleExecute(@RequestBody TaskExecution dto, HttpServletRequest request) {
        boolean result = iTaskExecutionService.cancelExecute(dto);
        return new ResponseData(result);
    }

    @GetMapping(value = "/detailDownload")
    @ResponseBody
    public void detailDownload(Long executionId, HttpServletRequest request, HttpServletResponse response) {
        IRequest iRequest = createRequestContext(request);
        TaskExecution dto = new TaskExecution();
        dto.setExecutionId(executionId);
        List<TaskExecution> list = iTaskExecutionService.queryExecutionDetail(iRequest, dto);
        if (list != null) {
            TaskExecution taskExecution = list.get(0);
            //执行详情文件名
            String fileName = taskExecution.getExecutionNumber() + ".log" ;
            if (taskExecution.getParentId() != null){
                dto.setExecutionId(taskExecution.getParentId());
                List<TaskExecution> parentExecution = iTaskExecutionService.queryExecutionDetail(iRequest, dto);
                if (parentExecution != null){
                    fileName = parentExecution.get(0).getExecutionNumber()+"-"+taskExecution.getExecutionOrder() + ".log";
                    taskExecution.setExecutionNumber(parentExecution.get(0).getExecutionNumber());
                    taskExecution.setExecutionDescription(parentExecution.get(0).getExecutionDescription());
                    taskExecution.setLastExecuteDate(parentExecution.get(0).getLastExecuteDate());
                }
            }
            try {
                String content = iTaskExecutionService.generateString(taskExecution);
                response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, ENC) + "\"");
                response.setContentType("text/plain;charset=" + ENC);
                response.setHeader("Accept-Ranges", "bytes");
                writeFileToResp(response, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/resultDownload")
    @ResponseBody
    public void resultDownload(Long executionId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        IRequest iRequest = createRequestContext(request);
        TaskExecution execution = new TaskExecution();
        execution.setExecutionId(executionId);
        execution = iTaskExecutionService.selectByPrimaryKey(iRequest, execution);
        File file = new File(execution.getExecuteResultPath());
        Properties props = System.getProperties();
        String fileSep = props.getProperty("file.separator");
        String fileName = execution.getExecuteResultPath().substring(execution.getExecuteResultPath().lastIndexOf(fileSep)+1);
        if (file.exists()) {
            response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, ENC) + "\"");
            response.setContentType("application/octet-stream;charset=" + ENC);
            response.setHeader("Accept-Ranges", "bytes");
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            if (fileLength > 0) {
                writeFileToResp(response, file);
            }
        } else {
            throw new AttachmentException(UpConstants.ERROR_DOWNLOAD_FILE_ERROR,UpConstants.ERROR_DOWNLOAD_FILE_ERROR,new Object[0]);
        }
    }

    /**
     * 将文件对象的流写入Responsne对象.
     *
     * @param response HttpServletResponse
     * @param content  string/file
     * @throws FileNotFoundException 找不到文件异常
     * @throws IOException           IO异常
     */
    private void writeFileToResp(HttpServletResponse response, Object content){
        byte[] buf = new byte[BUFFER_SIZE];
        InputStream inStream = null;
        ServletOutputStream outputStream = null;
        try {
            if (content.getClass() == String.class){
                inStream = new ByteArrayInputStream(content.toString().getBytes());
            }else if(content.getClass() == File.class){
                inStream = new FileInputStream((File)content);
            }
            outputStream = response.getOutputStream();
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                outputStream.write(buf, 0, readLength);
            }
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (inStream != null){
                    inStream.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



}