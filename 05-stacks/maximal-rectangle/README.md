# WIN #118 — 85. Maximal Rectangle

**LeetCode:** [85. Maximal Rectangle](https://leetcode.com/problems/maximal-rectangle/)  
**Difficulty:** Hard  
**Topics:** Array, Dynamic Programming, Stack, Matrix, Monotonic Stack

---

## 1. Problem Statement

Given a binary matrix filled with `'0'` and `'1'`, find the largest rectangle containing only `'1'` and return its area.

### Examples

**Example 1**

Input:
```text
matrix = [
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
```

Output:
```text
6
```

Explanation: The largest rectangle containing only ones has an area of 6.

**Example 2**

Input:
```text
matrix = [["0"]]
```

Output:
```text
0
```

**Example 3**

Input:
```text
matrix = [["1"]]
```

Output:
```text
1
```

---

## 2. Core Idea — Matrix to Histogram + Monotonic Stack

The main challenge is finding the largest rectangle in a 2D matrix.

Instead of checking every possible rectangle, we transform the matrix into a series of histograms.

We process the matrix row by row.

For every row:
1. Update the height of each column.
2. If the current cell is `'1'`, increase that column's height.
3. If the current cell is `'0'`, reset that column's height to zero.
4. Treat the updated height array as a histogram.
5. Calculate the largest rectangle using the Largest Rectangle in Histogram algorithm.

Finally, maintain the maximum area across all rows.

### Why does this work?

Each histogram represents consecutive ones ending at the current row.

A rectangle in that histogram corresponds to a rectangle of ones in the original matrix.

By processing every row as the bottom boundary of a possible rectangle, we consider every possible maximal rectangle.

---

## 3. Java Solution

```java
class Solution {
    public int maximalRectangle(char[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        int[] height = new int[n];
        Arrays.fill(height, 0);
        int maxArea = 0;

        for(int i=0;i <m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j] == '1'){
                    height[j]++;
                }else{
                    height[j] = 0;
                }
            }
            int currentArea = largestHistogram(height);
            maxArea = Math.max(maxArea, currentArea);
        }
        return maxArea;
        
    }

    private int largestHistogram(int[] heights){
        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        int n = heights.length;

        for(int i=0;i<=n;i++){
            int currentHeight = (i == n)? 0: heights[i];

            while(!stack.isEmpty()&& heights[stack.peek()] > currentHeight){
                int height = heights[stack.pop()];

                int width;
                if(stack.isEmpty()){
                    width = i;
                }
                else{
                    width = i - stack.peek()-1;
                }

                int area = height * width;
                answer = Math.max(answer, area);
            }

            stack.push(i);
        }

        return answer;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Get matrix dimensions

```java
int m = matrix.length;
int n = matrix[0].length;
```

- `m` represents the number of rows.
- `n` represents the number of columns.

### Step 2: Initialize the histogram

```java
int[] height = new int[n];
Arrays.fill(height, 0);
int maxArea = 0;
```

Create an array that stores the consecutive number of ones in each column, ending at the current row.

Initially, all heights are zero.

For example:

```text
height = [0, 0, 0, 0, 0]
```

### Step 3: Traverse every row

```java
for(int i=0;i <m;i++){
```

Each row becomes the bottom boundary of a possible rectangle.

We update the histogram before calculating its maximum rectangle.

### Step 4: Update histogram heights

```java
for(int j=0;j<n;j++){
    if(matrix[i][j] == '1'){
        height[j]++;
    }else{
        height[j] = 0;
    }
}
```

This is the most important transformation.

If the current cell contains `'1'`, increase the height.

If it contains `'0'`, reset the height to zero because consecutive ones are broken.

For example:

```text
Current row: [1, 0, 1, 1, 1]
Previous:    [1, 0, 1, 0, 0]

Updated:     [2, 0, 2, 1, 1]
```

The updated array represents consecutive ones ending at the current row.

### Step 5: Calculate the largest histogram rectangle

```java
int currentArea = largestHistogram(height);
```

Now we reuse the solution to LeetCode 84 — Largest Rectangle in Histogram.

The helper method:
- Maintains a monotonic increasing stack of indices.
- Finds shorter bars that establish rectangle boundaries.
- Calculates rectangle width and area.
- Returns the largest histogram rectangle.

### Step 6: Update the global maximum

```java
maxArea = Math.max(maxArea, currentArea);
```

Compare the current row's largest rectangle with the maximum area found so far.

After processing every row, return the global maximum.

---

## 5. Understanding the Histogram Transformation

Consider the following matrix:

```text
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```

We process each row and build the histogram.

### After Row 0

```text
Matrix row:  1 0 1 0 0
Height:      1 0 1 0 0
```

### After Row 1

```text
Matrix row:  1 0 1 1 1
Height:      2 0 2 1 1
```

### After Row 2

```text
Matrix row:  1 1 1 1 1
Height:      3 1 3 2 2
```

### After Row 3

```text
Matrix row:  1 0 0 1 0
Height:      4 0 0 3 0
```

Every updated height array is treated as an independent histogram.

The largest rectangle from each histogram is calculated, and the global maximum is maintained.

---

## 6. Dry Run

Let's focus on Row 2:

```text
Height = [3, 1, 3, 2, 2]
```

The histogram looks like:

```text
      3       3
      |       |   2   2
      |   1   |   |   |
      |   |   |   |   |
      |   |   |   |   |
```

The largest rectangle has:

```text
Height = 2
Width  = 3
Area   = 6
```

It spans columns 2, 3 and 4 (zero-based indexing).

The corresponding matrix region is:

```text
1 1 1
1 1 1
```

Therefore:

```text
Maximum Area = 2 × 3 = 6
```

This is the answer for the complete matrix.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `[["0"]]` | 0 | No ones |
| `[["1"]]` | 1 | Single one |
| `[["1","1"]]` | 2 | Horizontal rectangle |
| `[["1"],["1"]]` | 2 | Vertical rectangle |
| `[["1","0"],["1","1"]]` | 2 | Rectangle formed across rows |
| All zeros | 0 | No valid rectangle |
| All ones | Rows × Columns | Entire matrix forms one rectangle |

---

## 8. Complexity Analysis

Let:
- `m` = number of rows.
- `n` = number of columns.

**Time Complexity: O(m × n)**

For every row:
- Updating histogram heights takes O(n).
- Finding the largest histogram rectangle takes O(n), because each index is pushed and popped at most once.

Therefore:

```text
O(m × (n + n))
= O(m × n)
```

**Space Complexity: O(n)**

- The histogram height array requires O(n).
- The monotonic stack inside `largestHistogram()` requires O(n).

Therefore, auxiliary space complexity is O(n).

---

## 9. Pattern Recognition

**Pattern: 2D Matrix → 1D Histogram → Monotonic Stack**

This technique is useful when:
- You need to find the largest rectangle in a binary matrix.
- Rectangles must contain consecutive columns and rows.
- You can represent consecutive valid cells as histogram heights.
- A known 1D histogram solution can solve each row independently.

The important observation is that a 2D rectangle can be represented as a rectangle in a histogram when we fix its bottom row.

---

## 10. Connection to Largest Rectangle in Histogram

This problem builds directly on the histogram technique.

The two problems have different responsibilities:

| Largest Rectangle in Histogram | Maximal Rectangle |
|---|---|
| Receives a height array | Receives a binary matrix |
| Finds the largest histogram rectangle | Builds histograms from matrix rows |
| Uses a monotonic stack | Uses matrix traversal and a monotonic stack |
| O(n) time | O(m × n) time |

The helper method `largestHistogram()` is essentially the same algorithm from LeetCode 84.

The new challenge is converting the matrix into the correct histogram after every row.

---

## 11. Final Mental Model

Think of the matrix as a collection of histograms.

1. Start with an empty histogram.
2. Process the matrix one row at a time.
3. Increase column heights when you encounter `'1'`.
4. Reset column heights when you encounter `'0'`.
5. Calculate the largest rectangle in the updated histogram.
6. Keep track of the maximum area.

The central transformation is:

```text
Binary Matrix
     |
     v
Update Consecutive Heights
     |
     v
Histogram for Current Row
     |
     v
Monotonic Increasing Stack
     |
     v
Largest Rectangle for Current Row
     |
     v
Update Global Maximum
```

**The main takeaway:** When a 2D binary matrix asks for the largest rectangle of ones, think about fixing a row as the bottom boundary and converting the rows above it into histogram heights. Then reuse the monotonic stack solution for Largest Rectangle in Histogram.