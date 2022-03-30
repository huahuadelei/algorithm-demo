package algo.demo.dom.node;

public class StaticTextScriptNode implements ScriptNode{

    private String sql;

    public StaticTextScriptNode(String sql) {
        this.sql = sql;
    }

    @Override
    public boolean apply(ScriptBuildContext context) {
        context.appendSql(sql);
        return true;
    }
}
