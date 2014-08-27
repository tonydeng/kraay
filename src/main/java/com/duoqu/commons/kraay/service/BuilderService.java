package com.duoqu.commons.kraay.service;

import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.bean.Field;
import com.google.common.collect.Lists;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tonydeng on 14-8-27.
 */
@Service("builderService")
public class BuilderService {
    private static final Logger log = LoggerFactory.getLogger(BuilderService.class);
    @Autowired
    private Configuration freemarkerConfiguration;

    public String builderDao(String packaging, String tableName, List<ColumnInfo> columns, String templateName) {
        String text = null;
        try {
            Map model = new ConcurrentHashMap();

            List<Field> fields = Lists.newArrayList();
            for (ColumnInfo ci : columns) {
                Field field = new Field();

                field.setType(getFieldType(ci.getType()));
                field.setLower(getLower(ci.getName()));
                field.setUpper(getUpper(ci.getName()));
                fields.add(field);
            }

            model.put("packaging", packaging);
            model.put("tableName",tableName);
            model.put("columns", columns);

            model.put("fields", fields);

            Template template = freemarkerConfiguration.getTemplate(templateName);
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return text;
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
            case "TINYINT":
                fieldType = "Boolean";
                break;
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
}
