package com.duoqu.commons.kraay.service;

import com.duoqu.commons.BaseTest;
import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.bean.MysqlInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tonydeng on 14-8-27.
 */
public class BuilderServiceTest extends BaseTest {
    @Autowired
    private BuilderService builderService;
    @Autowired
    private DatabaseService databaseService;
    private MysqlInfo mi;
    @Before
    public void init(){
        mi = new MysqlInfo();
        mi.setUser("rs");
        mi.setPassword("duoqu_rs");
        mi.setHost("mysql-m.dq.in");
        mi.setPort(3306);
    }
    @Test
    public void builderDaoTest(){
        mi.setDatabase("rs");
        mi.setTable("t_rs_upgrade");


        List<ColumnInfo> columns = databaseService.descTable(mi);
        String packaging = "com.duoqu.rs.dao.entity";
        String tableName = "Upgrade";
        String text = builderService.builderDao(packaging,tableName,columns,"entity.ftl");
        log.info(text);
    }
}
