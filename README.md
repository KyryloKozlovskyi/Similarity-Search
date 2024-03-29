# Similarity-Search
Similarity Search with Word Embeddings

## Author

- Kyrylo Kozlovskyi G00425385 (GitHub: [KyryloKozlovskyi](https://github.com/KyryloKozlovskyi/Similarity-Search))

## Description

This console Java application allows users to perform similarity search with word embeddings. Word embeddings are parsed into a HashMap structure from a user-specified file. Users can input a word or a short sentence to find the number (N) of the most similar words using either Euclidean Distance or Cosine Distance algorithms to compare similarity. The top N user-specified matches and their similarity scores are displayed in a console and written to a user-specified output file.

## Requirements

- Java SE-17

## How To Use

Open the directory containing the .jar file using the Command Line, and use the following syntax to run the application: java -cp ./"fileName".jar ie.atu.sw.Runner. Then:
1. Enter "1" - to Specify Embeddings File Path and Create a HashMap (or Press "enter" to use the default file path "./word-embeddings.txt").
2. Enter "2" - to Specify an Output File Path (or Press "enter" to use the default file path "./out.txt").
3. Enter "3" - to Specify N - Number of Top Matches (or Press "enter" to use the default value of "3").
4. Enter "4" - to Specify the Similarity Comparison Algorithm (or Press "enter" to use the default algorithm "Euclidean Distance").
5. Enter "5" - (Optional) to Print Current Settings (then press "enter" to return to the menu).
6. Enter "6" - to Enter a Word (or Text) to Process. *NOTE! The Word (or Text) must be a valid word from the Embeddings File* (then press "enter" to return to the menu). 

## Features

- **Parsing Word Embeddings**: Parses word embeddings from a file into a HashMap structure.
- **User Input Processing**: Prompts the user to input words or text and validates input.
- **Similarity Calculation**: Calculates similarity scores using either Euclidean Distance or Cosine Distance.
- **Output Generation**: Generates output (console, file) containing the top N matches (words) and their similarity scores.
- **Customizable Settings**: Allows the user to specify paths for embeddings file and output file, set the number of top matches (N), and select the similarity comparison algorithm.
- **Settings View**: Settings can be viewed at any time (to confirm settings).