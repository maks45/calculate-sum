import java.util.List;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Callable<Integer>> calculators = new ListGenerator().getCalculators();
        Calculator executorCalculator = new ExecutorServiceCalculator(ListGenerator.CORES_COUNT);
        Calculator forkJoinPoolCalculator = new ForkJoinPoolCalculator(ListGenerator.CORES_COUNT);
        System.out.println("ExecutorService sum: " + executorCalculator.calculate(calculators));
        System.out.println("ForkJoin sum: " + forkJoinPoolCalculator.calculate(calculators));
    }
}
