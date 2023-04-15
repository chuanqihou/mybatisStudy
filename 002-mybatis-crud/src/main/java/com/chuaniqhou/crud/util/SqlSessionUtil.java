package com.chuaniqhou.crud.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 传奇后
 * @date 2023/4/12 8:54
 * @description
 */
public class SqlSessionUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession openSqlSession() {
        return sqlSessionFactory.openSession(true);
    }

}
