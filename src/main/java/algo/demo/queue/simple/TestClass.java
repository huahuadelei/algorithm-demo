package algo.demo.queue.simple;

public class TestClass {

    public static void main(String[] args) {
//        Queue<Integer> queue = new ArrayQueue<>(3);
        Queue<Integer> queue = new LinkedQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());

        System.out.println("---------------");
        queue.add(4);
        System.out.println(queue.pop());
        queue.add(5);
        System.out.println(queue.pop());
        queue.add(6);
        System.out.println(queue.pop());
        queue.add(7);
        System.out.println(queue.pop());
        queue.add(8);
        System.out.println(queue.pop());

    }

}
