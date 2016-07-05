package com.github.tonydeng.kraay.bean;

/**
 * Created by tonydeng on 14-9-3.
 */
public class TemplateConfig {
    private String tempateName;
    private String targetFile;

    public TemplateConfig(String tempateName,String targetFile){
        this.tempateName = tempateName;
        this.targetFile = targetFile;
    }

    public String getTempateName() {
        return tempateName;
    }

    public void setTempateName(String tempateName) {
        this.tempateName = tempateName;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }
}
