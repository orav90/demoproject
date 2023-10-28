import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ExternalSortServiceNonParallel extends ExternalSortAbstract{


    protected void handleOutputFile(ArrayList<FileSlot> slotArray, int totalLines, int linesPerSlot, PriorityQueue<FileSlot> pq, String outputFile) {
        int outputFileLines = 0;
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
                outputFileLines = writeToOutputFile(slotArray, filesCount, outputFileLines, outputFile);
                slotArray.get(filesCount).getLines().clear();
            }
        }
    }



    private int writeToOutputFile(ArrayList<FileSlot> slotArray, int numOfFiles, int outputFileLines, String outputFile) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile), StandardCharsets.UTF_8, StandardOpenOption.APPEND)){
            for (String line : slotArray.get(numOfFiles).getLines()) {
                writer.write(line);
                writer.newLine();
                outputFileLines++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while writing output file");
        }
        return outputFileLines;
    }


}
