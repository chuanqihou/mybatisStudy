package com.chuanqihou.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * @author 传奇后
 * @date 2023/4/11 19:56
 * @description
 */
public class SqlSessionUtil {

    private static SqlSessionFactory sqlSessionFactory;


    static {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SqlSessionUtil() {}

    public static SqlSession openSession() {
        //每调用一次openSession()可获取一个新的会话，该会话支持自动提交。
        return sqlSessionFactory.openSession(true);
    }


}
