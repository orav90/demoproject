# External Sorting 
The main objective of this project is to efficiently perform external sorting on a large file while complying to memory size constraints (referred to as 'x'). The process involves multiple steps:

1. Handling the Input File: The program takes an input file with 'n' lines and divides it into 'k' smaller chunks, each of which is limited to the size of available memory 'x'. The value of 'k' is determined by the upper limit of 'n/x'.

2. Sorting the Chunks: Each of these smaller chunks is sorted independently and then saved to its own temporary file.

3. Merging Process: To efficiently merge the sorted chunks, the available memory 'x' is divided into 'k+1' equally sized slots. The program reads lines from each of the 'k' temporary files into the first 'k' slots while using the 'k+1' slot for merging. 
4. Generating the Final Output: The sorted data from the 'k+1' slot is written to the final output file.
5. Parallel Enhancement: In an effort to improve efficiency, one thread is responsible for managing the sorting and merging of data, while another thread handles the output. This parallel approach has been found to enhance processing speed by at least 20%.

By breaking down the sorting process into smaller, manageable chunks and effectively merging the sorted results, this method allows for the sorting of large files while operating within the constraints of limited available memory.

O(n) - reading from large file
n/x * xlogx - sorting
n/x * xlogx * logn - merging
O(n) - printing to output file. When working with two threads this time is in parallel to the processing time.
