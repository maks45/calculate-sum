import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceCalculator extends Calculator {

    public ExecutorServiceCalculator(int threadsCount) {
        super(threadsCount);
    }

    public int calculate(List<Callable<Integer>> calculators) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        int result = execute(executorService.invokeAll(calculators));
        executorService.shutdown();
        return result;
    }
}
