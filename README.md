# Longest-Strand-of-Bytes
Eluvio Challenge - Core Engineering (Find longest common strand of bytes) (option 1)

## Description

A program that finds the longest strand of bytes that exists between 2 or more files.
In the main function, enter the directory of the files in 'directory', as well as the file names as a list in 'fileNames'
The program would convert each file into hexadecimals by 'convertFileToHex' and find the longest common strand of bytes


## Nodes.java

#### 'Nodes(int l, int o1, int o2, String rs)'

This constructor would store the length of the strand (l), the offset on the first file (o1), the offset on the second file (o2), and the strand in String (rs)

### 'Nodes(int l ,ArrayList<Integer> offsetA, ArrayList<String> cf)'

This constructor would store the length of the strand (l), the list of offset in respect to cf (offsetA), and the file names where the strand exists (cf)


## LongestBytes.java

### 'ConvertFileToHex' 

Source: https://mkyong.com/java/how-to-convert-file-to-hex-in-java/

This function would take in a 'Path' and would convert and return that specific path or file into strands of hexadecimals

### 'longCommonHex'

Source: https://www.geeksforgeeks.org/longest-common-substring-space-optimized-dp-solution/

This function would take in 2 string arrays and would return a Nodes consisting of the length of the longest common strand of hexadecimal, 
the offset in the first file, the offset in the second file, and the longest common strand of hexadecimal.

By using a 2D integer array and mainting space complexity, the function stores the length of the longest common strand of hexadecimal as well as the offset
of both file1 and file2. The longest common strand of hexadecimal could be obtain by slicing from offset to offset + length

### 'applyToAll'

This function would take in the directory and file names and applies 'longCommonHex' to find the longest common strand of hexadecimal for ALL the files.
It would return a Nodes consisting of the length of the longest strand of bytes (hexadecimal), list of offset respective to the common files (Files that contains the longest strand of bytes),
and a list of common files.

By comparing each files while also avoiding commutative cases (ex: 1-2 and 2-1 is only compared once), it stores the length, offset, and file name for the longest strand of bytes
and would overwrite if found a longer strand of bytes.

### 'printResult'

Source: https://www.w3schools.com/java/java_files_create.asp

This function would print out the length of the longest strand of bytes, the files where the strand of bytes exists, and the offset on each files in the terminal.
It would also create and print out the results in a txt file 'results.txt'.



