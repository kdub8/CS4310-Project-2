import java.util.Scanner;

public class LogicalBlockNum {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a logical block number: ");
            int logicalBlockNum = scanner.nextInt();
            
            System.out.print("Enter HD number of cylinders: ");
                    int numCylinders = scanner.nextInt();
            
            System.out.print("Enter HD number of tracks: ");
            int numTracks = scanner.nextInt();
            
            System.out.print("Enter HD number of sectors: ");
            int numSectors = scanner.nextInt();
            
            int cylinder = logicalBlockNum / (numTracks * numSectors);
            int remainingBlocks = logicalBlockNum % (numTracks * numSectors);
            int track = remainingBlocks / numSectors;
            int sector = remainingBlocks % numSectors;
            
            System.out.printf("The logical block number %d is located at <%d, %d, %d>\n",
                    logicalBlockNum, cylinder, track, sector);
        }
    }
}
