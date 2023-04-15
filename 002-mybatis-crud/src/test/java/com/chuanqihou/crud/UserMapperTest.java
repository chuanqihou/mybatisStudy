package com.chuanqihou.crud;

import com.chuaniqhou.crud.bean.User;
import com.chuaniqhou.crud.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 传奇后
 * @date 2023/4/12 8:56
 * @description
 */
public class UserMapperTest {

    @Test
    public void insertUserOld() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();

        int insertUser = sqlSession.insert("insertUserOld");
        System.out.println(insertUser);
        sqlSession.close();
    }

    @Test
    public void insertUserMap() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();

        Map<String, Object> map = new HashMap<>();

        map.put("name", "赵六2");
        map.put("password", "zhaoliu2");
        map.put("age", 25);

        int insertUserMap = sqlSession.insert("insertUserMap", map);

        System.out.println(insertUserMap);

        sqlSession.close();
    }

    @Test
    public void insertUser() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();

        User user = new User();
        user.setUserName("赵六3");
        user.setUserPassword("zhaoliu3");
        user.setUserAge(23);
        int insertUser = sqlSession.insert("insertUser", user);

        System.out.println(insertUser);

        sqlSession.close();
    }

    @Test
    public void deleteUserById() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        int deleteUserById = sqlSession.delete("deleteUserById", 18);
        System.out.println(deleteUserById);
        sqlSession.close();
    }

    @Test
    public void updateUserById() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        User user = new User(17L,"张三","zhangsan",30);
        int updateUserById = sqlSession.update("updateUserById", user);
        System.out.println(updateUserById);
        sqlSession.close();
    }

    @Test
    public void testBoolean() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        com.chuaniqhou.crud.bean.Test t = new com.chuaniqhou.crud.bean.Test(9,true);
        sqlSession.insert("test",t);
    }

    @Test
    public void selectUserById() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Object user = sqlSession.selectOne("selectUserById", 1);
        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void selectUserById2() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Object user = sqlSession.selectOne("selectUserById2", 1);
        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void selectAllUser() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        List<User> selectAllUser = sqlSession.selectList("selectAllUser");

        selectAllUser.forEach(System.out::println);

        sqlSession.close();
    }

    @Test
    public void testNameSpace() {
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        List<User> selectAllUser = sqlSession.selectList("user.selectAllUser");

        selectAllUser.forEach(System.out::println);

        sqlSession.close();
    }

}