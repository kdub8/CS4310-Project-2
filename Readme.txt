Task 1
Page Number and Offset Calculator
This program calculates the page number and offset of a virtual address based on a given page size.

Usage
Compile the program using 'javac PageNumOffset.java'.
Run the program using 'java PageNumOffset'.
Input
The program prompts the user to enter the page size in KB and a virtual address.
Output
The program calculates and prints the page number and offset of the given virtual address.

Example:
Enter page size (in KB): 2
Enter a virtual address: 10500

The address 10500 contains: page number = 5 offset = 260

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Task 2
Disk Scheduling Algorithms
This program implements various disk scheduling algorithms, including FCFS, SSTF, SCAN, and C-SCAN. 
It calculates the total head movement required to process a sequence of disk requests and the number of changes in direction 
in the disk requests.

Usage
Running the Program
Compile the program using 'javac DiskScheduling.java'.
Run the program using 'java DiskScheduling <initialPosition>', where <initialPosition> is the initial position of the disk head.
Input
The program can generate random disk requests or read them from a file. 
To alter the file requests, change the input.txt file with your desired disk requests and ensure that it 
contains one disk request per line.

Output
The program prints the results of each algorithm, including the head movement and the number of changes in direction, for both 
randomly generated requests and requests read from a file.

Example
To run the program with an initial position of 53 and generate random requests as well as requests 
from input.txt (must be in the same directory as DiskScheduling.java):

'java DiskScheduling 53'

From randomly generated requests:
FCFS - Head Movement: 1661345, Change of Direction: 659
SSTF - Head Movement: 5042, Change of Direction: 659
SCAN - Head Movement: 9941, Change of Direction: 1
C-SCAN - Head Movement: 9993, Change of Direction: 1

From input.txt requests:
FCFS (File) - Head Movement: 640, Change of Direction: 6
SSTF (File) - Head Movement: 236, Change of Direction: 6
SCAN (File) - Head Movement: 9931, Change of Direction: 1
C-SCAN (File) - Head Movement: 9982, Change of Direction: 1

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Task 3
Logical Block Number Calculator
This program calculates the physical location of a logical block number on a hard disk with a specified number of cylinders, 
tracks, and sectors.

Usage
Compile the program using 'javac LogicalBlockNum.java'.
Run the program using 'java LogicalBlockNum'.
Input
The program prompts the user to enter the logical block number, the number of cylinders, tracks, and sectors of the hard disk.
Output
The program calculates and prints the physical location of the given logical block number in the format <cylinder, track, sector>.

Example:
Enter a logical block number: 1041
Enter HD number of cylinders: 1000
Enter HD number of tracks: 20
Enter HD number of sectors: 63

The logical block number 1041 is located at <0, 16, 33>