# Communication Network Designer

This is our program for the ECSE 422 Final project.

## What it does

Given an input file named `input.txt` formatted as follows (example values included): 

```
# lines starting with # are ignored
# number of nodes in the network as N (include the word nodes in this line)
6
# this is a symmetric matrix in row major form
# N(N-1)/2 numbers representing the reliability of edges (include the word reliability in this line)
0.94 0.91 0.96 0.93 0.92 0.94 0.97 0.91 0.92 0.94 0.90 0.94 0.93 0.96 0.91
# N(N-1)/2 numbers representing the  cost of edges (include the word cost in this line)
# this is a symmetric matrix in row major form
10 25 10 20 30 10 10 25 20 20 40 10 20 10 30
```

The program asks for a reliability goal and a cost constraint via command line variables and calculates the two following network designs:
 * a design that meets the reliability goal
 * a design that maximizes the reliability subject to a given cost constraint

## How to run it

Navigate to the `src/` folder via terminal. Your `input.txt` file should already be in the root folder. The program takes two arguments: a reliability goal and a cost constraint <br/>
Compile and run the program with the following commands:
```
$ javac Network.java
$ java Network <reliability goal> <cost constraint>
```

## Contributors

[Lara Kollokian, 260806317](https://github.com/larakollokian) <br/>
[Marie Vu, 260807903](https://github.com/marievu)

