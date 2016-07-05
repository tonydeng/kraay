package com.github.tonydeng.kraay.web.formbean;

import com.github.tonydeng.kraay.Constant;
import com.github.tonydeng.kraay.bean.MysqlInfo;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isEmpty(packaging))
            setPackaging(Constant.PACKAING);
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }
}
