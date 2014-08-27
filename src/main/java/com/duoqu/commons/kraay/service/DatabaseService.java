package com.duoqu.commons.kraay.service;

import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.bean.MysqlInfo;
import com.duoqu.commons.kraay.utils.DBUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tonydeng on 14-8-26.
 */
@Service("databaseService")
public class DatabaseService {
    private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);

    public List<String> showDatabases(MysqlInfo mi) {

        Statement stmt = null;
        ResultSet rs = null;
        List<String> dbs = null;
        try {
            stmt = DBUtil.getConnection(mi).createStatement();
            String sql = "show databases;";
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int j = rsmd.getColumnCount();
            dbs = Lists.newArrayList();
            while (rs.next()) {
                for (int i = 0; i < j; i++) {
                    dbs.add(rs.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.closeConnection();
        }
        return dbs;
    }

    public List<String> showTables(MysqlInfo mi) {
        Statement stmt = null;
        ResultSet rs = null;
        List<String> tables = null;
        try {
            stmt = DBUtil.getConnection(mi).createStatement();
            String sql = "show tables;";
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int j = rsmd.getColumnCount();
            tables = Lists.newArrayList();
            while (rs.next()) {
                for (int i = 0; i < j; i++) {
                    tables.add(rs.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.closeConnection();
        }
        return tables;
    }

    public Map<String, List<ColumnInfo>> descTable(MysqlInfo mi) {
        if(CollectionUtils.isEmpty(mi.getTables())){
            mi.setTables(showTables(mi));
        }
        Statement stmt = null;
        ResultSet rs = null;
        Map<String, List<ColumnInfo>> result = null;
        try {
            stmt = DBUtil.getConnection(mi).createStatement();
            result = new ConcurrentHashMap<>();
            for (String table : mi.getTables()) {
                String sql = "select * from " + table + " where 1=2";
                rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int j = rsmd.getColumnCount();
                List<ColumnInfo> columns = Lists.newArrayList();
                for (int i = 1; i <= j; i++) {
                    ColumnInfo column = new ColumnInfo(rsmd.getColumnName(i), rsmd.getColumnTypeName(i).split(" ")[0]);
                    columns.add(column);
                }

                result.put(table,columns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.closeConnection();
        }
        return result;
    }

}
