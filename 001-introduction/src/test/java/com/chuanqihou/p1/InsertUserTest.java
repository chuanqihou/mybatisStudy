package com.chuanqihou.p1;

import com.chuanqihou.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author 传奇后
 * @date 2023/4/11 21:17
 * @description
 */
public class InsertUserTest {

    @Test
    public void test1() {

        SqlSession sqlSession = SqlSessionUtil.openSession();

        //sqlSession.insert()

        sqlSession.commit();
        sqlSession.close();

    }

}
