# EasyCC [![Build Status](https://travis-ci.org/amirbawab/EasyCC.svg?branch=master)](https://travis-ci.org/amirbawab/EasyCC)
An Easy Compiler Compiler implemented in Java

### Table of contents

  * [Introducation](#introduction)
  * [Lexical-Analyzer](#lexical-analysis)

### Introduction

EasyCC is a Compiler Compiler developed mainly to be an educational tool for students learning about compilers. The software has a command line interface and a graphical user interface. EasyCC is composed of four components: Lexical analysis, Syntax analysis, Semantic analysis and Code generation.

### Lexical analysis

Lexical analysis is the first step in building a compiler. In this step the user has to build a finite automaton representing the possible tokens in the language. The finiate automaton should be provided by a JSON file following this structure:

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
