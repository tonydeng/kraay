package com.github.tonydeng.kraay.web.controller;

import com.cim120.commons.utils.FileUtil;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
        model.put("filename", form.getMi().getDatabase() + ".zip");
        return "home/build";
    }

    @RequestMapping("down.do")
    public void down(@RequestParam("file") String file, HttpServletResponse response) {

        if (StringUtils.isEmpty(file)) {
            return;
        }

        File zip = new File(new File(builderService.getSavePath()).getParent() + File.separator + file);
        log.info("zip {} parent {}  {}",zip,zip.getParent(),new File(builderService.getSavePath()).getParent());
        if (null == zip || !zip.exists()
                || !zip.getParent().equals(new File(builderService.getSavePath()).getParent())) {
            return;
        }

        OutputStream toClient = null;
        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getBytes("utf-8")));
            response.addHeader("Content-Length", "" + zip.length());
            response.setContentType("application/octet-stream; charset=utf-8");
            toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(FileUtil.getByteForFile(zip));
            toClient.flush();

        } catch (IOException ex) {
            log.error("file download error", ex);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            zip.delete();
        }
    }

}
