import java.util.LinkedList;

public class FileSlot {

    private int index;
    private String name;

    private LinkedList<String> lines;

    public FileSlot(int index, String name, LinkedList<String> lines) {
        this.index = index;
        this.name = name;
        this.lines = lines;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getLines() {
        return lines;
    }

    public void setLines(LinkedList<String> lines) {
        this.lines = lines;
    }
}
