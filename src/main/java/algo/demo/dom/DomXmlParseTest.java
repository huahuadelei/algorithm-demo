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
//        DocumentBuilder documentBuild = getDocumentBuild();
//        documentBuild.setErrorHandler(new ErrorHandler() {
//            @Override
//            public void warning(SAXParseException exception) throws SAXException {
//                throw exception;
//            }
//
//            @Override
//            public void error(SAXParseException exception) throws SAXException {
//                throw exception;
//            }
//
//            @Override
//            public void fatalError(SAXParseException exception) throws SAXException {
//                throw exception;
//            }
//        });
//
//        URL resource = DomXmlParse.class.getResource("cdemo.xml");
//
//        Document document = documentBuild.parse(resource.openStream());
//
//        Element documentElement = document.getDocumentElement();
//
//        XPath xPath = getXPathInstance();
//
//        NodeList nodeList = (NodeList) xPath.evaluate("//sqls/sql", documentElement, XPathConstants.NODESET);
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node item = nodeList.item(i);
//            String qname = item.getNodeName();
//            NamedNodeMap attributes = item.getAttributes();
//            String name = attributes.getNamedItem("name").getNodeValue();
//            String paramRoot = attributes.getNamedItem("paramRoot").getNodeValue();
//
//
//            System.out.printf("标签名称：%s , name = %s ,paramRoot = %s, children=%s \n", qname,name,paramRoot,item.getTextContent());
//
//            NodeList childNodes = item.getChildNodes();
//        }


        XMLParserFactory xmlParserFactory = new DomXMLParserFactory();
        XMLParaser xmlParser = xmlParserFactory.createXMLParser();


        XNode xNode = xmlParser.parseRoot(DomXmlParseTest.class.getResourceAsStream("cdemo.xml"));
        List<XNode> nodeList = xNode.eval("//sqls/sql");

        List<SqlSource> sqlSources = new ArrayList<>();

        for (XNode node : nodeList) {
            SqlSource sqlSource = parseSqlSource(node);
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

    private static SqlSource parseSqlSource(XNode node) {
        ScriptNode scriptNode = buildSqlSource((Node) node.getNode());
        return new DynamicSqlSource(scriptNode);
    }

    private static ScriptNode buildSqlSource(Node node) {

        List<ScriptNode> scriptNodes = new ArrayList<>();
        NodeList childNodes = node.getChildNodes();

        if (childNodes != null && childNodes.getLength() != 0) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                String nodeName = item.getNodeName();

                if (item.getNodeType() == Node.TEXT_NODE) {
                    scriptNodes.add(new StaticTextScriptNode(((CharacterData) item).getData()));
                } else {
                    // 处理脚本标签
                    if (nodeName.toUpperCase(Locale.ENGLISH).equals("WHERE")) {

                        ScriptNode scriptNode = buildSqlSource(item);
                        WhereScriptNode where = new WhereScriptNode(scriptNode);
                        scriptNodes.add(where);

                    } else if (nodeName.toUpperCase(Locale.ENGLISH).equals("IF")) {
                        ScriptNode scriptNode = buildSqlSource(item);

                        String test = null;

                        NamedNodeMap attributes = item.getAttributes();
                        if (attributes != null) {
                            for (int k = 0; k < attributes.getLength(); k++) {
                                Node n = attributes.item(k);
                                if (n.getNodeName().toUpperCase(Locale.ENGLISH).equals("TEST")) {
                                    test = n.getNodeValue();
                                    break;
                                }
                            }
                        }

                        if (test == null) {
                            throw new RuntimeException("if [test] property is not null");
                        }

                        IfScriptNode ifScriptNode = new IfScriptNode(test, scriptNode);
                        scriptNodes.add(ifScriptNode);
                    }
                }

            }
        }

        return new MultiScriptNode(scriptNodes);
    }


}
