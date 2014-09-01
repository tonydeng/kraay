package com.duoqu.commons.kraay.service;

import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.bean.Field;
import com.duoqu.commons.utils.CompressUtil;
import com.duoqu.commons.utils.FileUtil;
import com.google.common.collect.Lists;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tonydeng on 14-8-27.
 */
public class BuilderService {
    private static final Logger log = LoggerFactory.getLogger(BuilderService.class);
    private static final String encoding = "UTF-8";
    private static final String table_prefix = "t_";
    private static final String well = "#";
    private static String savePath;
    @Autowired
    private Configuration freemarkerConfiguration;

    public void builder(String packaging,String database, Map<String, List<ColumnInfo>> columns) {

        FileUtil.delFolder(savePath);

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
            model.put("well",well);
            writeFile(model);
        }

        CompressUtil.zipFile(savePath,new File(savePath).getParent()+File.separator+database+".zip");
    }

    private void writeFile(Map model) {
        try {
            String path = savePath+model.get("className")+"/";


            Template daoTemplate = freemarkerConfiguration.getTemplate("dao.ftl", encoding);
            String daoText = FreeMarkerTemplateUtils.processTemplateIntoString(daoTemplate, model);
            FileUtil.saveFile(daoText.getBytes(),savePath+File.separator+"dao"+File.separator+model.get("className")+"Dao.java");

            Template entityTemplate = freemarkerConfiguration.getTemplate("entity.ftl", encoding);
            String enttityText = FreeMarkerTemplateUtils.processTemplateIntoString(entityTemplate, model);
            FileUtil.saveFile(enttityText.getBytes(),savePath+File.separator+"entity"+File.separator+model.get("className")+".java");

            Template sqlmapTemplate = freemarkerConfiguration.getTemplate("sqlmap.ftl", encoding);
            String sqlmapText = FreeMarkerTemplateUtils.processTemplateIntoString(sqlmapTemplate, model);
            FileUtil.saveFile(sqlmapText.getBytes(),savePath+File.separator+"sqlmap"+File.separator+model.get("className")+"Mapper.xml");

            Template serviceTemplate = freemarkerConfiguration.getTemplate("service.ftl",encoding);
            String serviceText = FreeMarkerTemplateUtils.processTemplateIntoString(serviceTemplate,model);
            FileUtil.saveFile(serviceText.getBytes(),savePath+File.separator+"service"+File.separator+model.get("className")+"Service.java");

            Template controllerTemplate = freemarkerConfiguration.getTemplate("controller.ftl",encoding);
            String controllerText = FreeMarkerTemplateUtils.processTemplateIntoString(controllerTemplate,model);
            FileUtil.saveFile(controllerText.getBytes(),savePath+File.separator+"controller"+File.separator+model.get("className")+"Controller.java");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
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

    private String getLower(String name) {
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

    private String getClassName(String table) {
        String className;
        if (table.indexOf(table_prefix) == 0) {
            className = table.substring(table_prefix.length(), table.length());
        } else {
            className = table;
        }
        StringBuffer sb = new StringBuffer();
        for (String s : className.split("_")) {
            sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        return sb.toString();
    }

    public static String getSavePath() {
        return savePath;
    }

    public static void setSavePath(String savePath) {
        BuilderService.savePath = savePath;
    }
}
