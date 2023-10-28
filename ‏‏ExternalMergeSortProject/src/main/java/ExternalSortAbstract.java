import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public abstract class ExternalSortAbstract {

    int filesCount = 0;

    protected PriorityQueue<FileSlot> initPQ(int keyFieldIndex) {
        PriorityQueue<FileSlot> pq = new PriorityQueue<>((slot1, slot2) -> {
            String s1 = slot1.getLines().getFirst().split(Constants.COMMA_DELIMITER)[keyFieldIndex];
            String s2 = slot2.getLines().getFirst().split(Constants.COMMA_DELIMITER)[keyFieldIndex];
            return s1.compareTo(s2);
        });
        return pq;
    }

    protected int processInputFile(int x, int keyFieldIndex, ArrayList<FileSlot> slotArray, String inputFile) {
        int linesCountPerFile = 0;
        int totalLines = 0;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                linesCountPerFile++;
                totalLines++;
                if (linesCountPerFile == x) {
                    writeToTempFile(filesCount+1, lines, slotArray, keyFieldIndex);
                    filesCount++;
                    lines.clear();
                    linesCountPerFile = 0;
                }
            }
            if (!lines.isEmpty()) {
                writeToTempFile(filesCount+1, lines, slotArray, keyFieldIndex);
                filesCount++;
            }
        } catch (IOException e){
            throw new RuntimeException("Error while processing input file");
        }
        return totalLines;
    }


    protected boolean shouldWriteToFile(ArrayList<FileSlot> slotArray, int numOfFiles, int totalLines, int linesPerSlot, int outputFileLines) {
        return slotArray.get(numOfFiles).getLines().size() == linesPerSlot ||
                outputFileLines + slotArray.get(numOfFiles).getLines().size() == totalLines;
    }

    protected boolean fillPqSlot(FileSlot minSlot, int linesPerSlot) {
        try (Stream<String> lines = Files.lines(Paths.get(minSlot.getName()))) {

            List<String> strings = lines.skip(minSlot.getIndex())
                    .limit(linesPerSlot)
                    .toList();

            if(strings.isEmpty()) return false;

            minSlot.getLines().addAll(strings);
            minSlot.setIndex(minSlot.getIndex() + linesPerSlot);
        } catch (IOException e) {
            throw new RuntimeException("Error while filling pq");
        }
        return true;
    }

    public void externalSort(int x, int keyFieldIndex, String inputFile, String outputFile){

        ArrayList<FileSlot> slotArray = new ArrayList<>();
        createTempDir();
        createNewOutputFile(outputFile);
        int totalLines = processInputFile(x, keyFieldIndex, slotArray, inputFile);
        slotArray.add(new FileSlot(0,outputFile, new LinkedList<>()));
        int linesPerSlot = x/(filesCount+1);

        PriorityQueue<FileSlot> pq = initPQ(keyFieldIndex);

        for(int i=0; i<slotArray.size()-1; ++i){
            FileSlot slot = slotArray.get(i);
            fillPqSlot(slot,linesPerSlot);
            pq.offer(slot);
        }
        handleOutputFile(slotArray, totalLines, linesPerSlot, pq, outputFile);
        removeTempDir();
    }

    private void createNewOutputFile(String outputFile) {
        try {
            Path path = Path.of(outputFile);
            if (!Files.exists(path))
                Files.createFile(path);
            else {
                new PrintWriter(outputFile).close();
            }
        } catch (IOException e){
            throw new RuntimeException("Could not find output file");
        }
    }

    protected void writeToTempFile(int fileCount, List<String> lines, List<FileSlot> slotArray, int keyFieldIndex) throws IOException {
        lines.sort((s1, s2) -> {
            String[] split1 = s1.split(Constants.COMMA_DELIMITER);
            String[] split2 = s2.split(Constants.COMMA_DELIMITER);
            return split1[keyFieldIndex].compareTo(split2[keyFieldIndex]);
        });
        String fileName = Constants.TEMP_DIR + Constants.TEMP + fileCount + Constants.CSV;
        Files.write(Paths.get(fileName), lines);
        slotArray.add(new FileSlot(0,fileName, new LinkedList<>()));
    }

    public void createTempDir(){
        try {
            Files.createDirectories(Paths.get(Constants.TEMP_DIR));
        } catch (FileAlreadyExistsException e) {
            System.out.println("Directory already exists.");
        } catch (IOException e) {
            System.err.println("Failed to create the directory: " + e.getMessage());
        }
    }

    public void removeTempDir(){

        String directoryPath = Constants.TEMP_DIR;

        try {
            FileUtils.deleteDirectory(new File(directoryPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    protected abstract void handleOutputFile(ArrayList<FileSlot> slotArray, int totalLines, int linesPerSlot, PriorityQueue<FileSlot> pq, String outputFile);


    }
