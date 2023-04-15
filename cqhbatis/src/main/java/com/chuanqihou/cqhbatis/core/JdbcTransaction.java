package com.chuanqihou.cqhbatis.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC事务管理器
 *
 * @author 传奇后
 * @date 2023/4/13 20:53
 * @description
 */
public class JdbcTransaction implements Transaction{

    /**
     * 数据源属性
     * 面向接口编程
     */
    private DataSource dataSource;

    /**
     * 自动提交标志
     * true：自动提交
     * false：不自动提交
     */
    private boolean autoCommit;

    /**
     * 数据库连接对象
     */
    private Connection connection;

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection() {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public JdbcTransaction(DataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }
}
