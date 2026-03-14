**Data Structure Design**
**Plagiarism Detector Using Data Structures**

This project will check for similarities between two given text documents by processing the words in the documents. It will compare the words to find similarities between the two documents. Efficient data structures are used to store the words in the documents and then compare them.

**Data Structures Used**
**1. Array/List**

Lists are used to store the words obtained from the documents after preprocessing.
The text will be preprocessed by removing punctuation marks and changing it to lowercase. Then it will be split into individual words, which will be stored in the list.

For example:
["data", "structures", "improve", "efficiency"]

Lists will be used to traverse the words. It will be the first step to store the words before proceeding to the next step.

**2. Hash Set**

Hash Sets are used to store the words in the documents. Since sets do not allow duplicate values, it will automatically remove duplicate words.

For example:
{"data", "structures", "algorithm"}

Using hash sets will help to find similarities between the two documents very efficiently because the search time will be very fast (average time complexity: O(1)).
3. Hash Table

Hash Tables are used to store word frequency in the document. Each word in the document is used as a key, and the number of times that word appears in the document is stored as the value.

Example:

data → 2  
structures → 1  
algorithm → 1

This helps in analyzing the occurrence of each word in the document and makes the comparison more accurate.

Comparison Process

Accept two text documents as input.

Clean and preprocess the input.

Split the input into words and store them in lists.

Convert the lists to hash sets to eliminate duplicates.

Compare the hash sets and find common words.

Calculate the percentage of similarity.

Similarity Calculation

The percentage of similarity between two documents is calculated using the formula:

Similarity % = (Number of Common Words / Total Unique Words) * 100

Conclusion

Using Lists, Hash Sets, and Hash Tables makes the plagiarism detection system highly efficient.
