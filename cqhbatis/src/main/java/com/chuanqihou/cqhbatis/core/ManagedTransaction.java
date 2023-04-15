package com.chuanqihou.cqhbatis.core;

import java.sql.Connection;

/**
 * MANAGED事务管理器（目前先不实现）
 *
 * @author 传奇后
 * @date 2023/4/13 20:53
 * @description
 */
public class ManagedTransaction implements Transaction{
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public void openConnection() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
