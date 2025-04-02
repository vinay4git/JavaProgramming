package multi.threading;

public class JavaThreadOrder1 {
    public static void main(String[] args) {
        System.out.println("Hello world");

        //Older way of creating thread
        MyThread t = new MyThread();
        t.start();

        // Lambda Expression Way
        new Thread(() -> {
            System.out.println("Running My Thread 2");
        }).start();

        // Runnable way
        Runnable objR = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running My Thread 3");
            }
        };
        Thread newR = new Thread(objR, "Runnable Thread");
        newR.start();

        // Better way of runnable
        Runnable objBetterRunnable = () -> System.out.println("Running My Thread 4");
        Thread newRunnable = new Thread(objBetterRunnable, "Better Runnable Thread");
        newRunnable.start();

        // Making a thread asyncronous
        Runnable asyncRunnable = () -> {
            System.out.println("Running My Thread 5 in Async");
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread 5 stopped");
        };
        Thread asyncThread = new Thread(asyncRunnable, "runnable-thread-in-async");
        asyncThread.setDaemon(true);
        asyncThread.start();

        System.out.println("Main Thread ended");
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running My Thread 1");
    }
}
