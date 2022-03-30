package algo.demo.dom.node;

import java.util.Map;

import java.util.Collections;

public class ScriptBuildContext {

    private Map<String,Object> parameterMap;
    private StringBuilder sqlBuilder;

    public ScriptBuildContext(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
        this.sqlBuilder = new StringBuilder();
    }

    public String getSql(){
        String sql = sqlBuilder.toString().trim();
        sql = sql.replaceAll("(\\s|\\n)+"," ");
        return sql;
    }

    public Map<String,Object> getBinds(){
        return parameterMap;
    }


    public void appendSql(String trim) {
        sqlBuilder.append(trim);
    }
}
