
/**
 * This program implements various disk scheduling algorithms, including FCFS, SSTF, SCAN, and C-SCAN.
 * It calculates the total head movement required to process a sequence of disk requests.
 * The program can generate random disk requests or read them from a file.
 * It also calculates the number of changes in direction in the disk requests.
 *
 * The main method demonstrates the usage of the implemented algorithms by generating random requests
 * and reading requests from a file. It prints the results of each algorithm, including the head movement
 * and the number of changes in direction.
 *
 * The program also provides utility methods for reading requests from a file, generating random requests,
 * and implementing each disk scheduling algorithm.
 *
 * Note: The program assumes a cylinder range from 0 to 4999.
 * Course: CS4310 Operating Systems Project 2
 *
 * @author Kevin Wong
 * @since 4/30/2024
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DiskScheduling {

    /**
     * The main method is the entry point of the program. It performs disk
     * scheduling algorithms on randomly generated requests and requests read
     * from a file. It calculates the head movement and change of direction for
     * each algorithm and prints the results.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        //Part A.
        if (args.length == 0) {
            System.err.println("Please enter a command line argument for the initial position.");
            System.exit(1);
        }
        int initialPosition = Integer.parseInt(args[0]);

        int[] requests = generateRandomRequests(1000); // Generate 1000 random requests

        // FCFS
        int fcfsHeadMovement = fcfs(initialPosition, requests);
        int fcfsChangeOfDirection = calculateChangeOfDirection(requests, "FCFS");

        // SSTF
        int sstfHeadMovement = sstf(initialPosition, requests);
        int sstfChangeOfDirection = calculateChangeOfDirection(requests, "SSTF");

        int[] newRequests = new int[requests.length + 2];
        System.arraycopy(requests, 0, newRequests, 0, requests.length);

        newRequests[newRequests.length - 1] = 0;
        newRequests[newRequests.length - 2] = 4999;

        // SCAN
        int scanHeadMovement = scan(initialPosition, requests);
        int scanChangeOfDirection = calculateChangeOfDirection(newRequests, "SCAN");

        // C-SCAN
        int cscanHeadMovement = cscan(initialPosition, requests);
        int cscanChangeOfDirection = calculateChangeOfDirection(newRequests, "C-SCAN");

        // Print the results
        System.out.println("From randomly generated requests:");
        System.out.println("FCFS - Head Movement: " + fcfsHeadMovement + ", Change of Direction: " + fcfsChangeOfDirection);
        System.out.println("SSTF - Head Movement: " + sstfHeadMovement + ", Change of Direction: " + sstfChangeOfDirection);
        System.out.println("SCAN - Head Movement: " + scanHeadMovement + ", Change of Direction: " + scanChangeOfDirection);
        System.out.println("C-SCAN - Head Movement: " + cscanHeadMovement + ", Change of Direction: " + cscanChangeOfDirection);
        System.out.println("");
        //Part B.
        // Read requests from file
        int[] fileRequests = readRequestsFromFile("input.txt");
        // FCFS
        int fcfsFileHeadMovement = fcfs(initialPosition, fileRequests);
        int fcfsFileChangeOfDirection = calculateChangeOfDirection(fileRequests, "FCFS");
        // SSTF
        int sstfFileHeadMovement = sstf(initialPosition, fileRequests);
        int sstfFileChangeOfDirection = calculateChangeOfDirection(fileRequests, "SSTF");

        int[] newFileRequests = new int[fileRequests.length + 2];
        System.arraycopy(fileRequests, 0, newFileRequests, 0, fileRequests.length);

        newFileRequests[newFileRequests.length - 1] = 0;
        newFileRequests[newFileRequests.length - 2] = 4999;

        // SCAN
        int scanFileHeadMovement = scan(initialPosition, fileRequests);
        int scanFileChangeOfDirection = calculateChangeOfDirection(newFileRequests, "SCAN");//head direction is RIGHT
        // C-SCAN
        int cscanFileHeadMovement = cscan(initialPosition, fileRequests);
        int cscanFileChangeOfDirection = calculateChangeOfDirection(newFileRequests, "C-SCAN");

        // Print the results
        System.out.println("From input.txt requests:");
        System.out.println("FCFS (File) - Head Movement: " + fcfsFileHeadMovement + ", Change of Direction: " + fcfsFileChangeOfDirection);
        System.out.println("SSTF (File) - Head Movement: " + sstfFileHeadMovement + ", Change of Direction: " + sstfFileChangeOfDirection);
        System.out.println("SCAN (File) - Head Movement(head direction is RIGHT): " + scanFileHeadMovement + ", Change of Direction: " + scanFileChangeOfDirection);
        System.out.println("C-SCAN (File) - Head Movement: " + cscanFileHeadMovement + ", Change of Direction: " + cscanFileChangeOfDirection);

    }

    /**
     * Reads disk requests from a file and returns an array of integers
     * representing the requests.
     *
     * @param fileName the name of the file to read the requests from
     * @return an array of integers representing the disk requests
     */
    public static int[] readRequestsFromFile(String fileName) {
        try {
            List<Integer> requestsList = new ArrayList<>();
            File file = new File(fileName);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    int request = Integer.parseInt(scanner.nextLine());
                    requestsList.add(request);
                }
            }
            int[] requests = new int[requestsList.size()];
            for (int i = 0; i < requestsList.size(); i++) {
                requests[i] = requestsList.get(i);
            }
            return requests;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return new int[0];
        }
    }

    /**
     * Calculates the number of changes in direction in the given array of disk
     * requests.
     *
     * @param requests an array of disk requests
     * @param algorithm
     * @return the number of changes in direction in the disk requests
     */
    public static int calculateChangeOfDirection(int[] requests, String algorithm) {
        int initialPosition = requests[0];
        int changeOfDirection = 0;
        boolean movingUp;

        movingUp = requests[0] < requests[1];
        for (int i = 1; i < requests.length; i++) {
            int request = requests[i];
            if (((request < initialPosition && movingUp) || (request > initialPosition && !movingUp)) && (algorithm.equals("FCFS") || algorithm.equals("SSTF"))) {
                changeOfDirection++;
                movingUp = !movingUp; // Change direction
            }
            if (((request == 0 && initialPosition == 4999) || (request == 4999 && initialPosition == 0)) && (algorithm.equals("SCAN") || algorithm.equals("C-SCAN"))) {
                changeOfDirection++;
            }
            initialPosition = request;
        }
        return changeOfDirection;
    }

    /**
     * Generates an array of random disk requests.
     *
     * @param numRequests the number of disk requests to generate
     * @return an array of random disk requests
     */
    public static int[] generateRandomRequests(int numRequests) {
        int[] requests = new int[numRequests];
        Random random = new Random();
        for (int i = 0; i < numRequests; i++) {
            requests[i] = random.nextInt(5000); // Assuming the cylinder range is from 0 to 4999
        }
        return requests;
    }

    /**
     * Implements the First-Come-First-Serve (FCFS) disk scheduling algorithm.
     * Calculates the total head movement required to process a sequence of disk
     * requests.
     *
     * @param initialPosition The initial position of the disk head.
     * @param requests An array of disk requests in the order they are received.
     * @return The total head movement required to process the disk requests.
     */
    public static int fcfs(int initialPosition, int[] requests) {
        int headMovement = Math.abs(initialPosition - requests[0]);
        for (int i = 1; i < requests.length; i++) {
            headMovement += Math.abs(requests[i] - requests[i - 1]);
        }
        return headMovement;
    }

    /**
     * Implements the Shortest Seek Time First (SSTF) disk scheduling algorithm.
     * This algorithm selects the request with the shortest seek time from the
     * current position. The seek time is the absolute distance between the
     * current position and the requested position.
     *
     * @param initialPosition The initial position of the disk head.
     * @param requests An array of disk requests.
     * @return The total head movement required by the SSTF algorithm.
     */
    public static int sstf(int initialPosition, int[] requests) {
        int headMovement = 0;
        int currentPosition = initialPosition;
        boolean[] visited = new boolean[requests.length];

        for (int i = 0; i < requests.length; i++) {
            int shortestDistance = Integer.MAX_VALUE;
            int shortestIndex = -1;

            // Find the nearest unvisited request
            for (int j = 0; j < requests.length; j++) {
                if (!visited[j]) {
                    int distance = Math.abs(currentPosition - requests[j]);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        shortestIndex = j;
                    }
                }
            }
            // Update the head movement and mark the request as visited
            headMovement += shortestDistance;
            visited[shortestIndex] = true;
            currentPosition = requests[shortestIndex];
        }
        return headMovement;
    }

    /**
     * Performs the SCAN disk scheduling algorithm to calculate the total head
     * movement. (Head direction is set to RIGHT)
     *
     * @param initialPosition The initial position of the disk head.
     * @param requests An array of cylinder numbers representing the disk access
     * requests.
     * @return The total head movement required to fulfill all the requests.
     */
    public static int scan(int initialPosition, int[] requests) {
        int headMovement = 0;
        int currentPosition = initialPosition;
        boolean[] visited = new boolean[requests.length];
        boolean direction = true; // true for moving towards higher cylinder numbers, false for moving towards lower cylinder numbers

        // Sort the requests in ascending order
        Arrays.sort(requests);

        // Find the index of the initial position in the sorted requests
        int initialIndex = Arrays.binarySearch(requests, initialPosition);
        if (initialIndex < 0) {
            // If the initial position is not found in the requests, find the index where it should be inserted
            initialIndex = -initialIndex - 1;
        }

        // Perform SCAN algorithm
        if (direction) {
            // Move towards higher cylinder numbers
            for (int i = initialIndex; i < requests.length; i++) {
                headMovement += Math.abs(currentPosition - requests[i]);
                currentPosition = requests[i];
                visited[i] = true;
            }
            // Move to the highest cylinder number
            headMovement += Math.abs(currentPosition - 4999);
            currentPosition = 4999;
            // Move towards lower cylinder numbers
            for (int i = requests.length - 1; i >= 0; i--) {
                if (!visited[i]) {
                    headMovement += Math.abs(currentPosition - requests[i]);
                    currentPosition = requests[i];
                    visited[i] = true;
                }
            }
        } else {
            // Move towards lower cylinder numbers
            for (int i = initialIndex - 1; i >= 0; i--) {
                headMovement += Math.abs(currentPosition - requests[i]);
                currentPosition = requests[i];
                visited[i] = true;
            }
            // Move to the lowest cylinder number
            headMovement += Math.abs(currentPosition - 0);
            currentPosition = 0;
            // Move towards higher cylinder numbers
            for (int i = 0; i < requests.length; i++) {
                if (!visited[i]) {
                    headMovement += Math.abs(currentPosition - requests[i]);
                    currentPosition = requests[i];
                    visited[i] = true;
                }
            }
        }
        return headMovement;
    }

    /**
     * Performs the C-SCAN disk scheduling algorithm to calculate the total head
     * movement.
     *
     * @param initialPosition The initial position of the disk head.
     * @param requests An array of cylinder numbers representing the disk access
     * requests.
     * @return The total head movement required to fulfill all the requests.
     */
    public static int cscan(int initialPosition, int[] requests) {
        int headMovement = 0;
        int currentPosition = initialPosition;
        boolean[] visited = new boolean[requests.length];
        boolean direction = true; // true for moving towards higher cylinder numbers, false for moving towards lower cylinder numbers
        // Sort the requests in ascending order
        Arrays.sort(requests);
        // Find the index of the initial position in the sorted requests
        int initialIndex = Arrays.binarySearch(requests, initialPosition);
        if (initialIndex < 0) {
            // If the initial position is not found in the requests, find the index where it should be inserted
            initialIndex = -initialIndex - 1;
        }
        // Perform C-SCAN algorithm
        if (direction) {
            // Move towards higher cylinder numbers
            for (int i = initialIndex; i < requests.length; i++) {
                headMovement += Math.abs(currentPosition - requests[i]);
                currentPosition = requests[i];
                visited[i] = true;
            }
            // Move to the highest cylinder number
            headMovement += Math.abs(currentPosition - 4999);
            currentPosition = 4999;
            // Move to the lowest cylinder number
            headMovement += Math.abs(currentPosition - 0);
            currentPosition = 0;
            // Move towards higher cylinder numbers again
            for (int i = 0; i < initialIndex; i++) {
                if (!visited[i]) {
                    headMovement += Math.abs(currentPosition - requests[i]);
                    currentPosition = requests[i];
                    visited[i] = true;
                }
            }
        } else {
            // Move towards lower cylinder numbers
            for (int i = initialIndex - 1; i >= 0; i--) {
                headMovement += Math.abs(currentPosition - requests[i]);
                currentPosition = requests[i];
                visited[i] = true;
            }
            // Move to the lowest cylinder number
            headMovement += Math.abs(currentPosition - 0);
            currentPosition = 0;
            // Move to the highest cylinder number
            headMovement += Math.abs(currentPosition - 4999);
            currentPosition = 4999;
            // Move towards lower cylinder numbers again
            for (int i = requests.length - 1; i >= initialIndex; i--) {
                if (!visited[i]) {
                    headMovement += Math.abs(currentPosition - requests[i]);
                    currentPosition = requests[i];
                    visited[i] = true;
                }
            }
        }
        return headMovement;
    }
}
