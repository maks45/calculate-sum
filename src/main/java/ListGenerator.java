import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListGenerator {
    public static final int ELEMENTS_COUNT = 1_000_000;
    public static final int CORES_COUNT = Runtime.getRuntime().availableProcessors();

    public List<Callable<Integer>> getCalculators() {
        List<Integer> intsList = IntStream.range(0, ELEMENTS_COUNT)
                .boxed().collect(Collectors.toList());
        List<Callable<Integer>> calculators = new ArrayList<>();
        IntStream.range(0, CORES_COUNT).forEach(i -> calculators.add(
                new SumCalculator(intsList.subList(i * (ELEMENTS_COUNT / CORES_COUNT), (i + 1)
                        * (ELEMENTS_COUNT / CORES_COUNT)))));
        return calculators;
    }
}
