import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class Calculator {
    protected final int threadsCount;

    public Calculator(int threadsCount) {
        this.threadsCount = threadsCount;
    }

    protected int execute(List<Future<Integer>> futures) {
        return futures.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("can't get future result");
            }
        }).reduce(0, Integer::sum);
    }

    public abstract int calculate(List<Callable<Integer>> calculators) throws InterruptedException;
}
