# WIN #63 — Max Points on a Line

## Problem

[LeetCode 149 — Max Points on a Line](https://leetcode.com/problems/max-points-on-a-line/)

Given an array of points where:

```text
points[i] = [xi, yi]
```

return the maximum number of points that lie on the same straight line.

The main challenge is determining when multiple points have the **same slope** with respect to a fixed point.

---

# Example 1

```text
Input:
points = [[1,1],[2,2],[3,3]]

Output:
3
```

All three points lie on the same line:

```text
(1,1)
   \
    (2,2)
       \
        (3,3)
```

The slope between every pair is:

```text
1
```

Therefore:

```text
answer = 3
```

---

# Example 2

```text
Input:
points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]

Output:
4
```

There is a line containing four points, so the answer is:

```text
4
```

---

# My Java Solution

```java
class Solution {
    public int maxPoints(int[][] points) {

        if(points.length <= 2){
            return points.length;
        }

        int answer = 0;

        for(int i = 0; i < points.length; i++){

            HashMap<String, Integer> map = new HashMap<>();

            int maxSlope = 0;

            for(int j = i + 1; j < points.length; j++){

                int dy = points[j][1] - points[i][1];
                int dx = points[j][0] - points[i][0];

                int gcd = gcd(dy, dx);

                dy /= gcd;
                dx /= gcd;

                if(dx < 0){
                    dy = -dy;
                    dx = -dx;
                }

                if(dy == 0){
                    dx = 1;
                }

                if(dx == 0){
                    dy = 1;
                }

                String slope = dy + "/" + dx;

                int frequency =
                    map.getOrDefault(slope, 0) + 1;

                map.put(slope, frequency);

                maxSlope = Math.max(maxSlope, frequency);
            }

            answer = Math.max(answer, maxSlope + 1);
        }

        return answer;
    }

    private int gcd(int a, int b) {

        a = Math.abs(a);
        b = Math.abs(b);

        while(b != 0){
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}
```

---

# Core Idea

The key observation is:

> If we fix one point, every other point determines a slope with that point.

For example:

```text
        B(3,3)
       /
      /
 A(1,1)
```

The slope between `A` and `B` is:

```text
dy / dx
```

If several points have exactly the same slope from the same fixed point, they must lie on the same line.

Therefore, for every point:

```text
1. Fix the point.
2. Calculate the slope to every other point.
3. Count how many times each slope occurs.
4. The most frequent slope gives the largest line through that point.
5. Repeat for every point.
```

---

# Why Do We Fix One Point?

Suppose we have:

```text
A
B
C
D
```

and we want to know whether:

```text
A, B, C, D
```

are on the same line.

Instead of checking every possible combination of points, we can fix `A`.

Then calculate:

```text
slope(A,B)
slope(A,C)
slope(A,D)
```

If:

```text
slope(A,B) = slope(A,C) = slope(A,D)
```

then:

```text
A, B, C, D
```

are on the same line.

This converts a geometry problem into a **frequency-counting problem using a HashMap**.

---

# Step 1 — Handle Small Inputs

```java
if(points.length <= 2){
    return points.length;
}
```

If there are only one or two points, all points automatically lie on the same line.

Therefore:

```text
1 point → answer = 1
2 points → answer = 2
```

No further calculation is necessary.

---

# Step 2 — Iterate Through Every Point

```java
for(int i = 0; i < points.length; i++){
```

We treat:

```text
points[i]
```

as the fixed or anchor point.

For example:

```text
points[i] = [1,1]
```

Now we calculate the slope between this point and every point after it.

---

# Step 3 — Create a HashMap

```java
HashMap<String, Integer> map = new HashMap<>();
```

The HashMap stores:

```text
slope → frequency
```

For example:

```text
"1/1" → 4
"2/1" → 2
"1/0" → 3
```

This means:

```text
slope 1/1 → 4 other points
slope 2/1 → 2 other points
vertical line → 3 other points
```

The most frequent slope represents the line containing the most points through the current anchor point.

---

# Step 4 — Compare With Every Other Point

```java
for(int j = i + 1; j < points.length; j++){
```

For the current anchor point:

```text
points[i]
```

we compare it with:

```text
points[j]
```

---

# Step 5 — Calculate `dy` and `dx`

```java
int dy = points[j][1] - points[i][1];
int dx = points[j][0] - points[i][0];
```

The slope formula is:

```text
slope = Δy / Δx
```

where:

```text
Δy = y2 - y1
Δx = x2 - x1
```

For example:

```text
A = (1,1)
B = (3,3)
```

Then:

```text
dy = 3 - 1 = 2
dx = 3 - 1 = 2
```

So:

```text
slope = 2/2
```

which simplifies to:

```text
1/1
```

---

# Why We Cannot Directly Store `dy / dx`

A tempting approach would be:

```java
double slope = (double) dy / dx;
```

But floating-point values can introduce precision problems.

For example, mathematically equivalent slopes may be represented slightly differently.

Instead, we normalize the slope using:

```text
GCD
```

This lets us represent every slope using an exact integer pair.

---

# Step 6 — Find the GCD

```java
int gcd = gcd(dy, dx);
```

The GCD allows us to reduce the slope to its simplest form.

For example:

```text
dy = 6
dx = 4
```

GCD:

```text
gcd(6,4) = 2
```

Therefore:

```text
dy = 6 / 2 = 3
dx = 4 / 2 = 2
```

So the normalized slope becomes:

```text
3/2
```

---

# GCD Function

```java
private int gcd(int a, int b) {

    a = Math.abs(a);
    b = Math.abs(b);

    while(b != 0){
        int temp = a % b;
        a = b;
        b = temp;
    }

    return a;
}
```

This uses the **Euclidean Algorithm**.

The basic idea is:

```text
gcd(a,b) = gcd(b, a % b)
```

until:

```text
b = 0
```

Then:

```text
a
```

is the GCD.

---

# Example of GCD

Suppose:

```text
a = 12
b = 8
```

Then:

```text
12 % 8 = 4
```

So:

```text
gcd(12,8)
→ gcd(8,4)
```

Then:

```text
8 % 4 = 0
```

Therefore:

```text
gcd = 4
```

---

# Step 7 — Normalize the Slope

```java
dy /= gcd;
dx /= gcd;
```

Suppose:

```text
dy = 6
dx = 4
```

After normalization:

```text
dy = 3
dx = 2
```

Now the slope can be represented as:

```text
3/2
```

---

# Why Normalization Is Necessary

Consider these slopes:

```text
2/4
3/6
4/8
```

Mathematically they all represent:

```text
1/2
```

But if we stored them directly in a HashMap, they would appear as three different keys.

After GCD normalization:

```text
2/4 → 1/2
3/6 → 1/2
4/8 → 1/2
```

Now they correctly map to the same key.

---

# Step 8 — Handle Negative Slopes

This part is extremely important:

```java
if(dx < 0){
    dy = -dy;
    dx = -dx;
}
```

Consider:

```text
dy = 2
dx = -4
```

The slope is:

```text
-1/2
```

But another pair might produce:

```text
dy = -2
dx = 4
```

which is also:

```text
-1/2
```

Without normalization, these could become different HashMap keys:

```text
2/-4
-2/4
```

We want one standard representation.

So we always make:

```text
dx > 0
```

by flipping both signs when necessary.

Thus:

```text
2/-4
```

becomes:

```text
-2/4
```

and after GCD:

```text
-1/2
```

---

# Step 9 — Handle Horizontal Lines

```java
if(dy == 0){
    dx = 1;
}
```

A horizontal line has:

```text
dy = 0
```

For example:

```text
(1,5)
(3,5)
(8,5)
```

The slope is:

```text
0 / anything
```

We normalize all horizontal slopes to:

```text
0/1
```

So:

```text
0/2
0/4
0/-5
```

all become:

```text
0/1
```

This ensures they have the same HashMap key.

---

# Step 10 — Handle Vertical Lines

```java
if(dx == 0){
    dy = 1;
}
```

A vertical line has:

```text
dx = 0
```

For example:

```text
(3,1)
(3,4)
(3,9)
```

The slope would mathematically be:

```text
dy / 0
```

which is undefined.

We represent every vertical line using:

```text
1/0
```

So:

```text
2/0
5/0
-7/0
```

are normalized to:

```text
1/0
```

---

# Step 11 — Create the Slope Key

```java
String slope = dy + "/" + dx;
```

For example:

```text
dy = 1
dx = 2
```

creates:

```text
"1/2"
```

For a horizontal line:

```text
"0/1"
```

For a vertical line:

```text
"1/0"
```

---

# Step 12 — Count the Slope

```java
int frequency =
    map.getOrDefault(slope, 0) + 1;

map.put(slope, frequency);
```

Suppose the same slope occurs three times.

The HashMap evolves like:

```text
"1/1" → 1
```

then:

```text
"1/1" → 2
```

then:

```text
"1/1" → 3
```

This means three other points have the same slope from the current anchor point.

---

# Step 13 — Track the Maximum Slope Frequency

```java
maxSlope = Math.max(maxSlope, frequency);
```

Suppose:

```text
"1/1" → 4
"2/1" → 2
"1/0" → 1
```

Then:

```text
maxSlope = 4
```

So there are four other points aligned with the current anchor point.

---

# Why Do We Add 1?

At the end:

```java
answer = Math.max(answer, maxSlope + 1);
```

`maxSlope` counts only the **other points**.

The current anchor point itself is not included in the HashMap frequency.

Suppose:

```text
maxSlope = 3
```

That means:

```text
3 other points
```

have the same slope from the anchor.

Including the anchor:

```text
3 + 1 = 4
```

Therefore:

```java
maxSlope + 1
```

gives the total number of points on that line.

---

# Complete Example

Consider:

```text
points = [[1,1],[2,2],[3,3]]
```

Start with:

```text
i = 0
```

Anchor:

```text
(1,1)
```

---

## Compare With `(2,2)`

```text
dy = 2 - 1 = 1
dx = 2 - 1 = 1
```

Slope:

```text
1/1
```

HashMap:

```text
"1/1" → 1
```

---

## Compare With `(3,3)`

```text
dy = 3 - 1 = 2
dx = 3 - 1 = 2
```

GCD:

```text
gcd(2,2) = 2
```

Normalize:

```text
dy = 1
dx = 1
```

Slope:

```text
1/1
```

HashMap:

```text
"1/1" → 2
```

Therefore:

```text
maxSlope = 2
```

Add the anchor:

```text
2 + 1 = 3
```

Final answer:

```text
3
```

---

# Visual Explanation

```text
(3,3)
   /
  /
(2,2)
 /
/
(1,1)
```

From `(1,1)`:

```text
slope to (2,2) = 1/1
slope to (3,3) = 1/1
```

Same slope means:

```text
same straight line
```

Therefore:

```text
3 points
```

are collinear.

---

# Why This Works

For a fixed point:

```text
P = (x1,y1)
```

every other point:

```text
Q = (x2,y2)
```

creates a slope:

```text
(y2-y1)/(x2-x1)
```

If:

```text
slope(P,Q1)
=
slope(P,Q2)
=
slope(P,Q3)
```

then:

```text
P,Q1,Q2,Q3
```

all lie on the same line.

Therefore, finding the most frequent slope from each anchor point gives the maximum number of collinear points involving that anchor.

Repeating this for every point guarantees that we consider the line containing the global maximum.

---

# Pattern Recognition

This problem combines:

```text
Geometry
+
HashMap Frequency Counting
+
GCD Normalization
```

The important transformation is:

```text
"Which points are on the same line?"
```

becomes:

```text
"Which slope occurs most frequently?"
```

For every anchor point:

```text
Anchor Point
     ↓
Calculate slopes
     ↓
Normalize slopes
     ↓
HashMap
     ↓
Count frequencies
     ↓
Maximum frequency
```

---

# Why HashMap?

Without a HashMap, we would repeatedly search whether a particular slope has already appeared.

The HashMap gives us:

```text
slope → count
```

and allows average:

```text
O(1)
```

lookup and insertion.

So instead of explicitly checking every group of points, we count identical slopes.

---

# Important Normalization Rules

The normalization logic can be remembered as:

```text
1. Divide dy and dx by GCD.

2. Make dx positive.

3. Horizontal:
      dy = 0
      dx = 1

4. Vertical:
      dx = 0
      dy = 1
```

This produces a unique representation for each slope.

---

# Slope Examples

| `dy` | `dx` | Normalized |
| ---: | ---: | ---------- |
|  `2` |  `4` | `1/2`      |
|  `3` |  `6` | `1/2`      |
| `-2` |  `4` | `-1/2`     |
|  `2` | `-4` | `-1/2`     |
|  `0` |  `5` | `0/1`      |
|  `0` | `-3` | `0/1`      |
|  `5` |  `0` | `1/0`      |
| `-7` |  `0` | `1/0`      |

The goal is that mathematically identical slopes produce the same key.

---

# Why We Don't Need to Check Every Pair of Lines

A naive approach could try to:

```text
Pick 2 points
Find their line
Count every other point on that line
```

This can become expensive.

Instead, fixing one point lets us group all other points according to their slope.

For one anchor:

```text
        slope 1/1
           ↙
          A
        ↙
       B

        slope 2/1
           ↘
            C
```

The HashMap automatically groups points by line direction.

---

# Complexity

Let:

```text
n = points.length
```

We choose every point as an anchor.

For each anchor, we compare it with the remaining points:

```text
O(n)
```

Therefore:

```text
O(n × n)
```

or:

```text
O(n²)
```

The GCD operation takes logarithmic time in the magnitude of the coordinates, but with the given coordinate bounds it is small; the standard overall classification of the algorithm is:

```text
Time: O(n² log C)
```

where `C` represents the coordinate difference magnitude.

In practical interview discussion, this is commonly treated as:

```text
O(n²)
```

with GCD normalization.

---

# Space Complexity

For every anchor point, the HashMap stores at most one entry for each distinct slope to the other points.

Therefore:

```text
O(n)
```

auxiliary space.

The map is recreated for each anchor:

```java
HashMap<String, Integer> map = new HashMap<>();
```

so it does not accumulate across all iterations.

---

# Edge Cases

## 1. One Point

```text
[[1,1]]
```

Answer:

```text
1
```

Handled by:

```java
if(points.length <= 2)
```

---

## 2. Two Points

```text
[[1,1],[2,2]]
```

Any two points define a line.

Answer:

```text
2
```

---

## 3. Horizontal Line

```text
[[1,5],[2,5],[3,5]]
```

All points have:

```text
dy = 0
```

Normalized slope:

```text
0/1
```

Answer:

```text
3
```

---

## 4. Vertical Line

```text
[[5,1],[5,2],[5,3]]
```

All points have:

```text
dx = 0
```

Normalized slope:

```text
1/0
```

Answer:

```text
3
```

---

## 5. Negative Slope

For example:

```text
(1,5)
(2,3)
(3,1)
```

The slope is:

```text
-2/1
```

The sign normalization ensures that equivalent representations are stored consistently.

---

# The Key Insight

The most important thing to remember from this problem is:

> **Fix one point and group every other point by its normalized slope. The most frequent slope represents the largest line passing through that point.**

The algorithm is:

```text
For every point i:

    Create HashMap

    For every point j > i:

        Calculate dy
        Calculate dx

        Normalize using GCD
        Normalize sign
        Handle horizontal line
        Handle vertical line

        Count slope

    Update global maximum
```

---

# Final Takeaway

This problem looks like a geometry problem, but the main algorithmic idea is actually **HashMap frequency counting**.

The transformation is:

```text
Points on the same line
        ↓
Same slope from a fixed point
        ↓
Normalize slope
        ↓
HashMap
        ↓
Count frequency
        ↓
Maximum frequency + 1
```

The three concepts to remember are:

```text
1. Fix an anchor point.
2. Normalize slopes using GCD.
3. Count slopes using HashMap.
```

That converts the problem from checking geometric lines directly into an efficient frequency-counting problem.
