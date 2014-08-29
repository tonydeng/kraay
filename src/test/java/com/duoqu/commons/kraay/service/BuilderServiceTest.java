package com.duoqu.commons.kraay.service;

import com.duoqu.commons.BaseTest;
import com.duoqu.commons.kraay.bean.ColumnInfo;
import com.duoqu.commons.kraay.bean.MysqlInfo;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by tonydeng on 14-8-27.
 */
@Ignore
public class BuilderServiceTest extends BaseTest {
    @Autowired
    private BuilderService builderService;
    @Autowired
    private DatabaseService databaseService;
    private MysqlInfo mi;
    @Before
    public void init(){
        mi = new MysqlInfo();
//        mi.setUser("rs");
//        mi.setPassword("duoqu_rs");
//        mi.setHost("mysql-m.dq.in");
        mi.setUser("ebp");
        mi.setPassword("123456");
        mi.setHost("localhost");
        mi.setPort(3306);
    }
    @Test
    public void builderDaoTest(){
//        mi.setDatabase("rs");
//        mi.setTables(Lists.newArrayList("t_upgrade"));
        mi.setDatabase("ebp");
        mi.setTables(Lists.newArrayList("t_ebp_book"));

        Map<String,List<ColumnInfo>> columns = databaseService.descTable(mi);
        String packaging = "com.duoqu.rs";
        builderService.builder(packaging,mi.getDatabase(),columns);
    }
//    @Test
    public void getClassNameTest(){
        String table = "t_component";
        String className = table.substring("t_".length(),table.length());
        StringBuffer sb = new StringBuffer();
        for(String s:className.split("_")){
            sb.append(s.substring(0,1).toUpperCase()+s.substring(1));
        }
        log.info("className:'"+sb.toString()+"'");
    }
}
