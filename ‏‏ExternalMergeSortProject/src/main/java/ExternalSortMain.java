public class ExternalSortMain {
    //args list is as follows:
    //[memory limit size(x), key field index, input file name, output file name, Y/N is parallel]
    public static void main(String[] args) {
        int x, keyFieldIndex;
        String inputFile, outputFile;
        ExternalSortAbstract externalSort;
        if(args.length == 0) {
            x = 500;
            keyFieldIndex = 2;
            inputFile = Constants.INPUT_FILE;
            outputFile = Constants.OUTPUT_FILE;
            externalSort = new ExternalSortServiceNonParallel();
        } else{
            x = Integer.parseInt(args[0]);
            keyFieldIndex = Integer.parseInt(args[1]);
            inputFile = args[2];
            outputFile = args[3];
            if(args[4].equals("N")){
                externalSort = new ExternalSortServiceNonParallel();
            } else{
                externalSort = new ExternalSortServiceParallel();
            }
        }
        externalSort.externalSort(x, keyFieldIndex, inputFile, outputFile);
    }
}
