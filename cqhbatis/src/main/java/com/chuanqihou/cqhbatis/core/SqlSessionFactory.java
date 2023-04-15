package com.chuanqihou.cqhbatis.core;

import java.util.Map;

/**
 * SqlSessionFactory对象：
 *      一个数据库对应一个SqlSessionFactory对象
 *      通过SqlSessionFactory对象可以获得SqlSession会话对象（开启会话）
 *      一个SqlSessionFactory对象可以开启多个SqlSession会话对象
 *
 * @author 传奇后
 * @date 2023/4/13 20:06
 * @description
 */
public class SqlSessionFactory {

    /**
     * 事务管理器属性
     * 事务管理器可以灵活切换
     * SqlSessionFactory中的事务管理器应该是面向接口编程的
     * SqlSessionFactory类中的属性中应该有一个事务管理器的接口
     */
    private Transaction transaction;


    /**
     * 存放Sql语句的map集合
     * key:SqlId
     * value:Sql标签对象 MappedStatement
     */
    private Map<String,MappedStatement> mappedStatements;

    public SqlSession openSession() {
        //开启连接
        transaction.openConnection();
        //创建SqlSession对象
        SqlSession sqlSession = new SqlSession(this);
        return sqlSession;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }

    public SqlSessionFactory() {
    }

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatements) {
        this.transaction = transaction;
        this.mappedStatements = mappedStatements;
    }
}
