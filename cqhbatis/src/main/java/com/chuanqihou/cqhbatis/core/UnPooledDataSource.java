package com.chuanqihou.cqhbatis.core;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 数据源的实现类： UNPOLLED
 * 不使用连接池，每次都创建新的连接对象（重点实现）
 *
 * @author 传奇后
 * @date 2023/4/13 21:00
 * @description
 */
public class UnPooledDataSource implements DataSource {

    /**
     * 数据库驱动
     */
    private String driver;
    /**
     * 数据库连接地址
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;

    @Override
    public Connection getConnection() throws SQLException {
        //创建Connection连接对象并返回
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }


    public UnPooledDataSource(String driver, String url, String username, String password) {

        try {
            //注册驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
