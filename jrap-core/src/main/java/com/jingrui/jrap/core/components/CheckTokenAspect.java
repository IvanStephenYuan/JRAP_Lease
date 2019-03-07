package com.jingrui.jrap.core.components;

import java.util.Collection;

import com.jingrui.jrap.core.exception.TokenException;
import com.jingrui.jrap.core.interceptor.SecurityTokenInterceptor;
import com.jingrui.jrap.security.TokenUtils;
import com.jingrui.jrap.system.dto.BaseDTO;
import com.jingrui.jrap.system.dto.DTOStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 执行更新时，在controller层校验BaseDTO的防篡改token.
 *
 * @author qiang.zeng@jingrui.com
 * @date 2018/6/7
 */
@Aspect
@Component
public class CheckTokenAspect {

    /**
     * DTO update时，是否进行token校验.
     * 默认校验
     */
    @Value("${sys.security.token.validate:true}")
    private boolean sysSecurityTokenValidate;

    @Pointcut("execution(* *..*Controller.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void checkBeanValid(JoinPoint joinPoint) throws TokenException {
        if(sysSecurityTokenValidate) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof BaseDTO) {
                    checkToken(args[i]);
                } else if (args[i] instanceof Collection) {
                    for (Object o : (Collection) args[i]) {
                        if (o instanceof BaseDTO) {
                            checkToken(o);
                        }
                    }
                }
            }
        }
    }

    /**
     * BaseDTO 防篡改Token校验.
     *
     * @param obj Object
     * @throws TokenException token校验异常
     */
    private void checkToken(Object obj) throws TokenException {
        BaseDTO baseDto = (BaseDTO) obj;
        boolean check = DTOStatus.UPDATE.equals(baseDto.get__status());
        if (check) {
            TokenUtils.checkToken(SecurityTokenInterceptor.LOCAL_SECURITY_KEY.get(), baseDto);
        }
    }
}
