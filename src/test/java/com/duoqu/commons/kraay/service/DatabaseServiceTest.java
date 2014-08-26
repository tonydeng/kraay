package com.duoqu.commons.kraay.service;

import com.duoqu.commons.BaseTest;
import com.duoqu.commons.kraay.bean.MysqlInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

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
    @Test
    public void showDatabaseTest() throws SQLException, ClassNotFoundException {
        databaseService.showDatabases(mi);
    }
}
