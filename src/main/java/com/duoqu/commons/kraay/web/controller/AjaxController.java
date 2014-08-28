package com.duoqu.commons.kraay.web.controller;

import com.duoqu.commons.kraay.bean.MysqlInfo;
import com.duoqu.commons.kraay.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by tonydeng on 14-8-28.
 */
@Controller
@RequestMapping("/ajax/*")
public class AjaxController {
    private static final Logger log = LoggerFactory.getLogger(AjaxController.class);

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping("/db.do")
    @ResponseBody
    public List<String> getDatabase(@RequestParam("u") String user,
                                    @RequestParam("p") String password,
                                    @RequestParam("h") String host,
                                    @RequestParam("P") Integer port){
        MysqlInfo mi = new MysqlInfo();
        mi.setUser(user);
        mi.setPassword(password);
        mi.setHost(host);
        mi.setPort(port);

        return databaseService.showDatabases(mi);
    }

    @RequestMapping("/table.do")
    @ResponseBody
    public List<String> getTable(@RequestParam("u") String user,
                                    @RequestParam("p") String password,
                                    @RequestParam("h") String host,
                                    @RequestParam("P") Integer port,
                                    @RequestParam("db") String database){
        MysqlInfo mi = new MysqlInfo();
        mi.setUser(user);
        mi.setPassword(password);
        mi.setHost(host);
        mi.setPort(port);
        mi.setDatabase(database);

        return databaseService.showTables(mi);
    }
}
