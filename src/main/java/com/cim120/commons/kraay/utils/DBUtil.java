package com.cim120.commons.kraay.utils;

import com.cim120.commons.kraay.bean.MysqlInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tonydeng on 14-8-27.
 */
public class DBUtil {
    private static final Logger log = LoggerFactory.getLogger(DBUtil.class);

    private static final String driver = "com.mysql.jdbc.Driver";

    private static Connection conn;

    public static Connection getConnection(MysqlInfo mi){
        try {
            Class.forName(driver);
            if(conn == null)
                conn = DriverManager.getConnection(getMysqlConnUrl(mi.getHost(), mi.getPort(), mi.getDatabase()), mi.getUser(), mi.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getMysqlConnUrl(String host, int port, String database) {
        if (StringUtils.isNotEmpty(database))
            return "jdbc:mysql://" + host + ":" + port + "/" + database;
        else
            return "jdbc:mysql://" + host + ":" + port;
    }

    /**
     *
     */
    public enum Info{
        DB,Table
    }
}
