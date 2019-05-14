# Ancestial

As the test ask for, I implement NumberFinder and its methods.

# Method readFromFile
On readFromFile method I follow the steps:
   1. Read File
   2. Parse file to JsonArray
   3. Parse each array item into ArrayObject and after that put value at String
   4. Create a Regex Pattern to get just the numbers from file
   5. Create CustomNumberEntity using Reflection once the contructors are private
   6. Put on the array 

# Method contains
On the contains method I understand that the idea is find the value,
using less comparisons as possible, but once I read the constrains I have no idea
how to do it, so I use the basic sequential search, that is never the most
performance.

I think about use a Binary Search Tree or just order data to make a Binary search, but I will have to change the structure, 
and also use compare to load information, so I have the same problem.

