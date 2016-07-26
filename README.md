# EasyCC [![Build Status](https://travis-ci.org/amirbawab/EasyCC.svg?branch=master)](https://travis-ci.org/amirbawab/EasyCC)
An Easy Compiler Compiler implemented in Java

### Table of contents

  * [Introducation](#introduction)
  * [Lexical analysis](#lexical-analysis)
  * [Syntax analysis](#syntax-analysis)
  * [Semantic analysis](#semantic-analysis)
  * [Code generation](#code-generation)
  * [Lexical token](#lexical-token)
  
### Introduction

EasyCC is a Compiler Compiler developed mainly to be an educational tool for students learning about compilers. The software has a command line interface and a graphical user interface. EasyCC is composed of four components: Lexical analysis, Syntax analysis, Semantic analysis and Code generation.

### Lexical analysis

Lexical analysis is the first step in building a compiler. In this step the user has to build a finite automaton representing the possible tokens in the language. The finite automaton should be provided as a JSON file following this structure:

```
{
  "states": [
    {
      "types": ..., # initial, normal, final
      "name": ..., # unique name for a state
      "token": ..., # token name describing the target input
      "backtrack": ... # true | false. Usually a true is selected if the input has an unfixed length (e.g. variable, number).
    },
    {
      ... # Another state
    }
  ],
  "edges": [
    {
      "from": ..., # name of `from` state
      "to": ...,  # name of `to` state
      "value": ... # value of the edge
    },
    {
      ... # Another edge
    }
  ]
}
```

**Edge special values:**
* `SPACE` Space or tab
* `EOL` End of line
* `EOF` End of file
* `LETTER` A-Za-z
* `NONZERO` 1-9
* `OTHER` Any character not covered by an out-edge from a state

#### Auto-generate JSON

Writting a JSON file manually can be difficult for a large finite automaton. Therefore, a graphical user interface is developed to auto-generate the lexical JSON file by entering the states and the edges connecting them.

To run the lexical generator GUI:
```
./gradlew :lexical-analysis:lexical-generator:build :lexical-analysis:lexical-generator:run
```

*Note: The states on the GUI are displayed using the BFS algorithm assuming that the graph generated is connected. Therefore a state is only visible once it is connected to another visible one*

### Syntax analysis

After defining the set of lexical tokens for a language, the next step is writting the language grammar. Each production in the grammar is composed of terminals and non-terminals tokens. The terminals tokens should match one of the lexical tokens defined in the [previous step](#lexical-analysis). The syntax for a grammar file is defined as follow:

```
% 'My language' Grammar file
% Author: EasyCC
A -> B 'C' D
B -> 'B' | EPSILON
D -> 'D'
   | EPSILON
```

**Explanation:**  
* Starting with `%` marks the line as a comment
* First production is always the starting point for a grammar. (e.g. `A -> B 'C'`)
* A non-terminal is a word without single quote (e.g. `A` and `B`)
* A terminal is a word with single quote (e.g. `'B'` and `'C'`)
* Multiple productions sharing the same left hand side can be written on one or more lines seperated by `|` symbol.
* An epsilon is represented by the `EPSILON` word.
* Spaces between words and symbols are important, otherwise an exception is thrown.
