package com.duoqu.commons.kraay.web.formbean;

import com.duoqu.commons.kraay.bean.MysqlInfo;

/**
 * Created by tonydeng on 14-8-28.
 */
public class MybatisForm {
    private MysqlInfo mi;
    private String table;
    private String packaging;
    public MysqlInfo getMi() {
        return mi;
    }

    public void setMi(MysqlInfo mi) {
        this.mi = mi;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }
}
