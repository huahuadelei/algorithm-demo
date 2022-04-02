package algo.demo.reflect.test;

public abstract class Animal {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void wang();

    public void run(){
        wang();
        System.out.println("小动物一边 wang 一边跑");
    }
}
