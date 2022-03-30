package algo.demo.dom.node;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class WhereScriptNode extends TrimScrpitNode {

    static final Set<String> prefixArray = new HashSet<String>();

    static {
        prefixArray.add("OR");
        prefixArray.add("AND");
    }

    public WhereScriptNode(ScriptNode sqlNode) {
        super(null, null, sqlNode);
    }

    @Override
    public boolean apply(ScriptBuildContext context) {
        ScriptBuildContext scriptBuildContext = new ScriptBuildContext(context.getBinds());

        super.apply(scriptBuildContext);
        String sql = scriptBuildContext.getSql();
        if(sql.trim().length()==0){
            return true;
        }

        sql = removePrefix(sql);

        sql = "WHERE " + sql;

        context.appendSql(sql);

        return true;
    }

    private String removePrefix(String sql) {

        for (String prefix : prefixArray) {
            if(sql.toUpperCase(Locale.ENGLISH).startsWith(prefix)){
                return sql.substring(prefix.length());
            }
        }

        return sql;
    }
}
