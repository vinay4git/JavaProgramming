package multi.threading.race;

import java.util.concurrent.CountDownLatch;

class Participant implements Runnable {
    private int id;
    private CountDownLatch readyLatch;
    private CountDownLatch startLatch;

    public Participant(int id, CountDownLatch readyLatch, CountDownLatch startLatch) {
        this.id = id;
        this.readyLatch = readyLatch;
        this.startLatch = startLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("Participant " + id + ": Getting ready...");
            Thread.sleep((long) (Math.random() * 2000));  // Simulate preparation time
            System.out.println("Participant " + id + ": Ready at the line.");
            readyLatch.countDown();  // Signal readiness
            startLatch.await();  // Wait for referee to start race
            System.out.println("Participant " + id + ": Running!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
