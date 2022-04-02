package algo.demo.reflect.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Invoker {

    protected boolean fieldInvoke;

    protected Field field;

    protected Method method;

    public Invoker(Field field) {
        this.field = field;
        this.fieldInvoke = true;
    }

    public Invoker(Method method) {
        this.method = method;
        this.fieldInvoke = false;
    }


   public abstract Object invoke(Object target,Object[] args) throws InvocationTargetException, IllegalAccessException;
}
