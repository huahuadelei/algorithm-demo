package algo.demo.reflect.meta;

import algo.demo.reflect.Reflector;
import algo.demo.reflect.invoker.Invoker;

import java.lang.reflect.InvocationTargetException;

public class BeanObjectWrapper implements ObjectWrapper {

    private Object target;
    private Reflector reflector;

    public BeanObjectWrapper(Object target) {
        this.target = target;
        this.reflector = Reflector.forClass(target.getClass());
    }

    @Override
    public Object get(String property) {
        if(!reflector.hasGetter(property)){
            return null;
        }
        try {
            Invoker getterInvoke = reflector.getGetterInvoke(property);
            return getterInvoke.invoke(target,new Object[0]);
        } catch (Exception e) {
          return null;
        }
    }

    @Override
    public void set(String property, Object value) {
        if(!reflector.hasSetter(property)){
            return;
        }
        Invoker setterInvoke = reflector.getSetterInvoke(property);
        try {
            setterInvoke.invoke(target,new Object[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasProperty(String property) {
        return false;
    }
}
