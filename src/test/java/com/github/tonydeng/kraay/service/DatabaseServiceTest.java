package com.github.tonydeng.kraay.service;

import com.github.tonydeng.kraay.BaseTest;
import com.github.tonydeng.kraay.Constant;
import com.github.tonydeng.kraay.bean.ColumnInfo;
import com.github.tonydeng.kraay.bean.MysqlInfo;
import com.github.tonydeng.kraay.utils.DBUtil;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by tonydeng on 14-8-26.
 */
@Ignore
public class DatabaseServiceTest extends BaseTest {
    @Resource
    private DatabaseService databaseService;
    private MysqlInfo mi;
    @Before
    public void init(){
        mi = new MysqlInfo();
        mi.setUser("data_test");
        mi.setPassword("Cim123123");
        mi.setHost("mysql.cim.in");
        mi.setPort(3306);
    }
    @Test
    public void showDatabaseTest() throws SQLException, ClassNotFoundException {
        List<String> list = databaseService.getDatabaseOrTables(mi, Constant.MySQLSelect.DB);
        for(String db:list){
            log.info(db);
        }
    }

    @Test
    public void showTablesTest() throws SQLException, ClassNotFoundException {
        mi.setDatabase("data_test");
        List<String> list = databaseService.getDatabaseOrTables(mi, Constant.MySQLSelect.Table);
        for(String table:list){
            log.info(table);
        }
    }

    @Test
    public void descTableTest() throws SQLException, ClassNotFoundException {
        mi.setDatabase("data_test");
        mi.setTables(Lists.newArrayList("device"));
        Map<String,List<ColumnInfo>> columns = databaseService.descTable(mi);
        for(String table:columns.keySet()){
            for(ColumnInfo column: columns.get(table)){
                log.info(column.getName()+"     "+column.getType());
            }
        }
    }
}
