package com.chuanqihou.cqhbatis.core;

import java.sql.Connection;

/**
 * 事务管理器接口
 * 所有的事务管理器需要遵循该规范
 * JDBC、MANAGED事物管理器都应该实现该接口
 * Transaction接口提供管理事务的方法
 *
 * @author 传奇后
 * @date 2023/4/13 20:48
 * @description
 */
public interface Transaction {

    /**
     * 提交事务
     */
    void commit();

    /**
     * 管理事务
     */
    void rollback();

    /**
     * 关闭事务
     */
    void close();

    /**
     * 开启数据库连接
     */
    void openConnection();

    /**
     * 获取数据库连接对象
     */
    Connection getConnection();

}
