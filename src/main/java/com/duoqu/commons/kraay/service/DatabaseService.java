package com.duoqu.commons.kraay.service;

import com.duoqu.commons.kraay.bean.MysqlInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * Created by tonydeng on 14-8-26.
 */
@Service("databaseService")
public class DatabaseService {
    private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);
    private static final String driver = "com.mysql.jdbc.Driver";

    public List<String> showDatabases(MysqlInfo mi) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(getMysqlConnUrl(mi.getHost(), mi.getPort(), null), mi.getUser(), mi.getPassword());
        Statement stmt = conn.createStatement();
        String sql = "show databases;";
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int j = 0;
        j = rsmd.getColumnCount();
        for (int k = 0; k < j; k++) {
            log.info(rsmd.getCatalogName(k + 1));
        }
        while (rs.next()) {
            for (int i = 0; i < j; i++) {
                log.info(rs.getString(i + 1));
            }
        }
        return null;
    }

    private String getMysqlConnUrl(String host, int port, String database) {
        if (StringUtils.isNotEmpty(database))
            return "jdbc:mysql://" + host + ":" + port + "/" + database;
        else
            return "jdbc:mysql://" + host + ":" + port;
    }
}
