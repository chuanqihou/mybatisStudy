package com.chuanqihou.cqhbatis.core;

import com.chuanqihou.cqhbatis.util.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SqlSessionFactoryBuilder构造器对象
 * 通过SqlSessionFactoryBuilder中的builder方法解析
 * cqhbatis-config.xml文件，创建并返回SqlSessionFactory对象
 *
 * @author 传奇后
 * @date 2023/4/13 20:05
 * @description
 */
public class SqlSessionFactoryBuilder {

    /**
     * 解析cqhbatis-config.xml文件，构建并返回SqlSessionFactory对象
     * @param inputStream 指向cqhbatis-config.xml文件的一个输入流
     * @return  SqlSessionFactory对象
     */
    public SqlSessionFactory builder(InputStream inputStream) {
        SqlSessionFactory sqlSessionFactory = null;

        try {
            //解析cqhbatis-config.xml文件

            //创建SAXReader对象
            SAXReader saxReader = new SAXReader();
            //读取XML文件，返回Document文档对象，代表整个XML文件
            Document document = saxReader.read(inputStream);

            //获取environments标签中default属性的值（即获取默认环境配置的id）
            Element environments = (Element) document.selectSingleNode("/configuration/environments");
            String defaultEnvironmentId = environments.attributeValue("default");
            //根据默认环境id获取environment
            Element environment = (Element) document.selectSingleNode("/configuration/environments/environment[@id='" + defaultEnvironmentId + "']");
            //获得transactionManagerElement标签
            Element transactionMangerElement = environment.element("transactionManager");
            //获取dataSourceElement标签
            Element dataSourceElement = environment.element("dataSource");
            //获取所有的mapper标签（不想从根来时寻找，使用//）
            List<String> sqlMapperXMLPathList = new ArrayList<>();
            List<Node> mappers = document.selectNodes("//mapper");
            for (Node mapperNode : mappers) {
                Element mapper = (Element) mapperNode;
                String resource = mapper.attributeValue("resource");
                sqlMapperXMLPathList.add(resource);
            }

            //获取数据源对象
            DataSource dataSource = getDataSource(dataSourceElement);
            //获取事务管理器
            Transaction transaction = getTransaction(transactionMangerElement,dataSource);
            //获取mappedStatement
            Map<String,MappedStatement> mappedStatement = getManagedStatement(sqlMapperXMLPathList);

            //构建SqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactory(transaction,mappedStatement);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return sqlSessionFactory;
    }

    /**
     * 解析所有的sqlMapper.xml文件，构建Map集合
     * @param sqlMapperXMLPathList
     * @return
     */
    private Map<String, MappedStatement> getManagedStatement(List<String> sqlMapperXMLPathList) {
        Map<String, MappedStatement> managedStatement = new HashMap<>();

        for (String sqlMapperXmlPath : sqlMapperXMLPathList) {
            try {
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(Resource.getResourceAsStream(sqlMapperXmlPath));
                Element mapperElement = (Element) document.selectSingleNode("/mapper");
                String namespace = mapperElement.attributeValue("namespace");

                List<Element> elements = mapperElement.elements();
                for (Element element : elements) {
                    String id = element.attributeValue("id");
                    String resultType = element.attributeValue("resultType");
                    String sql = element.getTextTrim();

                    String sqlId = namespace + "." + id;
                    MappedStatement mappedStatement = new MappedStatement(sql, resultType);
                    managedStatement.put(sqlId, mappedStatement);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return managedStatement;
    }

    /**
     * 获取事务管理器
     * @param transactionMangerElement  事务管理器元素标签对象
     * @param dataSource    数据源
     * @return  事务管理器对象
     */
    private Transaction getTransaction(Element transactionMangerElement, DataSource dataSource) {
        //获取事务管理器的类型
        String transactionType = transactionMangerElement.attributeValue("type").trim().toUpperCase();
        Transaction transaction = null;
        switch (transactionType) {
            case Const.JDBC_TRANSACTION:
                //默认开启事务
                transaction = new JdbcTransaction(dataSource,false);
                break;
            case Const.JNDI_TRANSACTION:
                transaction = new ManagedTransaction();
                break;
            case Const.MANAGED_TRANSACTION:
                break;
        }
        return transaction;
    }

    /**
     * 获取数据源对象
     * @param dataSourceElement 数据源元素标签
     * @return  数据源对象
     */
    private DataSource getDataSource(Element dataSourceElement) {

        //获取properties标签
        Map<String, String> map = new HashMap<>();
        List<Element> propertiesElements = dataSourceElement.elements("property");
        for (Element propertiesElement : propertiesElements) {
            String name = propertiesElement.attributeValue("name");
            String value = propertiesElement.attributeValue("value");
            map.put(name, value);
        }

        //获取数据源类型 UNPOOLED POOLED JNDI
        String dataSourceType = dataSourceElement.attributeValue("type").trim().toUpperCase();
        DataSource dataSource = null;
        switch (dataSourceType) {
            case Const.UN_POOLED_DATASOURCE:
                dataSource = new UnPooledDataSource(map.get("driver"),map.get("url"),map.get("username"),map.get("password"));
                break;
            case Const.POOLED_DATASOURCE:
                dataSource = new PooledDataSource();
                break;
            case Const.JNDI_DATASOURCE:
                dataSource = new JNDIDataSource();
                break;
        }
        return dataSource;
    }
}
