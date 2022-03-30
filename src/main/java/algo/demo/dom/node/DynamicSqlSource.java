package algo.demo.dom.node;

import algo.demo.dom.sql.SqlBound;
import algo.demo.dom.sql.SqlSource;
import algo.demo.list.ArrayList;
import algo.demo.list.List;

import java.util.Map;

import java.util.HashMap;

public class DynamicSqlSource implements SqlSource {

    private ScriptNode scriptNode;

    public DynamicSqlSource(ScriptNode scriptNode) {
        this.scriptNode = scriptNode;
    }

    @Override
    public SqlBound getSqlBound(Object parameterObj) {
        ScriptBuildContext scriptBuildContext = new ScriptBuildContext((Map<String,Object>)parameterObj);
        scriptNode.apply(scriptBuildContext);

        SqlBoundbuilder sqlBoundbuilder = new SqlBoundbuilder();
        return sqlBoundbuilder.parse(scriptBuildContext.getSql(), parameterObj, scriptBuildContext.getBinds());
    }


    static class SqlBoundbuilder {

        public SqlBound parse(String sql, Object parameterObj, Map<String, Object> binds) {

            // 解析 ${...}
            ParseSqlJoinerHandler parseSqlJoinerHandler = new ParseSqlJoinerHandler("${", "}", (Map<String, Object>) parameterObj);
            String format = new GenericTokenParser(parseSqlJoinerHandler).parseSql(sql);

            //解析 #{...}
            DefaultTokenParseHandler handler = new DefaultTokenParseHandler("#{", "}");
            String parseSql = new GenericTokenParser(handler).parseSql(format);
            List<String> parameterMappings = handler.getParameterMappings();


            SqlBound sqlBound = new SqlBound(parseSql, parameterMappings, (Map<String, Object>) parameterObj);
            return sqlBound;
        }
    }

    static class GenericTokenParser {

        private TokenParseHandler handler;

        public GenericTokenParser(TokenParseHandler handler) {
            this.handler = handler;
        }

        public String parseSql(String sql) {

            return doParseSql1(sql);

        }

        // select * from user where name=#{name} and age = '#{age}'
        private String doParseSql1(String sql) {
            StringBuilder appender = new StringBuilder();
            int prefixIndex;
            int offset = 0;

            while ((prefixIndex = sql.indexOf(handler.getPrefix(), offset)) != -1) {
                int suffixIndex = sql.indexOf(handler.getSuffix(), prefixIndex + 1);

                // 没有找到后缀
                if (suffixIndex == -1) {
                    break;
                }

                // 拼接前半部分
                appender.append(sql, offset, prefixIndex);

                // 处理表达式属性
                String propertyName = getExpressionProperty(sql, prefixIndex, suffixIndex);
                String handleFormat = handler.handler(propertyName);
                appender.append(handleFormat);

                // offset的值设置为 后缀索引的后一个
                offset = suffixIndex + 1;
            }

            if (offset < sql.length()) {
                appender.append(sql.substring(offset));
            }

            return appender.toString();
        }

        private String getExpressionProperty(String sql, int prefixIndex, int suffixIndex) {
            return sql.substring(prefixIndex + handler.getPrefix().length(), suffixIndex);
        }
    }

    interface TokenParseHandler {
        String handler(String name);

        String getPrefix();

        String getSuffix();
    }

    static class ParseSqlJoinerHandler implements TokenParseHandler {


        private String prefix;
        private String suffix;
        private Map<String, Object> parameterMap;

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public Map<String, Object> getParameterMap() {
            return parameterMap;
        }

        public ParseSqlJoinerHandler(String prefix, String suffix, Map<String, Object> parameterMap) {
            this.prefix = prefix;
            this.suffix = suffix;
            this.parameterMap = parameterMap;
        }

        @Override
        public String handler(String name) {
            return parameterMap.containsKey(name) ? parameterMap.get(name).toString() : name;
        }
    }

    static class DefaultTokenParseHandler implements TokenParseHandler {

        private String prefix;
        private String suffix;

        private List<String> parameterMappings;

        public DefaultTokenParseHandler(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
            this.parameterMappings = new ArrayList<>();
        }

        @Override
        public String handler(String name) {
            this.parameterMappings.add(name);
            return "?";
        }

        public String getPrefix() {
            return prefix;
        }


        public String getSuffix() {
            return suffix;
        }

        public List<String> getParameterMappings() {
            return parameterMappings;
        }

    }

    public static void main(String[] args) {

        String sql = "select \n* \nfrom user \nwhere \nname=#{name} \nand age = ${age}";

        java.util.Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "张三");
        paramMap.put("age", 15);


        // 解析 ${...}
        ParseSqlJoinerHandler parseSqlJoinerHandler = new ParseSqlJoinerHandler("${", "}", (Map<String, Object>) paramMap);
        String format = new GenericTokenParser(parseSqlJoinerHandler).parseSql(sql);


        DefaultTokenParseHandler parseHandler = new DefaultTokenParseHandler("#{", "}");
        String parseSql = new GenericTokenParser(parseHandler).parseSql(format);

        List<String> parameterMappings = parseHandler.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            System.out.println(parameterMappings.get(i));
        }

        System.out.println(parseSql.replaceAll("(\\n|\\s)+", " "));
    }
}
