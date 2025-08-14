package multi.threading.J_CountDownLatch_Race;

import java.util.concurrent.CountDownLatch;

/*
    https://www.youtube.com/watch?v=Yd46R9E0Cj8
* */
public class RaceMain {
    private static final int NUM_PARTICIPANTS = 10;

    public static void main(String[] args) {
        CountDownLatch readyLatch = new CountDownLatch(NUM_PARTICIPANTS);
        CountDownLatch startLatch = new CountDownLatch(1);

        // Start referee thread
        new Thread(new Referee(readyLatch, startLatch)).start();

        // Start participant threads
        for (int i = 1; i <= NUM_PARTICIPANTS; i++) {
            new Thread(new Participant(i, readyLatch, startLatch)).start();
        }
    }
}
