import java.util.Scanner;

public class PageNumOffset {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter page size (in KB): ");
            int pageSize = scanner.nextInt();

            System.out.print("Enter a virtual address: ");
            int virtualAddress = scanner.nextInt();

            int pageSizeInBytes = pageSize * 1024;
            int pageNumber = virtualAddress / pageSizeInBytes;
            int offset = virtualAddress % pageSizeInBytes;

            System.out.println("The address " + virtualAddress + " contains: page number = " + pageNumber + " offset = " + offset);
        }
    }
}