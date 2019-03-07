package com.jingrui.jrap.core.web.view;


public interface IterationHandle<T> {
    
    public static final int IT_CONTINUE = 0;
    public static final int IT_NOCHILD = 1;
    public static final int IT_BREAK = 2;
    public static final int IT_REMOVE = -1;
    
    public int process( XMap map);
    
    public T find(XMap map);

}

