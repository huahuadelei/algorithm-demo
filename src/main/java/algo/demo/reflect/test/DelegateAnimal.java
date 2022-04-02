package algo.demo.reflect.test;

public class DelegateAnimal extends Animal{

    private Animal delegate;

    public DelegateAnimal(Animal delegate) {
        this.delegate = delegate;
    }

    @Override
    public void wang() {
        delegate.wang();
    }
}
