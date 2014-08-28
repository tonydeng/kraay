package com.duoqu.commons.kraay.web.controller;

import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.service.BuilderService;
import com.duoqu.commons.kraay.service.DatabaseService;
import com.duoqu.commons.kraay.web.formbean.MybatisForm;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by tonydeng on 14-8-28.
 */
@Controller
@RequestMapping("/home/*")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private String packaging = "com.duoqu";

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private BuilderService builderService;
    @Autowired
    private Properties kraayConfig;

    @RequestMapping("/index.do")
    public String index(Model model) {
        model.addAttribute("form", new MybatisForm());
        return "home/index";
    }
    @RequestMapping("/build.do")
    public String build(@ModelAttribute("form") MybatisForm form,Model model){
        if(StringUtils.isNotEmpty(form.getTable())){
            form.getMi().setTables(Lists.newArrayList(form.getTable()));
        }
        if(StringUtils.isNotEmpty(form.getPackaging())){
            packaging = form.getPackaging();
        }
        Map<String,List<ColumnInfo>> columns = databaseService.descTable(form.getMi());
        builderService.builder(packaging,form.getMi().getDatabase(),columns);
        model.addAttribute("downloadUrl",kraayConfig.get("download.url")+form.getMi().getDatabase()+".zip");
        model.addAttribute("filename",form.getMi().getDatabase()+".zip");
        return "home/build";
    }
}
