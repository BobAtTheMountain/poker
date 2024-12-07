package com.oe.controller;

import com.oe.tasks.InitKlinesTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitTask {

    @Autowired
    private InitKlinesTask initKlinesTask;

    @RequestMapping("/do_init_klines_task")
    public String doInitKlinesTask() {
        Runnable initTask = initKlinesTask.getInitKlinesTask();
        new Thread(initTask).start();
        return "启动成功";
    }

    @RequestMapping("/do_new_klines_task")
    public String doNewKlinesTask() {
        Runnable newTask = initKlinesTask.getNewKlinesTask();
        new Thread(newTask).start();
        return "启动成功";
    }
}
