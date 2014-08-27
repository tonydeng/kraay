package com.duoqu.commons.kraay.service;

import com.duoqu.commons.BaseTest;
import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.bean.MysqlInfo;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by tonydeng on 14-8-26.
 */
public class DatabaseServiceTest extends BaseTest {
    @Autowired
    private DatabaseService databaseService;
    private MysqlInfo mi;
    @Before
    public void init(){
        mi = new MysqlInfo();
        mi.setUser("root");
        mi.setPassword("");
        mi.setHost("localhost");
        mi.setPort(3306);
    }
//    @Test
    public void showDatabaseTest() throws SQLException, ClassNotFoundException {
        List<String> list = databaseService.showDatabases(mi);
        for(String db:list){
            log.info(db);
        }
    }

//    @Test
    public void showTablesTest() throws SQLException, ClassNotFoundException {
        mi.setDatabase("ebp");
        List<String> list = databaseService.showTables(mi);
        for(String table:list){
            log.info(table);
        }
    }

//    @Test
    public void descTableTest() throws SQLException, ClassNotFoundException {
        mi.setDatabase("ebp");
        mi.setTables(Lists.newArrayList("t_ebp_style"));
        Map<String,List<ColumnInfo>> columns = databaseService.descTable(mi);
        for(String table:columns.keySet()){
            for(ColumnInfo column: columns.get(table)){
                log.info(column.getName()+"     "+column.getType());
            }
        }
    }
}
