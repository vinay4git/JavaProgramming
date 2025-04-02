package multi.threading;

import java.util.concurrent.atomic.AtomicInteger;

public class InConsistencyRaceConditionOrder3 {
    //https://youtu.be/F2nuvXJHYxQ?t=1783
    public static void main(String[] args) {
        SharedObject sharedObject = new SharedObject();
        //AtomicSharedObject sharedObject = new AtomicSharedObject();

        new Thread(() -> {
            System.out.println("t1 Started");
            for (int value = 0; value < 50000; value++) {
                sharedObject.increment();
            }
            System.out.println("t1 Ended");
        }).start();

        new Thread(() -> {
            System.out.println("t2 Started");
            for (int value = 0; value < 50000; value++) {
                sharedObject.increment();
            }
            System.out.println("t2 Ended");
        }).start();

        System.out.println("Main Ended with counter value = " + sharedObject.getCounter());
    }
}

class SharedObject {
    private int counter = 0;

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