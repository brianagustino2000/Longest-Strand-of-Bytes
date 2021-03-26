import java.io.BufferedWriter;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;

public class LongestBytes {
    public static void main(String[] args) throws IOException {
        //Local directory of the file
        String directory = "C:\\Users\\Brian Agustino\\Desktop\\LongestStrandBytes\\Eluvio Challenge - Core Engineering\\";
        ArrayList<String> fileNames = new ArrayList<>(List.of("sample.1", "sample.2", "sample.3", "sample.4", "sample.5", "sample.6", "sample.7", "sample.8", "sample.9", "sample.10"));

        Nodes newNode = applyToAll(directory, fileNames);
        printResult(newNode);
    }


    /**
     * Function to print results of largest strand of bytes, its files, and offset for each files
     * and write to new text file ("results.txt")
     */
    public static void printResult(Nodes newNode) {
        ArrayList<String> commonFilesArray = newNode.getCommonFiles();
        ArrayList<Integer> offsetArray = newNode.offsetArrays;
        System.out.println("Length of longest strand of Bytes: " +  newNode.getLength());
        System.out.println("Longest strand of bytes exists in files: ");
        System.out.println("File Names:              | Offset (in Bytes):                      ");
        System.out.println("---------------------------------------------------------------------");
        for (int i = 0; i < newNode.getCommonFiles().size(); i++) {
            System.out.println(commonFilesArray.get(i) + "                 | " + offsetArray.get(i));
        }

        //Source: https://www.w3schools.com/java/java_files_create.asp
        //To write to a new text file ("results.txt")
        try {
            BufferedWriter resultWriter = new BufferedWriter(new FileWriter("results.txt"));
            resultWriter.write("Length of longest strand of Bytes: " +  newNode.getLength());
            resultWriter.newLine();
            resultWriter.write("Longest strand of bytes exists in files: ");
            resultWriter.newLine();
            resultWriter.write("File Names:              | Offset (in Bytes):                      ");
            resultWriter.newLine();
            resultWriter.write("---------------------------------------------------------------------");
            resultWriter.newLine();
            for (int i = 0; i < newNode.getCommonFiles().size(); i++) {
                resultWriter.write(commonFilesArray.get(i) + "                 | " + offsetArray.get(i));
                resultWriter.newLine();
            }
            resultWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


    /**
     * Function that finds the largest common strand of bytes
     * between multiple files through multiple calls of longCommonHex and return Nodes
     * with parameter longest length, offset of each files array, file names with common longest strand array
     */
    public static Nodes applyToAll(String directory, ArrayList<String> fileNames) throws IOException {
        //Initialize ArrayList cache that stores all String Arrays of each files
        ArrayList<String[]> cache = new ArrayList<>();
        //Initialize ArrayList commonFiles and offsetArray to store the file name and offset of longest strand
        ArrayList<String> commonFiles = new ArrayList<>();
        ArrayList<Integer> offsetArray = new ArrayList<>();

        //To store the current longest length of strand and the String of the longest strand
        int currLongest = 0;
        String currString = "";

        //Fill up the cache string array files to minimize costs
        for (int k = 0; k < fileNames.size(); k++) {
            cache.add(convertFileToHex(Paths.get(directory + fileNames.get(k))));
        }

        //Compare each files longest strands avoiding commutative cases
        for (int i = 0; i < fileNames.size(); i++) {
            for (int j = i + 1; j < fileNames.size(); j++) {
                //Get Nodes of longest strand between files i and files j
                Nodes tempNode = longCommonHex(cache.get(i), cache.get(j));
                int tempLen = tempNode.getLength();
                String tempString = tempNode.getResultString();

                //Case when strand of bytes is same as current longest strand
                if (tempLen == currLongest && currString.equals(tempString)) {
                    //To avoid multiple same file names
                    if (!commonFiles.contains(fileNames.get(i))) {
                        commonFiles.add(fileNames.get(i));
                        offsetArray.add(tempNode.getOffset1());
                    }
                    if (!commonFiles.contains(fileNames.get(j))) {
                        commonFiles.add(fileNames.get(j));
                        offsetArray.add(tempNode.getOffset2());
                    }
                } else if (tempLen > currLongest) {
                    //Case when the strand of bytes is longer than the current longest strand
                    currLongest = tempLen;
                    currString = tempString;
                    commonFiles = new ArrayList<>();
                    offsetArray = new ArrayList<>();
                    commonFiles.add(fileNames.get(i));
                    offsetArray.add(tempNode.getOffset1());
                    commonFiles.add(fileNames.get(j));
                    offsetArray.add(tempNode.getOffset2());
                }
            }
        }
        return new Nodes(currLongest, offsetArray, commonFiles);
    }


    /**
     * Function that finds the longest common strand of bytes or substring
     * between 2 string arrays (files) and return Nodes
     * with parameter longestLength, offset of file 1, offset of file 1, longest common strand
     */
    //Source: https://www.geeksforgeeks.org/longest-common-substring-space-optimized-dp-solution/
    public static Nodes longCommonHex(String[] s1, String[] s2) {
        // Find length of both the strings.
        int m = s1.length;
        int n = s2.length;

        // Variable to store length of longest
        // common substring.
        int longestLength = 0;

        // Matrix to store result of two
        // consecutive rows at a time.
        int [][]len = new int[2][n];

        // Variable to represent which row of
        // matrix is current row.
        int currRow = 0;
        int num1 = 0;
        int num2 = 0;

        // For a particular value of
        // i and j, len[currRow][j]
        // stores length of longest
        // common substring in string
        // X[0..i] and Y[0..j].
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    len[currRow][j] = 0;
                } else if (s1[i - 1].equals(s2[j - 1])) {
                    len[currRow][j] = len[(1 - currRow)][(j - 1)] + 1;
                    //If the length is greater than result, store the num to calculate the offset of the new largest substring
                    if (len[currRow][j] > longestLength) {
                        longestLength = len[currRow][j];
                        num1 = i;
                        num2 = j;
                    }
                } else {
                    len[currRow][j] = 0;
                }
            }

            // Make current row as previous
            // row and previous row as
            // new current row.
            currRow = 1 - currRow;
        }
        //Get the substring of the array from (offset - longestLength) to offset
        String[] subArr = Arrays.copyOfRange(s1, num1 - longestLength, num1);
        //Return new Nodes with parameter (length, offset1, offset2, resultString)
        return new Nodes(longestLength, num1 - longestLength, num2 - longestLength, Arrays.toString(subArr));
    }


    /**
     * Function that converts a file into hexadecimals
     * and return a String Array
     */
    //Source: https://mkyong.com/java/how-to-convert-file-to-hex-in-java/
    public static String[] convertFileToHex(Path path) throws IOException {
        if (Files.notExists(path)) {
            throw new IllegalArgumentException("File not found! " + path);
        }
        StringBuilder result = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        StringBuilder input = new StringBuilder();

        int count = 0;
        int value;

        // path to inputstream....
        try (InputStream inputStream = Files.newInputStream(path)) {

            while ((value = inputStream.read()) != -1) {

                hex.append(String.format("%02X ", value));

                //If the character is unable to convert, just prints a dot "."
                if (!Character.isISOControl(value)) {
                    input.append((char) value);
                } else {
                    input.append('.');
                }

                // After 15 bytes, reset everything for formatting purpose
                if (count == 14) {
                    result.append(hex);
                    hex.setLength(0);
                    input.setLength(0);
                    count = 0;
                } else {
                    count++;
                }

            }
            // if the count>0, meaning there is remaining content
            if (count > 0) {
                result.append(hex);
            }
        }
        return result.toString().split(" ");
    }
}

