package algo.demo.reflect.test;

public class Dog  extends Animal{

    private String color;

    public Dog(String color,String name) {
        this.color = color;
        this.setName(name);
    }

    public Dog() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDogColor(Integer color){
        switch (color){
            case 1:
                this.color = "白";
                break;
            case 2:
                this.color = "黑";
                break;
        }
    }

    @Override
    public void wang() {
        System.out.printf("%s色的小狗\"%s\"在汪汪叫\n");
    }
}
