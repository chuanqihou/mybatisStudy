package com.chuanqihou.stu;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @author 传奇后
 * @date 2023/4/12 14:28
 * @description
 */
public class ConfigurationTest {

    @Test
    public void environmentIdAndDefaultTest() throws IOException {
        //创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //获取SqlSessionFactory对象（默认环境）
        SqlSessionFactory sqlSessionFactory1 = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        //获取SqlSessionFactory对象（指定环境）
        SqlSessionFactory sqlSessionFactory2 = sqlSessionFactoryBuilder.build(Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml"),"userEn");

        //创建SqlSession对象（默认环境）
        SqlSession sqlSession1 = sqlSessionFactory1.openSession();
        //创建SqlSession对象（指定环境）
        SqlSession sqlSession2 = sqlSessionFactory2.openSession();

        //执行Sql
        sqlSession1.insert("User.insertUser");
        sqlSession1.commit();
        sqlSession1.close();

        //执行Sql
        sqlSession2.insert("insertUser");
        sqlSession2.commit();
        sqlSession2.close();
    }
}
