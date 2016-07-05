package com.github.tonydeng.kraay.web.controller;

import com.github.tonydeng.kraay.Constant;
import com.github.tonydeng.kraay.bean.ColumnInfo;
import com.github.tonydeng.kraay.service.BuilderService;
import com.github.tonydeng.kraay.service.DatabaseService;
import com.github.tonydeng.kraay.web.formbean.MybatisForm;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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

    @Resource
    private DatabaseService databaseService;
    @Resource
    private BuilderService builderService;
    @Resource
    private Properties kraayConfig;

    @RequestMapping("/index.do")
    public String index(ModelMap model) {
        model.put("form", new MybatisForm());
        return "home/index";
    }

    @RequestMapping("/build.do")
    public String build(@ModelAttribute MybatisForm form, ModelMap model) {

        if (StringUtils.isNotEmpty(form.getTable())) {
            form.getMi().setTables(Lists.newArrayList(form.getTable()));
        }
        if (log.isDebugEnabled())
            log.debug("mybatisform : {} mysql info {} package {}", form, form.getMi(), form.getPackaging());

        Map<String, List<ColumnInfo>> columns = databaseService.descTable(form.getMi());
        builderService.builder(form.getPackaging(), form.getMi().getDatabase(), columns);
        model.put("downloadUrl", kraayConfig.get("download.url") + form.getMi().getDatabase() + ".zip");
        model.put("filename", form.getMi().getDatabase() + ".zip");
        return "home/build";
    }
}
