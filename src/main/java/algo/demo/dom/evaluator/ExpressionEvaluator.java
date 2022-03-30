package algo.demo.dom.evaluator;

import java.util.Map;

public interface ExpressionEvaluator {

    Object eval(String expression, Map<String,Object> context);

}
