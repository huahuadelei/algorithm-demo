package algo.demo.dom.node;

import java.util.ArrayList;
import java.util.List;

public class MultiScriptNode  implements ScriptNode{
    private List<ScriptNode> scriptNodeList;

    public MultiScriptNode(List<ScriptNode> scriptNodeList) {
        this.scriptNodeList = scriptNodeList;
    }

    @Override
    public boolean apply(ScriptBuildContext context) {
        for (ScriptNode scriptNode : scriptNodeList) {
            boolean apply = scriptNode.apply(context);
            if(!apply){
                return false;
            }
        }
        return true;
    }
}
