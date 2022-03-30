package algo.demo.dom.evaluator;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.HashMap;
import java.util.Map;

public class JexlExpressionEvaluator implements ExpressionEvaluator {

    static final JexlEngine jexlEngine;

    static {
        jexlEngine = new JexlEngine();
        jexlEngine.setSilent(true);

        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("b",123);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("a",objectHashMap);

        Object property = jexlEngine.getProperty(hashMap, "a.b");
        System.out.println();
    }

    @Override
    public Object eval(String expression, Map<String, Object> context) {
        JexlContext jexlContext = new MapContext();
        context.forEach(jexlContext::set);

        Expression expression1 = jexlEngine.createExpression(expression);
        Object evaluate = expression1.evaluate(jexlContext);
        return evaluate;
    }
}
