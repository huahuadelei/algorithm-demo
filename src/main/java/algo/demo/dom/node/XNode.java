package algo.demo.dom.node;

import algo.demo.dom.XMLParaser;
import java.util.List;

import java.util.Properties;

public class XNode {

    private String qName;
    private Properties attributes;
    private Properties virables;

    private Object node;
    private XMLParaser xmlParaser;

    public XNode(Object node, XMLParaser xmlParaser, Properties virables) {
        this.node = node;
        this.xmlParaser = xmlParaser;
        this.virables = virables;

        // 初始化
        qName = xmlParaser.getQname(node);
        attributes = parseAttributes(xmlParaser.getAttributes(node));
    }

    private Properties parseAttributes(Properties attributes) {
        if (virables == null || virables.isEmpty()) {
            return attributes;
        }

        return null;
    }

    public List<XNode> children() {
        return xmlParaser.children(node, this.virables);
    }

    public List<XNode> eval(String expression) {
        return xmlParaser.eval(node,expression,virables);
    }

    public XNode evalFirst(String expression) {
        List<XNode> xn;
        return (xn = xmlParaser.eval(node,expression,virables)).isEmpty() ? null : xn.get(0);
    }

    public String getQName() {
        return qName;
    }


    public Properties getAttributes() {
        return attributes;
    }

    public String getAttribute(String propertyName) {
        return attributes.getProperty(propertyName);
    }

    public Object getNode() {
        return node;
    }

}
