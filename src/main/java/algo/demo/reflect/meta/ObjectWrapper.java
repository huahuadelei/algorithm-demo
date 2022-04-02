package algo.demo.reflect.meta;

public interface ObjectWrapper {

    Object get(String property);

    void set(String property,Object value);

    boolean hasProperty(String property);
}
