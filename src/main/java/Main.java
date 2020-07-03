import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final int ELEMENTS_COUNT = 1_000_000;
    private static final int CORES_COUNT = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {
        List<Integer> intsList = IntStream.range(0, ELEMENTS_COUNT)
                .boxed().collect(Collectors.toList());
        ExecutorService executorService = Executors.newFixedThreadPool(CORES_COUNT);
        List<Callable<Integer>> calculators = new ArrayList<>();
        IntStream.range(0, CORES_COUNT).forEach(i -> calculators.add(
                new SumCalculator(intsList.subList(i * (ELEMENTS_COUNT / CORES_COUNT), (i + 1)
                        * (ELEMENTS_COUNT / CORES_COUNT)))));
        List<Future<Integer>> futureSumsExecutorService = executorService.invokeAll(calculators);
        Integer sumExecutorService = futureSumsExecutorService.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("can't get future result");
            }
        }).reduce(0, Integer::sum);
        ForkJoinPool forkJoinPool = new ForkJoinPool(CORES_COUNT);
        List<Future<Integer>> futureSumsForkJoin = forkJoinPool.invokeAll(calculators);
        Integer sumForkJoin = futureSumsForkJoin.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("can't get future result");
            }
        }).reduce(0, Integer::sum);
        System.out.println("ExecutorService sum: " + sumExecutorService);
        System.out.println("ForkJoin sum: " + sumForkJoin);
        executorService.shutdown();
    }
}
