import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Job {


    public static AtomicInteger count = new AtomicInteger(0);
    public static int numberOfThreads = 100;

    public static void main(String[] args) {

     
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            callables.add(new MyCallable());
        }

   
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        try {
     
            List<Future<Integer>> futures = executorService.invokeAll(callables);

         
            for (Future<Integer> future : futures) {
                count.addAndGet(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
      
            executorService.shutdown();
        }

        System.out.println(count);
    }
}


class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() {
        int localCount = 0;
        for (int x = 0; x < 1_000_000; x++) {
            localCount++;
        }
        return localCount;
    }
}
