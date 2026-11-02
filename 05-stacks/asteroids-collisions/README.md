# WIN #112 — 735. Asteroid Collision

**LeetCode:** [735. Asteroid Collision](https://leetcode.com/problems/asteroid-collision/)  
**Difficulty:** Medium  
**Topics:** Array, Stack, Simulation

---

## Problem Statement

We are given an integer array `asteroids` representing asteroids moving in a straight line.

Each asteroid's absolute value represents its size, while its sign represents its direction:

- **Positive integer:** The asteroid moves to the right.
- **Negative integer:** The asteroid moves to the left.

All asteroids move at the same speed. When two asteroids collide:

- The smaller asteroid explodes.
- If both asteroids have the same size, both explode.
- Asteroids moving in the same direction never collide.
- Asteroids moving away from each other never collide.

Return the state of the asteroids after all collisions have occurred.

### Example 1

**Input:**
```text
asteroids = [5, 10, -5]
```

**Output:**
```text
[5, 10]
```

**Explanation:**

The asteroid `10` collides with `-5`. Since `10` is larger, `-5` explodes. The remaining asteroids are `[5, 10]`.

### Example 2

**Input:**
```text
asteroids = [8, -8]
```

**Output:**
```text
[]
```

**Explanation:**

Both asteroids have equal sizes, so both explode.

### Example 3

**Input:**
```text
asteroids = [10, 2, -5]
```

**Output:**
```text
[10]
```

**Explanation:**

The asteroid `2` collides with `-5` and explodes. The asteroid `-5` then collides with `10` and explodes.

### Example 4

**Input:**
```text
asteroids = [3, 5, -2, -5]
```

**Output:**
```text
[3]
```

**Explanation:**

The asteroid `-2` destroys `5`, then `-5` destroys the remaining right-moving asteroid `3`? No—the asteroid `-5` collides with the remaining `3` and destroys it, while `-5` survives. The correct output for this input is `[-5]`.

---

## Core Idea

The problem requires us to simulate collisions while preserving the order of surviving asteroids.

A **stack** is useful because a newly encountered asteroid can collide with the most recently encountered asteroid that is still moving to the right.

However, not every pair of asteroids collides.

A collision is possible only when:

- The asteroid on the stack's top is moving right, meaning it is positive.
- The current asteroid is moving left, meaning it is negative.

This is why your solution uses the condition:

```java
stack.peek() > 0 && asteroid < 0
```

### Main Strategy

1. Create a stack to store surviving asteroids.
2. Process each asteroid from left to right.
3. Initially assume the current asteroid survives by setting `isAlive = true`.
4. While the current asteroid can collide with the stack's top:
   - If the stack's top asteroid is smaller, remove it and continue checking.
   - If both asteroids have equal sizes, remove the stack's top and mark the current asteroid as destroyed.
   - If the stack's top asteroid is larger, mark the current asteroid as destroyed.
5. If the current asteroid survives, push it onto the stack.
6. Convert the stack into an array and return it.

The `while` loop is important because one asteroid can destroy multiple asteroids moving to the right before it either survives or is destroyed.

---

## Java Solution

```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for (int asteroid : asteroids) {
            boolean isAlive = true;

            while (isAlive && !stack.isEmpty()
                    && stack.peek() > 0 && asteroid < 0) {

                if (stack.peek() < -asteroid) {
                    stack.pop();

                } else if (stack.peek() == -asteroid) {
                    stack.pop();
                    isAlive = false;

                } else {
                    isAlive = false;
                }
            }

            if (isAlive) {
                stack.push(asteroid);
            }
        }

        int[] result = new int[stack.size()];

        for (int i = 0; i < stack.size(); i++) {
            result[i] = stack.get(i);
        }

        return result;
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the Stack

```java
Stack<Integer> stack = new Stack<>();
```

The stack stores asteroids that have survived all collisions processed so far.

For example, after processing:

```text
asteroids = [5, 10]
```

The stack contains:

```text
Bottom -> 5, 10 <- Top
```

Both asteroids are moving to the right, so they cannot collide with each other.

### 2. Process Each Asteroid

```java
for (int asteroid : asteroids) {
    boolean isAlive = true;
```

The enhanced `for` loop processes each asteroid in the input array.

The variable `isAlive` tracks whether the current asteroid survives the collision process.

Initially, every new asteroid is assumed to survive.

If a collision destroys it, `isAlive` becomes `false`.

### 3. Identify Possible Collisions

```java
while (isAlive && !stack.isEmpty()
        && stack.peek() > 0 && asteroid < 0)
```

The loop continues only when all four conditions are satisfied:

1. The current asteroid is still alive.
2. The stack is not empty.
3. The stack's top asteroid is moving right.
4. The current asteroid is moving left.

For example:

```text
Stack top:  8  ->
Current:   -5  <-
```

These asteroids are moving toward each other, so a collision occurs.

By contrast:

```text
Stack top: -8  <-
Current:   -5  <-
```

Both asteroids are moving left. They will not collide because the current asteroid is behind the asteroid already moving left.

### 4. Case One: The Current Asteroid Is Larger

```java
if (stack.peek() < -asteroid) {
    stack.pop();
}
```

The current asteroid is negative, so `-asteroid` gives its positive size.

For example:

```text
Stack top:  3
Current:   -7
```

Compare their sizes:

```text
3 < 7
```

The stack's top asteroid explodes, so it is removed.

The current asteroid remains alive and may collide with another asteroid.

That is why the `while` loop continues instead of immediately pushing the current asteroid.

### 5. Case Two: Both Asteroids Have Equal Sizes

```java
else if (stack.peek() == -asteroid) {
    stack.pop();
    isAlive = false;
}
```

If both sizes are equal, both asteroids explode.

For example:

```text
Stack top:  8
Current:   -8
```

The algorithm removes the stack's top asteroid and sets `isAlive = false` to indicate that the current asteroid has also been destroyed.

Because `isAlive` is now `false`, the loop terminates.

The current asteroid will not be pushed onto the stack.

### 6. Case Three: The Stack's Top Asteroid Is Larger

```java
else {
    isAlive = false;
}
```

If neither of the previous conditions is true, the stack's top asteroid must be larger than the current asteroid.

For example:

```text
Stack top:  10
Current:    -4
```

Since `10 > 4`, the current asteroid explodes.

The stack's top asteroid survives, so it remains in the stack.

Setting `isAlive = false` prevents the current asteroid from being added.

### 7. Push the Current Asteroid If It Survives

```java
if (isAlive) {
    stack.push