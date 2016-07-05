package com.github.tonydeng.kraay.service;

import com.cim120.commons.utils.Strings;
import com.github.tonydeng.kraay.BaseTest;
import com.github.tonydeng.kraay.Constant;
import com.github.tonydeng.kraay.bean.MysqlInfo;
import com.github.tonydeng.kraay.bean.ColumnInfo;
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
//@Ignore
public class BuilderServiceTest extends BaseTest {
    @Autowired
    private BuilderService builderService;
    @Autowired
    private DatabaseService databaseService;
    private static MysqlInfo mi;

    @Before
    public void init() {
        mi = new MysqlInfo();
        mi.setUser("data_test");
        mi.setPassword("Cim123123");
        mi.setHost("mysql.cim.in");
        mi.setPort(3306);
    }


    @Test
    public void testBuilder() {
        mi.setDatabase("rdp");
//        mi.setTables(Lists.newArrayList("t_upgrade", "t_book"));

//        mi.setDatabase("ebp");
//        mi.setTables(Lists.newArrayList("t_ebp_book"));

        Map<String, List<ColumnInfo>> columns = databaseService.descTable(mi);
        String zip = builderService.builder(Constant.PACKAING, mi.getDatabase(), columns);
        log.info("zip {}", zip);
    }

    //    @Test
    public void testGetClassName() {
        mi.setDatabase("rdp");
        List<String> tables = databaseService.getDatabaseOrTables(mi, Constant.MySQLSelect.Table);
        tables.stream().forEach(
                t -> {
                    log.info("table {} {} {}", t, builderService.getClassName(t), Strings.whitespaceTo(t, "_").camelCase());
                }
        );

    }

    //    @Test
    public void getClassNameTest() {
        String table = "t_component";
        String className = table.substring("t_".length(), table.length());
        StringBuffer sb = new StringBuffer();
        for (String s : className.split("_")) {
            sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        log.info("className:'" + sb.toString() + "'");
    }
}
