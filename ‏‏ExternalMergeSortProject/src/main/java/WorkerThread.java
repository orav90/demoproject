import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

public class WorkerThread implements Runnable{

    private CustomBlockingQueue<LinkedList<String>> arrayBlockingQueue;
    private String outputFile;

    public WorkerThread(CustomBlockingQueue<LinkedList<String>> arrayBlockingQueue, String outputFile) {
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        try {
            while (!arrayBlockingQueue.isEmpty() || !arrayBlockingQueue.isShouldTerminate()) {
                List<String> stringList = arrayBlockingQueue.take();
                try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile), StandardCharsets.UTF_8, StandardOpenOption.APPEND)){
                    for (String line : stringList) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error while writing output file");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
