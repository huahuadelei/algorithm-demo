package algo.demo.dom.factory;

import algo.demo.dom.XMLParaser;
import algo.demo.dom.XMLParserFactory;
import algo.demo.dom.parser.DomXmlParser;

public class DomXMLParserFactory implements XMLParserFactory {

    @Override
    public XMLParaser createXMLParser() {
        return new DomXmlParser();
    }
}
