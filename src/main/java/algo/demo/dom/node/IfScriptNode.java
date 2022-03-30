package algo.demo.dom.node;

import algo.demo.dom.evaluator.ExpressionEvaluator;
import algo.demo.dom.evaluator.JexlExpressionEvaluator;
import algo.demo.dom.evaluator.OgnlExpressionEvaluator;


public class IfScriptNode implements ScriptNode {

    private static final ExpressionEvaluator EVALUATOR;
    static {
//        EVALUATOR = new OgnlExpressionEvaluator();
        EVALUATOR = new JexlExpressionEvaluator();
    }

    private String test;
    private ScriptNode sqlNode;

    public IfScriptNode(String test, ScriptNode sqlNode) {
        this.test = test;
        this.sqlNode = sqlNode;
    }

    @Override
    public boolean apply(ScriptBuildContext context) {
        boolean test = (Boolean) EVALUATOR.eval(this.test, context.getBinds());

        if (test) {
            sqlNode.apply(context);
        }
        return true;
    }
}
