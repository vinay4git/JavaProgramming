package multi.threading;

public class D_ThreadLocalExample {
    public static void main(String[] args) throws InterruptedException {
        // Here if we see we used one common variable declaration across two threads without any Multi Threading issue.
        ThreadLocal<String> sharedThreadLocal = new ThreadLocal<>();
        new Thread(() -> {
            System.out.println("t1 Started");
                sharedThreadLocal.set("This thread belongs to t1");
            System.out.println(sharedThreadLocal.get());
            sharedThreadLocal.remove();
            System.out.println("t1 Ended");
        }).start();

        new Thread(() -> {
            System.out.println("t2 Started");
            sharedThreadLocal.set("This thread belongs to t2");
            System.out.println(sharedThreadLocal.get());
            sharedThreadLocal.remove();
            System.out.println("t2 Ended");
        }).start();


        Thread.sleep(2000);

        // If you observe we are able to access shared variable data set in t1 inside tc1 also. As tc1 is child of t1
        // And the removal of data in tc1 is not removed in t1. As tc1 has its own duplicated of InheritableThreadLocal.
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

        new Thread(() -> {
            System.out.println("t1 Started");
            inheritableThreadLocal.set("This thread belongs to t1");
            System.out.println(inheritableThreadLocal.get());

            new Thread(() -> {
                System.out.println("tc1 child of t1 Started");
                System.out.println(inheritableThreadLocal.get());
                inheritableThreadLocal.remove();
                System.out.println("tc1 child of t1 Ended");
            }).start();


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(inheritableThreadLocal.get());
            System.out.println("t1 Ended");
        }).start();

    }
}
