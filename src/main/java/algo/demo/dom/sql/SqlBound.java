package algo.demo.dom.sql;

import algo.demo.list.List;
import java.util.Map;

public class SqlBound {

    private String rawSql;
    private List<String> parameters;
    private Map<String,Object> binds;

    public SqlBound(String rawSql, List<String> parameters, Map<String, Object> binds) {
        this.rawSql = rawSql;
        this.parameters = parameters;
        this.binds = binds;
    }

    public String getRawSql() {
        return rawSql;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Map<String, Object> getBinds() {
        return binds;
    }
}
