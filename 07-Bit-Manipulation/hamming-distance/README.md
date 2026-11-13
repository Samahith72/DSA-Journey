# WIN #126 — 461. Hamming Distance

**LeetCode:** [461. Hamming Distance](https://leetcode.com/problems/hamming-distance/)  
**Difficulty:** Easy  
**Topics:** Bit Manipulation

---

## 1. Problem Statement

The Hamming distance between two integers is the number of positions at which their corresponding bits are different.

Given two integers `x` and `y`, return their Hamming distance.

### Examples

**Example 1**

Input:
```text
x = 1, y = 4
```

Output:
```text
2
```

Explanation:

```text
1 = 001
4 = 100
    ---
    101
```

The binary representations differ at two positions.

**Example 2**

Input:
```text
x = 3, y = 1
```

Output:
```text
1
```

Explanation:

```text
3 = 011
1 = 001
    ---
    010
```

Only one bit position is different.

---

## 2. Core Idea — XOR + Brian Kernighan's Algorithm

Your solution uses two operations.

**Step 1: XOR the two numbers**

```java
int distance = x ^ y;
```

XOR returns `1` whenever the corresponding bits are different.

Therefore, the result contains `1` at every position where `x` and `y` differ.

**Step 2: Count the set bits**

```java
while(distance != 0){
    distance = distance & (distance - 1);
    count++;
}
```

We use Brian Kernighan's Algorithm to count the number of set bits.

Remember:

```text
n & (n - 1)
```

removes the rightmost set bit.

Every iteration removes one differing bit, so the number of iterations equals the Hamming distance.

### The complete mathematical idea

\[
HammingDistance(x,y)=NumberOfSetBits(x \oplus y)
\]

---

## 3. Java Solution

```java
class Solution {
    public int hammingDistance(int x, int y) {
        int distance = x ^ y;
        int count = 0;

        while(distance != 0){
            distance = distance & (distance - 1);
            count++;
        }

        return count;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Find differing bit positions

```java
int distance = x ^ y;
```

XOR compares both numbers bit by bit.

| x | y | x XOR y |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 1 |
| 1 | 0 | 1 |
| 1 | 1 | 0 |

The resulting number contains a set bit wherever the original numbers differ.

### Step 2: Initialize the counter

```java
int count = 0;
```

This variable tracks how many differing bit positions exist.

### Step 3: Remove the rightmost set bit

```java
while(distance != 0){
    distance = distance & (distance - 1);
    count++;
}
```

Each iteration:
1. Removes one set bit.
2. Increments the counter.

The loop continues until all set bits are removed.

### Step 4: Return the Hamming distance

```java
return count;
```

The counter now represents the total number of differing bit positions.

---

## 5. Dry Run

Let's trace:

```text
x = 1
y = 4
```

### Step 1: Convert to binary

```text
x = 001
y = 100
```

### Step 2: Apply XOR

```text
  001
^ 100
-----
  101
```

Therefore:

```text
distance = 5
```

### Step 3: Count set bits

Initial state:

```text
distance = 101
count = 0
```

| Iteration | Distance | Distance - 1 | AND Result | Count |
|---|---|---|---|---:|
| 1 | `101` | `100` | `100` | 1 |
| 2 | `100` | `011` | `000` | 2 |

Now `distance = 0`, so the loop terminates.

Final answer:

```text
2
```

There are two positions where the binary representations differ.

---

## 6. Understanding Why Both Operations Work Together

Consider:

```text
x = 7
y = 10
```

Binary representations:

```text
7  = 0111
10 = 1010
```

XOR:

```text
  0111
^ 1010
------
  1101
```

The XOR result contains three set bits.

Now count them:

```text
1101 → 1100 → 1000 → 0000
```

Three set bits were removed.

Therefore:

```text
Hamming Distance = 3
```

This demonstrates how XOR identifies the differences and Brian Kernighan's Algorithm counts them.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `x = 0, y = 0` | 0 | No differing bits |
| `x = 1, y = 4` | 2 | Two differing positions |
| `x = 3, y = 1` | 1 | One differing position |
| `x = 7, y = 7` | 0 | Identical numbers |
| `x = 7, y = 10` | 3 | Three differing positions |

---

## 8. Complexity Analysis

Let `k` represent the number of differing bit positions.

**Time Complexity: O(k)**

- XOR takes constant time.
- Brian Kernighan's Algorithm executes once for every set bit.
- The loop therefore runs exactly `k` times.

Since Java integers have 32 bits, the maximum number of iterations is 32.

**Space Complexity: O(1)**

Only two additional variables are used:
- `distance`
- `count`

No additional data structures are required.

---

## 9. Pattern Recognition

**Pattern: XOR + Set Bit Counting**

This combination is useful when:
- You need to find differences between two binary numbers.
- You need to count differing bit positions.
- You need to compare binary representations efficiently.
- You need to count set bits in an XOR result.

The key observation:

```text
XOR identifies differing positions.
Brian Kernighan's Algorithm counts those positions.
```

---

## 10. Final Mental Model

Think of this problem in two stages.

**Stage 1: Find the differences**

```text
distance = x ^ y
```

Every `1` represents a position where the numbers differ.

**Stage 2: Count the differences**

```text
distance = distance & (distance - 1)
count++
```

Remove one set bit at a time until no set bits remain.

The complete logic:

```text
Initialize distance = x XOR y
Initialize count = 0

While distance is not zero:
    Remove rightmost set bit
    Increment count

Return count
```

**The main takeaway:** Hamming Distance is simply the number of set bits in the XOR of two numbers. Combining XOR with Brian Kernighan's Algorithm gives an efficient O(1)-bounded solution for 32-bit integers.