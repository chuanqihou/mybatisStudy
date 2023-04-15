package com.chuanqihou.cqhbatis.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

/**
 * 负责执行SQL语句的会话对象
 *
 * @author 传奇后
 * @date 2023/4/14 19:52
 * @description
 */
public class SqlSession {

    private SqlSessionFactory sqlSessionFactory;

    //insert

    /**
     * 执行insert语句，往数据库中插入一条数据
     * @param sqlId sql语句的Id
     * @param pojo 需要插入的数据
     * @return
     */
    public int insert(String sqlId, Object pojo) {
        int result = 0;
        try {
            //JDBC代码
            Connection connection = sqlSessionFactory.getTransaction().getConnection();
            //获取cqhBatisSQl
            String cqhBatisSql = sqlSessionFactory.getMappedStatements().get(sqlId).getSql();
            //将#{}替换成?
            String sql = cqhBatisSql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //给?传值
            int fromIndex = 0;
            int index = 1;
            while (true) {
                int jingIndex = cqhBatisSql.indexOf("#", fromIndex);
                if (jingIndex < 0) {
                    break;
                }
                int youkuoIndex = cqhBatisSql.indexOf("}", fromIndex);
                String propertyName = cqhBatisSql.substring(jingIndex + 2, youkuoIndex);

                //获取get方法的方法名
                String getMethodName = "get" + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method getMethod = pojo.getClass().getDeclaredMethod(getMethodName);
                //调用get方法获取值
                Object propertyValue = getMethod.invoke(pojo);
                //给?传值
                preparedStatement.setString(index,propertyValue.toString());

                index++;
                fromIndex = youkuoIndex + 1;
            }

            //执行SQL语句
            result = preparedStatement.executeUpdate();

        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    //selectOne
    public Object selectOne(String sqlId, Object param) {
        Object obj = null;
        try {
            //获取连接
            Connection connection = sqlSessionFactory.getTransaction().getConnection();
            MappedStatement mappedStatement = sqlSessionFactory.getMappedStatements().get(sqlId);
            //获取SQL
            String chqBatisSql = mappedStatement.getSql();
            //获取结果集封装的类型
            String resultType = mappedStatement.getResultType();
            //去除#{}，加上?
            String sql = chqBatisSql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            //预编译sql
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //给？赋值
            preparedStatement.setString(1,param.toString());
            //获取结果集
            ResultSet resultSet = preparedStatement.executeQuery();
            //封装结果集
            if (resultSet.next()) {
                //拿到结果集类型的Class对象
                Class<?> resultTypeClass = Class.forName(resultType);
                //创建该对象
                obj = resultTypeClass.getConstructor().newInstance();
                //拿到数据库表中的数据模型
                ResultSetMetaData metaData = resultSet.getMetaData();
                //获取表中数据字段个数
                int columnCount = metaData.getColumnCount();
                //循环取出
                for (int i = 0; i < columnCount; i++) {
                    //获取字段名
                    String columnName = metaData.getColumnName(i + 1);
                    //拼接方法名
                    String setMethodName = "set" + columnName.toUpperCase().charAt(0) + columnName.substring(1);
                    //获得set方法对象
                    Method declaredMethod = resultTypeClass.getDeclaredMethod(setMethodName, String.class);
                    //调用set方法给属性赋值
                    declaredMethod.invoke(obj, resultSet.getString(columnName));
                }
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 提交事务
     */
    public void commit() {
        sqlSessionFactory.getTransaction().commit();
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        sqlSessionFactory.getTransaction().rollback();
    }

    /**
     * 关闭连接
     */
    public void close() {
        sqlSessionFactory.getTransaction().close();
    }

    public SqlSession(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
