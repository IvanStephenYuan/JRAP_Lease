package com.jingrui.jrap.core.util;

/**
 * declare Holder as final to hold an item.
 *
 * @author shengyang.zhou@jingrui.com
 */
public class Holder<T> {
    public T item;

    public Holder() {

    }

    public Holder(T object) {
        this.item = object;
    }

}
