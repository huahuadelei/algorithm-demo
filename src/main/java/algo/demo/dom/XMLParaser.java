package algo.demo.dom;

import algo.demo.dom.node.XNode;
import java.util.List;

import java.io.InputStream;
import java.util.Properties;

public interface XMLParaser {

    XNode parseRoot(InputStream inputStream);

    List<XNode> eval(Object node,String expression,Properties virables);

    String getQname(Object node);

    String getBody(Object node);

    Properties getAttributes(Object node);

    List<XNode> children(Object node,Properties virables);
}
