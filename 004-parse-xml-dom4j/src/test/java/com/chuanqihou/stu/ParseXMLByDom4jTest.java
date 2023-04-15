package com.chuanqihou.stu;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/13 18:33
 * @description
 */
public class ParseXMLByDom4jTest {

    @Test
    public void testParseMybatisConfigXml() throws Exception {
        //创建SAXReader对象
        SAXReader saxReader = new SAXReader();
        //获取输入流
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        //读取XML文件，返回Document文档对象，代表整个XML文件
        Document document = saxReader.read(is);

        //获取文档中的根标签
        Element rootElement = document.getRootElement();
        //获取根标签的的名字
        String rootName = rootElement.getName();
        //System.out.println("根节点名字："+rootName);

        //获取environments标签中default属性的值（即获取默认环境配置的id）
        //xpath是做标签路径匹配的，能快速定位XML中的元素
        //以下xpath的值代表从根下开始寻找configuration标签，然后寻找configuration标签下的子标签environments
        String xpath = "/configuration/environments";
        //找到该节点对象
        //Node node = document.selectSingleNode(xpath);
        //Element是node的子类，方法更多，将其强转
        Element element = (Element) document.selectSingleNode(xpath);
        //获取属性的值
        String defaultEnvironmentId = element.attributeValue("default");
        //System.out.println("默认环境id:"+defaultEnvironmentId);

        //根据默认环境id获取environment
        xpath = "/configuration/environments/environment[@id='" + defaultEnvironmentId + "']";
        //System.out.println(xpath);
        //获得默认环境标签
        Element environment = (Element) document.selectSingleNode(xpath);
        //获取子environment的子标签（节点）transactionManager
        Element transactionMangerElement = environment.element("transactionManager");
        String transactionType = transactionMangerElement.attributeValue("type");
        System.out.println("事物管理类型：" + transactionType);

        //获取dataSource
        Element dataSourceElement = environment.element("dataSource");
        String dataSourceType = dataSourceElement.attributeValue("type");
        System.out.println("数据源类型：" + dataSourceType);

        //获取properties标签
        List<Element> propertiesElements = dataSourceElement.elements();
        for (Element propertiesElement : propertiesElements) {
            String name = propertiesElement.attributeValue("name");
            String value = propertiesElement.attributeValue("value");
            System.out.println(name + "=" + value);
        }

        //获取所有的mapper标签（不想从根来时寻找，使用//）
        xpath = "//mapper";
        List<Node> mappers = document.selectNodes(xpath);
        for (Node mapperNode : mappers) {
            Element mapper = (Element) mapperNode;
            String resource = mapper.attributeValue("resource");
            System.out.println(resource);
        }
    }

    @Test
    public void testParseSqlMapperXml() throws Exception {
        SAXReader saxReader = new SAXReader();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("UserMapper.xml");
        Document document = saxReader.read(is);
        String xpath = "/mapper";
        Element mapperElement = (Element) document.selectSingleNode(xpath);
        String namespaceValue = mapperElement.attributeValue("namespace");
        //System.out.println(namespaceValue);

        List<Element> elements = mapperElement.elements();
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            System.out.println(id);
            System.out.println(resultType);

            String sql = element.getTextTrim();
            System.out.println(sql);

            String s = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            System.out.println(s);
        }
    }

}
