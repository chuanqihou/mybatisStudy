package com.chuaniqhou.stu;

import com.chuaniqhou.stu.pojo.User;
import com.chuanqihou.cqhbatis.core.SqlSession;
import com.chuanqihou.cqhbatis.core.SqlSessionFactory;
import com.chuanqihou.cqhbatis.core.SqlSessionFactoryBuilder;
import com.chuanqihou.cqhbatis.util.Resource;
import org.junit.Test;

/**
 * @author 传奇后
 * @date 2023/4/14 19:41
 * @description
 */
public class CqhBatisTest {

    @Test
    public void testSqlSessionFactory() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.builder(Resource.getResourceAsStream("cqhbatis-config.xml"));
        System.out.println(sqlSessionFactory);
    }

    @Test
    public void testInsert() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.builder(Resource.getResourceAsStream("cqhbatis-config.xml"));
        SqlSession session = sqlSessionFactory.openSession();

        User user = new User("1004", "李四", "23");

        int insert = session.insert("user.insertUser", user);

        System.out.println(insert);

        session.commit();
        session.close();
    }

    @Test
    public void testSelectOne() {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.builder(Resource.getResourceAsStream("cqhbatis-config.xml"));
        SqlSession session = sqlSessionFactory.openSession();

        Object o = session.selectOne("user.selectUserById", "1004");
        System.out.println(o);
    }

}
