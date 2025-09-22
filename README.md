# Wordzzule - The Word Ladder Solver 🪜

[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-green.svg)]()

**Wordzzule** is a command-line Java application that solves Word Ladder puzzles using the Breadth-First Search (BFS) algorithm. Find the shortest sequence of valid words to transform one word into another by changing only one letter at a time!

## 🎯 What is a Word Ladder?

A Word Ladder puzzle challenges you to transform one word into another by:
- Changing exactly **one letter at a time**
- Every intermediate step must be a **valid dictionary word**
- Finding the **shortest possible path**

### Example
Transform **CAT** into **DOG**:
```
CAT → COT → DOT → DOG
```

## 🚀 Features

- ✅ **Optimal Algorithm**: Uses BFS to guarantee the shortest path
- ✅ **Fast Dictionary Lookups**: HashSet implementation for O(1) word validation
- ✅ **User-Friendly Interface**: Clear prompts and beautiful result display
- ✅ **Robust Error Handling**: Graceful handling of invalid inputs and missing files
- ✅ **Educational Code**: Extensively commented for learning purposes
- ✅ **Modular Design**: Clean separation of concerns across three classes

## 📁 Project Structure

```
Wordzzule/
├── README.md
├── DictionaryService.java     # Dictionary management and word validation
├── WordLadderSolver.java      # Core BFS algorithm implementation
├── WordzzuleApp.java          # Main application and user interface
└── wordlist.txt              # Sample dictionary file
```

## 🛠️ Installation & Setup

### Prerequisites
- **Java 8 or higher** installed on your system
- A text editor or IDE (optional)

### Quick Start

1. **Clone or download** the project files to a directory
2. **Navigate** to the project directory in your terminal/command prompt
3. **Compile** the Java files:
   ```bash
   javac *.java
   ```
4. **Run** the application:
   ```bash
   java WordzzuleApp
   ```

## 📖 Usage

### Basic Usage

1. **Start the application**:
   ```bash
   java WordzzuleApp
   ```

2. **Enter your words** when prompted:
   ```
   Enter the START word:
   > cat
   
   Enter the END word:
   > dog
   ```

3. **View the result**:
   ```
   🎉 SUCCESS! Word ladder found:
   
   CAT -> COT -> DOT -> DOG
   
   Ladder length: 4 words
   Number of steps: 3
   ```

### Example Sessions

#### Successful Ladder
```
===============================================
    Welcome to Wordzzule - The Word Ladder Solver!
===============================================

Enter the START word:
> cat

Enter the END word:
> dog

Searching for word ladder from 'CAT' to 'DOG'...

🎉 SUCCESS! Word ladder found:

CAT -> COT -> DOT -> DOG

Ladder length: 4 words
Number of steps: 3
```

#### No Path Found
```
Enter the START word:
> cat

Enter the END word:
> xyz

❌ No word ladder found between 'CAT' and 'XYZ'.

This could happen if:
• One or both words are not in the dictionary
• The words have different lengths
• No valid path exists between the words
```

## 🧠 Algorithm Details

### Why Breadth-First Search (BFS)?

Wordzzule uses BFS because:
- **Guarantees shortest path**: BFS explores all paths of length N before exploring paths of length N+1
- **Optimal for unweighted graphs**: Each word transformation has equal "cost"
- **Systematic exploration**: Ensures no shorter path is missed

### Algorithm Overview

1. **Validation**: Check word lengths and dictionary presence
2. **Initialization**: Create queue for paths and set for visited words
3. **BFS Loop**:
   - Dequeue current path
   - Check if we've reached the target
   - Generate all valid one-letter neighbors
   - Enqueue new paths with unvisited neighbors
4. **Result**: Return first complete path found (guaranteed shortest)

### Time Complexity
- **Word validation**: O(1) average (HashSet lookup)
- **Neighbor generation**: O(26 × word_length) per word
- **Overall**: O(V + E) where V = vocabulary size, E = possible word transitions

### Space Complexity
- **Dictionary storage**: O(vocabulary_size)
- **BFS queue**: O(vocabulary_size × path_length) worst case

## 🏗️ Architecture

### Class Responsibilities

#### DictionaryService.java
```java
- HashSet<String> dictionary     // O(1) word lookups
- loadDictionary(String path)    // File loading with error handling
- isValidWord(String word)       // Fast word validation
```

#### WordLadderSolver.java
```java
- findLadder(start, end, dict)   // Main BFS algorithm
- getNeighbors(word, dict)       // Generate one-letter variations
```

#### WordzzuleApp.java
```java
- main(String[] args)            // Application entry point
- displayWordLadder(List)        // Beautiful result formatting
```

## 📝 Dictionary File Format

The `wordlist.txt` file should contain one word per line:

```
cat
cot
cut
bat
dog
dot
...
```

### Customizing the Dictionary

You can use your own dictionary by:
1. Creating a text file with one word per line
2. Updating the file path in `WordzzuleApp.java`:
   ```java
   String dictionaryFilePath = "your-dictionary.txt";
   ```

## 🧪 Testing

### Manual Test Cases

Try these word pairs to test different scenarios:

#### Easy Cases
- `cat` → `dog` (Expected: CAT → COT → DOT → DOG)
- `hit` → `bat` (Expected: HIT → HAT → BAT)

#### Edge Cases
- Same word: `cat` → `cat` (Expected: Single word result)
- Different lengths: `cat` → `dogs` (Expected: Error message)
- Invalid words: `xyz` → `abc` (Expected: Error message)

#### Impossible Cases
- `cat` → `run` (if no path exists in your dictionary)

### Running Tests

```bash
# Compile
javac *.java

# Test basic functionality
java WordzzuleApp
# Enter: cat, dog

# Test error handling
java WordzzuleApp
# Enter: cat, dogs (different lengths)
```

## 🔧 Troubleshooting

### Common Issues

#### "Dictionary file not found"
- **Cause**: `wordlist.txt` is missing or in wrong location
- **Solution**: Ensure `wordlist.txt` is in the same directory as your Java files

#### "No word ladder found"
- **Cause**: Words not in dictionary or no valid path exists
- **Solution**: Check that both words exist in `wordlist.txt`

#### Compilation errors
- **Cause**: Java version incompatibility or syntax errors
- **Solution**: Ensure Java 8+ and check file encoding (UTF-8)

### Performance Tips

- **Large dictionaries**: Consider using a more comprehensive word list
- **Memory usage**: For very large dictionaries, monitor JVM heap space
- **Custom optimization**: Add word length filtering for better performance

## 🎓 Educational Value

This project demonstrates:

- **Graph Algorithms**: BFS implementation and shortest path finding
- **Data Structures**: HashSet for fast lookups, Queue for BFS, ArrayList for paths
- **Java Fundamentals**: File I/O, exception handling, object-oriented design
- **Algorithm Analysis**: Time/space complexity considerations
- **Software Engineering**: Modular design, documentation, error handling

## 🤝 Contributing

Contributions are welcome! Areas for enhancement:

- **GUI Interface**: JavaFX or Swing front-end
- **Performance**: Memory optimization for large dictionaries
- **Features**: Word ladder statistics, difficulty ratings
- **Testing**: Unit tests with JUnit
- **Documentation**: Additional examples and tutorials

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Authors

- **Wordzzule Team** - Initial implementation

## 🙏 Acknowledgments

- Inspired by the classic Word Ladder puzzle
- Educational implementation focused on clarity over performance
- Thanks to the Java community for excellent documentation

---

**Happy Word Laddering! 🪜✨**