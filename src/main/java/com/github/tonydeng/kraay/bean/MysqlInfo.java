package com.github.tonydeng.kraay.bean;

import com.github.tonydeng.kraay.Constant;

import java.util.List;

/**
 * Created by tonydeng on 14-8-26.
 */
public class MysqlInfo {
    private String user;
    private String password;
    private String host;
    private int port;
    private String database;
    private List<String> tables;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        if (port == 0)
            setPort(Constant.PORT);
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "MysqlInfo{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", database='" + database + '\'' +
                ", tables=" + tables +
                '}';
    }
}
