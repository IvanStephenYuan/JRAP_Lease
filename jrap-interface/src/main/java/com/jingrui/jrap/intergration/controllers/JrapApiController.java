package com.jingrui.jrap.intergration.controllers;

import com.codahale.metrics.annotation.Timed;
import com.jingrui.jrap.cache.impl.ApiConfigCache;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.components.UserLoginInfoCollection;
import com.jingrui.jrap.intergration.annotation.JrapInbound;
import com.jingrui.jrap.intergration.annotation.JrapOutbound;
import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.intergration.exception.JrapApiException;
import com.jingrui.jrap.intergration.service.IJrapApiService;
import com.jingrui.jrap.intergration.service.IJrapInterfaceHeaderService;
import com.jingrui.jrap.system.controllers.BaseController;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by xiangyuQi on 2016/11/2.
 */
@Controller
public class JrapApiController extends BaseController implements BaseConstants {

    private final Logger logger = LoggerFactory.getLogger(JrapApiController.class);

    private static final int INTERFACE_EXPIRE = 10; //API访问限制.时间周期  单位.秒
    private static final int INTERFACE_NUM = 10; //API访问次数限制.最大访问次数
    private static final Long INTERFACE_INTERVAL = 500L; //API访问.时间间隔内只能访问一次  单位.毫秒

    @Autowired
    IJrapInterfaceHeaderService headerService;

    @Resource(name = "plsqlBean")
    IJrapApiService plsqlService;

    @Resource(name = "restBean")
    IJrapApiService restService;

    @Resource(name = "soapBean")
    IJrapApiService soapService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String redisCatalog = JRAP_CACHE+"interface:";


    @ResponseBody
    @Timed
    @JrapOutbound
    @JrapInbound(apiName = "jrap.invoke.apiname.interfacetranspond")
    @RequestMapping(value = "/r/api", method = RequestMethod.POST)
    public JSONObject sentRequest(HttpServletRequest request,  @RequestBody(required = false)  JSONObject params)
            throws Exception {
        String sysName = request.getParameter("sysName");
        String apiName = request.getParameter("apiName");
        logger.info("sysName:{}  apiName:{} ", sysName, apiName);
        logger.info("requestBody:{}", params);

        JrapInterfaceHeader hapInterfaceHeader = headerService.getHeaderAndLine(sysName, apiName);
        logger.info("return HmsInterfaceHeader:{}", hapInterfaceHeader);

        Map map = new HashMap<String, Object>();
        if (hapInterfaceHeader == null) {
            throw new JrapApiException(JrapApiException.ERROR_NOT_FOUND, "根据sysName和apiName没有找到数据");
        }
        if (!hapInterfaceHeader.getRequestFormat().equals("raw")) {
            throw new JrapApiException(JrapApiException.ERROR_REQUEST_FORMAT, "不支持的请求形式");
        }
        JSONObject json = null;

        /**
         * 注释部分为 API透传访问次数限制 ，功能可用，但不完善，后续在接口服务管理中完善
         */
//        Map<String,String> resultMap = requestLimit(request);   //API透传访问次数限制
//        String result = resultMap.get("result");
//        if (result.equals("success")){

        if (hapInterfaceHeader.getInterfaceType().equals("REST")) {
            json = restService.invoke(hapInterfaceHeader, params);

        } else if (hapInterfaceHeader.getInterfaceType().equals("SOAP")) {
            json = soapService.invoke(hapInterfaceHeader, params);
        } else if (hapInterfaceHeader.getInterfaceType().equals("PLSQL")) {
            json = plsqlService.invoke(hapInterfaceHeader, params);
        } else {
            throw new JrapApiException(JrapApiException.ERROR_INTERFACE_TYPE, "不支持的接口类型");
        }
        return json;

//        }else {
//            return JSONObject.fromObject(resultMap);
//        }

    }


    @ResponseBody
    @RequestMapping("/api/user")
    public Principal user(Principal principal) {
        return principal;
    }


    /**
     * 测试.
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/api/public/xml_test", method= RequestMethod.POST)
    @ResponseBody
    public String jf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletInputStream servletInputStream=request.getInputStream();
        BufferedReader reader= new BufferedReader(new InputStreamReader(servletInputStream,"UTF-8"));
        String crmDataXml= FileCopyUtils.copyToString(reader);
        System.out.println(crmDataXml);
        return crmDataXml;
    }

    /**
     * API请求限制
     * @param request
     * @return
     */
    public Map<String,String> requestLimit(HttpServletRequest request){
        Long lastTime = System.currentTimeMillis(); //获取当前请求时间
        Map<String,String> map = new HashMap<>();//结果返回

        String ip = UserLoginInfoCollection.getIpAddress(request);
        String sysName = request.getParameter("sysName");
        String apiName = request.getParameter("apiName");

        String redisKey = redisCatalog+ip+"_"+sysName+"_"+apiName;

        Object redisLastTime = redisTemplate.opsForHash().get(redisKey,"lastTime");
        if (redisLastTime != null){
            long interval =  lastTime - Long.valueOf(redisLastTime.toString());
            if (interval < INTERFACE_INTERVAL){
                map.put("result" , "fail");
                map.put("msg" , "Requests are too frequent");
            } else {
                Object num = redisTemplate.opsForHash().get(redisKey,"num");
                if (Integer.valueOf(num.toString()) >= INTERFACE_NUM){
                    map.put("result" , "fail");
                    map.put("msg" , INTERFACE_EXPIRE + "seconds to reach the maximum number of requests");
                } else {
                    map.put("result" , "success");
                    redisTemplate.opsForHash().put(redisKey, "lastTime", lastTime+"");
                    redisTemplate.opsForHash().increment(redisKey,"num",1);
                }
            }
        } else {
            map.put("result" , "success");
            redisTemplate.opsForHash().put(redisKey, "lastTime", lastTime+"");
            redisTemplate.opsForHash().put(redisKey, "num", "1");
            redisTemplate.expire(redisKey, INTERFACE_EXPIRE, TimeUnit.SECONDS);
        }
        return map;
    }

}
