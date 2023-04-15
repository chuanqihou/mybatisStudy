package com.chuanqihou.p1;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.net.URL;

/**
 * @author 传奇后
 * @date 2023/4/10 20:17
 * @description
 */
public class OneDemo {
    public static void main(String[] args) throws Exception {

        //InputStream resourceAsStream1 = ClassLoader.getSystemClassLoader().getResourceAsStream("");

        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        int insertUser = sqlSession.insert("insertUser");

        System.out.println(insertUser);

        sqlSession.commit();
    }
}
