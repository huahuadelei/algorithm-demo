package algo.demo.dom.evaluator;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import java.util.Map;

public class OgnlExpressionEvaluator  implements ExpressionEvaluator{
    @Override
    public Object eval(String expression, Map<String, Object> context) {
        OgnlContext context1 = new OgnlContext();
        context1.putAll(context);
        try {
            return Ognl.getValue(expression, context1);
        } catch (OgnlException e) {
            return false;
        }
    }
}
