import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class TestExternalSort {

    @Test
    public void testParallelIsFaster(){

        int x = 500;
        int keyFieldIndex = 2;
        String inputFile = Constants.INPUT_FILE;
        String outputFileNonParallel = "output_test_nonparallel.csv";
        String outputFileParallel = "output_test_parallel.csv";

        ExternalSortAbstract externalSortNonParallel = new ExternalSortServiceNonParallel();

        long start = System.currentTimeMillis();
        externalSortNonParallel.externalSort(x, keyFieldIndex, inputFile, outputFileNonParallel);
        long end = System.currentTimeMillis();
        long nonParallelDiff = end - start;

        ExternalSortAbstract externalSortParallel = new ExternalSortServiceParallel();

        start = System.currentTimeMillis();
        externalSortParallel.externalSort(x, keyFieldIndex, inputFile, outputFileParallel);
        end = System.currentTimeMillis();
        long parallelDiff = end - start;

        Assertions.assertTrue(parallelDiff < nonParallelDiff);

    }

    @Test
    public void testExternalSortNonParallel() throws IOException {

        int x = 500;
        int keyFieldIndex = 2;
        String inputFile = Constants.INPUT_FILE;
        String outputFileNonParallel = "output_test_nonparallel.csv";

        ExternalSortAbstract externalSortNonParallel = new ExternalSortServiceNonParallel();

        externalSortNonParallel.externalSort(x, keyFieldIndex, inputFile, outputFileNonParallel);

        List<String> lines = Files.readAllLines(Path.of(outputFileNonParallel));

        for(int i=1; i<lines.size(); ++i){
            String current = lines.get(i).split(",")[keyFieldIndex];
            String prev = lines.get(i-1).split(",")[keyFieldIndex];
            Assertions.assertTrue(prev.compareTo(current) < 0);
        }

    }

    @Test
    public void testExternalSortParallel() throws IOException {

        int x = 500;
        int keyFieldIndex = 2;
        String inputFile = Constants.INPUT_FILE;
        String outputFileParallel = "output_test_parallel.csv";

        ExternalSortAbstract externalSortServiceParallel = new ExternalSortServiceParallel();

        externalSortServiceParallel.externalSort(x, keyFieldIndex, inputFile, outputFileParallel);

        List<String> lines = Files.readAllLines(Path.of(outputFileParallel));

        for(int i=1; i<lines.size(); ++i){
            String current = lines.get(i).split(",")[keyFieldIndex];
            String prev = lines.get(i-1).split(",")[keyFieldIndex];
            Assertions.assertTrue(prev.compareTo(current) < 0);
        }

    }
}
