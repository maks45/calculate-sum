import java.util.List;
import java.util.concurrent.Callable;

public class SumCalculator implements Callable<Integer> {
    private final List<Integer> elements;

    public SumCalculator(List<Integer> elements) {
        this.elements = elements;
    }

    @Override
    public Integer call() throws Exception {
        return elements.stream().reduce(0, Integer::sum);
    }
}
