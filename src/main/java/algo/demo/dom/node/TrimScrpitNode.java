package algo.demo.dom.node;

import java.util.Map;

public class TrimScrpitNode  implements ScriptNode{
    private String prefix;
    private String suffix;

    private ScriptNode sqlNode;


    public TrimScrpitNode(String prefix, String suffix, ScriptNode sqlNode) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.sqlNode = sqlNode;
    }

    @Override
    public boolean apply(ScriptBuildContext context) {

        ScriptBuildContext tempContext = new ScriptBuildContext(context.getBinds());
        sqlNode.apply(tempContext);

        String sql = tempContext.getSql();
        return handle0(sql,context);
    }

    private boolean handle0(String sql, ScriptBuildContext context) {
        if(prefix != null){
            sql = prefix.trim()+" "+sql;
        }

        if(suffix != null){
            sql = sql.trim()+" "+suffix;
        }

        String trim = sql.trim();

        context.appendSql(trim);
        return true;
    }
}
