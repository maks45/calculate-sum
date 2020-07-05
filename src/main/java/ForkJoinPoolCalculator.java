import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolCalculator extends Calculator {

    public ForkJoinPoolCalculator(int threadsCount) {
        super(threadsCount);
    }

    @Override
    public int calculate(List<Callable<Integer>> calculators) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadsCount);
        return execute(forkJoinPool.invokeAll(calculators));
    }
}
