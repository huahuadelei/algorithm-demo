package algo.demo.reflect.meta;

import java.util.Map;

public class MapObjectWrapper implements ObjectWrapper {

    private Map<String,Object> target;

    public MapObjectWrapper(Map<String,Object> target) {
        this.target=target;
    }

    @Override
    public Object get(String property) {
        return target.get(property);
    }

    @Override
    public void set(String property, Object value) {
        target.put(property,value);
    }

    @Override
    public boolean hasProperty(String property) {
        return target.containsKey(property);
    }
}
