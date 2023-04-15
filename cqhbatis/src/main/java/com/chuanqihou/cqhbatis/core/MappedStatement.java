package com.chuanqihou.cqhbatis.core;

import java.util.Objects;

/**
 * 普通的java类。POJO，封装了一个SQL标签
 * 一个MappedStatement对应一个SQL标签
 * 一个SQL标签中的所有信息封装在MappedStatement类中
 * 面向对象编程，封装
 *
 * @author 传奇后
 * @date 2023/4/13 20:28
 * @description
 */
public class MappedStatement {

    /**
     *sql语句
     */
    private String sql;

    /**
     *封装的结果集类型，若没有则为null
     */
    private String resultType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedStatement that = (MappedStatement) o;
        return Objects.equals(sql, that.sql) && Objects.equals(resultType, that.resultType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, resultType);
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public MappedStatement() {
    }

    public MappedStatement(String sql, String resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }
}
