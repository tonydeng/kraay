package com.github.tonydeng.kraay.service;

import com.cim120.commons.utils.CompressUtil;
import com.cim120.commons.utils.FileUtil;
import com.github.tonydeng.kraay.Constant;
import com.github.tonydeng.kraay.bean.ColumnInfo;
import com.github.tonydeng.kraay.bean.Field;
import com.github.tonydeng.kraay.bean.TemplateConfig;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tonydeng on 14-8-27.
 */
@Service("builderService")
public class BuilderService {
    private static final Logger log = LoggerFactory.getLogger(BuilderService.class);
    @Resource
    private Properties kraayConfig;

    @Resource
    private Configuration freemarker;

    public String builder(String packaging, String database, Map<String, List<ColumnInfo>> columns) {

        FileUtil.delFolder(getSavePath());

        for (String table : columns.keySet()) {
            Map model = new ConcurrentHashMap();

            List<Field> fields = Lists.newArrayList();
            for (ColumnInfo ci : columns.get(table)) {
                Field field = new Field();

                field.setType(getFieldType(ci.getType()));
                field.setLower(getLower(ci.getName()));
                field.setUpper(getUpper(ci.getName()));
                field.setOriginal(ci.getName());
                fields.add(field);
            }

            String className = getClassName(table);

            model.put("packaging", packaging);
            model.put("className", className);
            model.put("tableName", table);
            model.put("lowerClassName", className.substring(0, 1).toLowerCase() + className.substring(1));
            model.put("columns", columns);

            model.put("fields", fields);
            model.put("well", Constant.WELL);
            writeFile(model);
        }
        String zipFile = Joiner.on("/").join(new String[]{new File(getSavePath()).getParent(),database+".zip"});
        CompressUtil.zipFile(getSavePath(),  zipFile);
        return zipFile;
    }

    private void writeFile(Map model) {
        try {

            for (TemplateConfig config : getTemplates(model)) {
                Template template = freemarker.getTemplate(config.getTempateName(), Constant.UTF_8);
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                FileUtil.saveFile(content.getBytes(), config.getTargetFile());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private List<TemplateConfig> getTemplates(Map model) {
        return Lists.newArrayList(
                new TemplateConfig("dao.ftl", getSavePath() + File.separator + "dao" + File.separator + model.get("className") + "Dao.java"),
                new TemplateConfig("entity.ftl", getSavePath() + File.separator + "entity" + File.separator + model.get("className") + ".java"),
                new TemplateConfig("sqlmap.ftl", getSavePath() + File.separator + "sqlmap" + File.separator + model.get("className") + "Mapper.xml"),
                new TemplateConfig("service.ftl", getSavePath() + File.separator + "service" + File.separator + model.get("className") + "Service.java"),
                new TemplateConfig("controller.ftl", getSavePath() + File.separator + "controller" + File.separator + model.get("className") + "Controller.java")
        );
    }


    private String getFieldType(String type) {
        String fieldType = null;
        switch (type.toUpperCase()) {
            case "VARCHAR":
                fieldType = "String";
                break;
            case "INT":
                fieldType = "Integer";
                break;
            case "TIMESTAMP":
                fieldType = "Date";
                break;
            case "DATE":
                fieldType = "Date";
                break;
            case "TINYINT":
                fieldType = "Integer";
                break;
            case "BIGINT":
                fieldType = "Long";
                break;
            case "DECIMAL":
                fieldType = "Double";
                break;
            case "TEXT":
                fieldType = "String";
            default:
                fieldType = "String";
        }
        return fieldType;
    }

    public String getLower(String name) {
        StringBuffer lower = new StringBuffer();
        String[] array = name.split("_");
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                lower.append(array[i]);
            } else {
                lower.append(array[i].substring(0, 1).toUpperCase() + array[i].substring(1));
            }
        }
        return lower.toString();
    }

    private String getUpper(String name) {
        StringBuffer upper = new StringBuffer();
        String[] array = name.split("_");
        for (int i = 0; i < array.length; i++) {
            upper.append(array[i].substring(0, 1).toUpperCase() + array[i].substring(1));
        }
        return upper.toString();
    }

    public String getClassName(String table) {
        String className;
        if (table.indexOf(Constant.TABLE_PREFIX) == 0) {
            className = table.substring(Constant.TABLE_PREFIX.length(), table.length());
        } else {
            className = table;
        }
        StringBuffer sb = new StringBuffer();
        for (String s : className.split("_")) {
            sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        return sb.toString();
    }

    public String getSavePath() {
        return Constant.TMP_PATH + kraayConfig.getProperty("save.path");
    }

}
