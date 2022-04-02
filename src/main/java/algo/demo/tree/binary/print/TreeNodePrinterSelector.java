package algo.demo.tree.binary.print;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TreeNodePrinterSelector {

    private static final Map<PrintMode, TreeNodePrinter> PRINTER_MAP = new ConcurrentHashMap<>();

    private static TreeNodePrinter create(PrintMode mode) {

        TreeNodePrinter printer = null;
        switch (mode) {
            case PRE:
                printer = new PreTreeNodePrinter();
                break;
            case INFIX:
                printer = new InfixTreeNodePrinter();
                break;
            case POST:
                printer = new PostTreeNodePrinter();
                break;
        }

        if (printer == null) {
            throw new RuntimeException("打印器初始化失败");
        }
        return printer;
    }

    public static TreeNodePrinter select(PrintMode mode) {
        return PRINTER_MAP.computeIfAbsent(mode, TreeNodePrinterSelector::create);
    }
}
