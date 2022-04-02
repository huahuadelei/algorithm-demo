package algo.demo.reflect.meta;

public class PropertyTokenizer {

    private final String delim;
    private final String current;
    private final String children;

    public PropertyTokenizer(String content, String delim) {
        this.delim = delim;
        int index = content.indexOf(delim);

        if (index != -1) {
            current = content.substring(0, index);
            children = index == content.length() - 1 ? null : content.substring(index + 1);
        }else{
            current = content;
            children = null;
        }
    }

    public PropertyTokenizer(String content) {
        this(content, ".");
    }

    public String getCurrent() {
        return current;
    }

    public String getChildren(){
        return children;
    }

    public boolean hasNext() {
        return children != null;
    }

    public PropertyTokenizer next() {
        return new PropertyTokenizer(children);
    }
}
