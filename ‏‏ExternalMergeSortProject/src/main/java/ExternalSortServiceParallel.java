
import java.util.*;

public class ExternalSortServiceParallel extends ExternalSortAbstract {

    CustomBlockingQueue<LinkedList<String>> arrayBlockingQueue = new CustomBlockingQueue<>(1);

    protected void handleOutputFile(ArrayList<FileSlot> slotArray, int totalLines, int linesPerSlot, PriorityQueue<FileSlot> pq, String outputFile) {
        int outputFileLines = 0;
        Thread thread1 = new Thread(new WorkerThread(arrayBlockingQueue, outputFile));
        thread1.start();
        while (outputFileLines < totalLines) {
            boolean moreLinesToRead = true;
            FileSlot minSlot = pq.peek();
            slotArray.get(filesCount).getLines().add(pq.poll().getLines().getFirst());
            minSlot.getLines().removeFirst();
            if(minSlot.getLines().isEmpty()){
                moreLinesToRead = fillPqSlot(minSlot, linesPerSlot);
            }
            if(moreLinesToRead) {
                pq.offer(minSlot);
            } else{
                pq.remove(minSlot);
            }
            if (shouldWriteToFile(slotArray, filesCount, totalLines, linesPerSlot, outputFileLines)) {
                outputFileLines = writeToOutputFile(slotArray, filesCount, outputFileLines);
                slotArray.get(filesCount).getLines().clear();
            }
        }
        arrayBlockingQueue.terminate();
    }

    private int writeToOutputFile(ArrayList<FileSlot> slotArray, int numOfFiles, int outputFileLines) {
        LinkedList<String> lines = (LinkedList<String>) slotArray.get(numOfFiles).getLines().clone();

        try {
            arrayBlockingQueue.put(lines);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        outputFileLines += slotArray.get(numOfFiles).getLines().size();

        return outputFileLines;
    }

}
