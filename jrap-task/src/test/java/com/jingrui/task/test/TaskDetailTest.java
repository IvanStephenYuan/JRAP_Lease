package com.jingrui.task.test;

import com.jingrui.jrap.task.dto.TaskDetail;
import com.jingrui.jrap.task.service.impl.TaskDetailServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailTest {

    @Autowired
    TaskDetailServiceImpl service;
    @Test
    public void test() {
        List<String> list = new ArrayList<>();

        service.queryChildrenByPrimaryKey(list);
    }
}
