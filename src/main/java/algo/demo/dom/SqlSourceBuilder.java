package algo.demo.dom;

import algo.demo.dom.node.*;
import algo.demo.dom.sql.SqlSource;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class SqlSourceBuilder {
    static final Map<String, ScriptNodeHandler> SCRIPT_NODE_HANDLER_MAP = new HashMap<>();

    static {
        SCRIPT_NODE_HANDLER_MAP.put("WHERE", new WhereScriptNodeHandler());
        SCRIPT_NODE_HANDLER_MAP.put("IF", new IfScriptNodeHandler());
    }

    public SqlSource buildSqlSource(XNode node) {
        ScriptNode scriptNode = SqlSourceBuilder.parseScriptNode((Node) node.getNode());
        return new DynamicSqlSource(scriptNode);
    }


    private static ScriptNode parseScriptNode(Node node) {

        List<ScriptNode> scriptNodes = new ArrayList<>();
        NodeList childNodes = node.getChildNodes();

        if (childNodes != null && childNodes.getLength() != 0) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                String nodeName = item.getNodeName();

                if (item.getNodeType() == Node.TEXT_NODE) {
                    scriptNodes.add(new StaticTextScriptNode(((CharacterData) item).getData()));
                } else {
                    String scriptName = nodeName.toUpperCase(Locale.ENGLISH);
                    // 处理脚本标签
                    ScriptNodeHandler scriptNodeHandler = SCRIPT_NODE_HANDLER_MAP.get(scriptName);
                    if(scriptNodeHandler!=null){
                         scriptNodeHandler.handleScriptNode(item,scriptNodes);
                    }
                }

            }
        }

        return new MultiScriptNode(scriptNodes);
    }

    interface ScriptNodeHandler {
        void handleScriptNode(Node node, List<ScriptNode> nodeList);
    }

    static class IfScriptNodeHandler implements ScriptNodeHandler {

        @Override
        public void handleScriptNode(Node item, List<ScriptNode> scriptNodes) {
            ScriptNode scriptNode = parseScriptNode(item);

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

    static class WhereScriptNodeHandler implements ScriptNodeHandler {

        @Override
        public void handleScriptNode(Node item, List<ScriptNode> scriptNodes) {
            ScriptNode scriptNode = parseScriptNode(item);
            WhereScriptNode where = new WhereScriptNode(scriptNode);
            scriptNodes.add(where);
        }
    }
}
