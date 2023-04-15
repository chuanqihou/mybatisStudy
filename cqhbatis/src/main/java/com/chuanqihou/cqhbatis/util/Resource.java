package com.chuanqihou.cqhbatis.util;

import java.io.InputStream;

/**
 * cqhbatis提供的一个工具类
 * 该类可以加载”类路径“下的资源
 * @author 传奇后
 * @date 2023/4/13 19:57
 * @description
 */
public class Resource {

    /**
     * 工具类的构造方法建议私有化
     * 工具类中的方法都是静态的，不需要创建对象就能调用
     * 为了避免new对象，将构造方法私有化
     */
    private Resource(){}

    /**
     * 从类路径中加载资源
     * @param resource  在类路径中的资源
     * @return  指向该资源的输入流
     */
    public static InputStream getResourceAsStream(String resource) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }
}
