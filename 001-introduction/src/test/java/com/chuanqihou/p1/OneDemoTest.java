package com.chuanqihou.p1;

import com.chuanqihou.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 传奇后
 * @date 2023/4/11 15:17
 * @description
 */
public class OneDemoTest {

    @Test
    public void testOne() {

        InputStream resourceAsStream = null;
        try {
            resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        int insertUser = sqlSession.insert("insertUser");

        System.out.println(insertUser);

        sqlSession.commit();
    }

    @Test
    public void testSqlSessionUtil() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        sqlSession.insert("insertUser");
        sqlSession.commit();
        sqlSession.close();
    }

}
