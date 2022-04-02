package algo.demo.dom;

import algo.demo.dom.factory.DomXMLParserFactory;
import algo.demo.dom.node.*;

import java.util.*;

import algo.demo.dom.sql.SqlBound;
import algo.demo.dom.sql.SqlSource;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class DomXmlParseTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {


        XMLParserFactory xmlParserFactory = new DomXMLParserFactory();
        XMLParaser xmlParser = xmlParserFactory.createXMLParser();


        XNode xNode = xmlParser.parseRoot(DomXmlParseTest.class.getResourceAsStream("cdemo.xml"));
        List<XNode> nodeList = xNode.eval("//sqls/sql");

        List<SqlSource> sqlSources = new ArrayList<>();

        SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder();

        for (XNode node : nodeList) {
            SqlSource sqlSource = sqlSourceBuilder.buildSqlSource(node);
            sqlSources.add(sqlSource);
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "zhangsan");
        hashMap.put("sex", "男");

        SqlBound sqlBound = sqlSources.get(1).getSqlBound(hashMap);


        System.out.println("==================================");
        System.out.println("SQL ==> " +sqlBound.getRawSql());

        for (int i = 0; i < sqlBound.getParameters().size(); i++) {
            String param = sqlBound.getParameters().get(i);
            System.out.println("参数 ==> "+param);
        }
    }

}
