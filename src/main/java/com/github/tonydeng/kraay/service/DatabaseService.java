package com.github.tonydeng.kraay.service;

import com.github.tonydeng.kraay.Constant;
import com.github.tonydeng.kraay.bean.ColumnInfo;
import com.github.tonydeng.kraay.bean.MysqlInfo;
import com.github.tonydeng.kraay.utils.DBUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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


    public Map<String, List<ColumnInfo>> descTable(MysqlInfo mi) {
        if(CollectionUtils.isEmpty(mi.getTables())){
            mi.setTables(getDatabaseOrTables(mi, Constant.MySQLSelect.Table));
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

    public List<String> getDatabaseOrTables(MysqlInfo mi, Constant.MySQLSelect select){
        Statement stmt = null;
        ResultSet rs = null;
        List<String> list = Lists.newArrayList();
        try {
            if(log.isDebugEnabled())
                log.debug("mysql info : {}",mi);
            stmt = DBUtil.getConnection(mi).createStatement();
            String sql = null;
            switch (select){
                case DB:
                    sql = "show databases;";
                    break;
                case Table:
                    sql = "show tables;";
                    break;
            }
            if(StringUtils.isNotEmpty(sql)){
                rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int j = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 0; i < j; i++) {
                        list.add(rs.getString(i + 1));
                    }
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
        return list;
    }

}
