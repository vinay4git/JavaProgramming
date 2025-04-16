package multi.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class InConsistencyRaceConditionOrder3 {
    //https://youtu.be/F2nuvXJHYxQ?t=1783
    public static void main(String[] args) throws InterruptedException {
        //SharedObject sharedObject = new SharedObject();
        AtomicSharedObject sharedObject = new AtomicSharedObject();

        Thread thread1 = new Thread(() -> {
            System.out.println("t1 Started");
            for (int value = 0; value < 5000; value++) {
                //System.out.println("T1 incrementing");
                sharedObject.increment();
            }
            System.out.println("t1 Ended");
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            System.out.println("t2 Started");
            for (int value = 0; value < 5000; value++) {
                //System.out.println("T2 incrementing");
                sharedObject.increment();
            }
            System.out.println("t2 Ended");
        });
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Main Ended with counter value = " + sharedObject.getCounter());
    }
}

class SharedObject {
    private Integer counter = 0;

    public synchronized void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}


class AtomicSharedObject {
    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.getAndIncrement();
    }

    public int getCounter() {
        return counter.get();
    }
}