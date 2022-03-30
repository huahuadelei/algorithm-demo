package algo.demo.dom.parser;

import algo.demo.dom.XMLParaser;
import algo.demo.dom.node.XNode;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DomXmlParser implements XMLParaser {

    private final Map<Class<?>, Object> caches = new HashMap<>();

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        Object obj = caches.get(DocumentBuilder.class);
        if (obj == null) {
            synchronized (this) {
                obj = caches.get(DocumentBuilder.class);
                if (obj == null) {
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    obj = documentBuilderFactory.newDocumentBuilder();
                    caches.put(DocumentBuilder.class, obj);
                }
            }
        }

        return (DocumentBuilder) obj;
    }

    private XPath getXPath() {
        Object obj = caches.get(XPath.class);
        if (obj == null) {
            synchronized (this) {
                obj = caches.get(XPath.class);
                if (obj == null) {
                    XPathFactory xPathFactory = XPathFactory.newInstance();
                    obj = xPathFactory.newXPath();
                    caches.put(XPath.class, obj);
                }
            }
        }

        return (XPath) obj;
    }

    @Override
    public XNode parseRoot(InputStream inputStream) {
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = getDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            Element documentElement = document.getDocumentElement();

            return new XNode(documentElement, this, null);
        } catch (Exception e) {
            throw new RuntimeException("解析异常", e);
        }

    }

    @Override
    public List<XNode> eval(Object node ,String expression,Properties virables) {
        XPath xPath = getXPath();
        try {
            NodeList nodeList = (NodeList) xPath.evaluate(expression, node, XPathConstants.NODESET);
            return nodeListToXNode(nodeList,virables);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("解析异常",e);
        }
    }

    @Override
    public String getQname(Object node) {
        if (node == null) {
            return null;
        } else if (!(node instanceof Node)) {
            throw new IllegalArgumentException("node is not a [org.w3c.dom.Node] instance");
        }
        return ((Node) node).getNodeName();
    }

    @Override
    public String getBody(Object node) {
        if (node == null) {
            return null;
        } else if (!(node instanceof Node)) {
            throw new IllegalArgumentException("node is not a [org.w3c.dom.Node] instance");
        }

        if(((Node)node).getNodeType() == Node.TEXT_NODE||((Node)node).getNodeType() == Node.CDATA_SECTION_NODE){
            return ((CharacterData) node).getData();
        }
        return ((Node)node).getTextContent();
    }

    @Override
    public Properties getAttributes(Object node) {
        if (node == null) {
            return null;
        } else if (!(node instanceof Node)) {
            throw new IllegalArgumentException("node is not a [org.w3c.dom.Node] instance");
        }

        Properties properties = new Properties();

        NamedNodeMap attributes = ((Node) node).getAttributes();
        if(attributes != null&&attributes.getLength()!=0){
            for (int i = 0; i < attributes.getLength(); i++) {
                Node item = attributes.item(i);
                String nodeName = item.getNodeName();
                String nodeValue = item.getNodeValue();
                properties.setProperty(nodeName,nodeValue);
            }
        }


        return properties;
    }

    @Override
    public List<XNode> children(Object node,Properties virables) {
        if (node == null) {
            return null;
        } else if (!(node instanceof Node)) {
            throw new IllegalArgumentException("node is not a [org.w3c.dom.Node] instance");
        }

        NodeList childNodes = ((Node) node).getChildNodes();
        return  nodeListToXNode(childNodes,virables);
    }

    private List<XNode> nodeListToXNode(NodeList childNodes,Properties virables) {
        ArrayList<XNode> arrayList = new ArrayList<>(childNodes.getLength());

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            arrayList.add(new XNode(item,this,virables));

        }
        return arrayList;
    }
}
