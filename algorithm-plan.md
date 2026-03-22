
# Algorithm Plan

This algorithm analyzes two text documents to measure their similarity and identify potential plagiarism.

1. Take two text documents as input.
   - The system accepts two files provided by the user.

2. Convert the text into individual words.
   - The sentences are broken into smaller units called words (tokens).

3. Remove punctuation and unnecessary characters.
   - Symbols like ., !, ?, etc. are removed to clean the data.

4. Store words using a suitable data structure (Hash Table).
   - Words are stored efficiently for fast searching and comparison.

5. Compare the words from both documents.
   - The system checks how many words are common in both documents.

6. Calculate similarity percentage.
   - Similarity is calculated based on matching words.

7. Display plagiarism result.
   - The final result shows how much content is similar.

