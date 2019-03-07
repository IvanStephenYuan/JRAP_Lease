package com.jingrui.jrap.core.exception;

/**
 * @author xiangyu.qi@jingrui.com on 2018/3/27.
 */
public interface IBaseException {

    String getCode();

    String getDescriptionKey();

    Object[] getParameters();

    void setCode(String code);

    void setDescriptionKey(String descriptionKey);

    void setParameters(Object[] parameters);
}
