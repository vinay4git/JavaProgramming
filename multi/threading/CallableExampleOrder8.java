package multi.threading;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableExampleOrder8 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyCallable());

        new Thread(futureTask).start();

        System.out.println(futureTask.get());
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 10;
    }
}