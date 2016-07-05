package com.github.tonydeng.kraay;

import java.io.File;

/**
 * Created by tonydeng on 16/7/5.
 */
public class Constant {
    /**
     * 默认的package
     */
    public static final String PACKAING = "com.cim120";
    /**
     * 默认的端口
     */
    public static final int PORT = 3306;

    /**
     * 默认临时文件目录
     */
    public static final String TMP_PATH = System.getProperty("java.io.tmpdir");

    public static final String UTF_8 = "UTF-8";

    public static final String TABLE_PREFIX = "t_";

    public static final String WELL = "#";

    /**
     * MySQL结构选择
     */
    public enum MySQLSelect {
        DB, Table
    }
}
