package com.chuanqihou.cqhbatis.core;

/**
 * 常量类
 *
 * @author 传奇后
 * @date 2023/4/14 19:02
 * @description
 */
public class Const {

    /**
     * 数据源：不使用连接池
     */
    public static final String UN_POOLED_DATASOURCE = "UNPOOLED";
    /**
     * 数据源：使用连接池
     */
    public static final String POOLED_DATASOURCE = "POOLED";
    /**
     * 数据源：使用第三方连接池
     */
    public static final String JNDI_DATASOURCE = "JNDI";


    /**
     * 事务管理器类型：JNDI
     */
    public static final String JDBC_TRANSACTION = "JDBC";
    /**
     * 事务管理器类型：JDBC
     */
    public static final String JNDI_TRANSACTION = "JNDI";
    /**
     * 事务管理器类型：MANAGED
     */
    public static final String MANAGED_TRANSACTION = "MANAGED";

}
