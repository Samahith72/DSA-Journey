# WIN #142 — 374. Guess Number Higher or Lower

[LeetCode Problem](https://leetcode.com/problems/guess-number-higher-or-lower/)

**Difficulty:** Easy

**Topics:** Binary Search, Interactive

---

## Problem Statement

We are playing a guessing game. The API has picked a number between `1` and `n`.

You need to find the picked number by calling the provided `guess(int num)` API.

The API returns:

- `-1` if your guess is higher than the picked number.
- `1` if your guess is lower than the picked number.
- `0` if your guess is correct.

Return the picked number.

---

## Example 1

**Input:**
```text
n = 10
pick = 6
```

**Output:**
```text
6
```

**Explanation:**

- Guess `5`: the picked number is higher.
- Guess `8`: the picked number is lower.
- Guess `6`: the guess is correct.

## Example 2

**Input:**
```text
n = 1
pick = 1
```

**Output:**
```text
1
```

## Example 3

**Input:**
```text
n = 2
pick = 1
```

**Output:**
```text
1
```

---

## Core Idea

The picked number lies within a known range, and the API tells us which half of the range can contain it.

This makes Binary Search suitable.

We maintain two boundaries:

- `low`: The smallest possible picked number.
- `high`: The largest possible picked number.

For each middle value, we call `guess(mid)`:

1. If the response is `0`, we found the picked number.
2. If the response is `-1`, our guess was too high, so search the left half.
3. If the response is `1`, our guess was too low, so search the right half.

Each incorrect guess eliminates roughly half of the remaining candidates.

---

## Java Solution — Binary Search Using the Guess API

```java
/* 
 * Forward declaration of guess API.
 * @param  num   your guess
 * @return       -1 if your guess is higher than the picked number
 *                1 if your guess is lower than the picked number
 *                otherwise return 0
 * int guess(int num);
 */

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int low = 0;
        int high = n;

        while(low <= high){
            int mid = low + (high - low)/2;

            int response = guess(mid);

            if(response == -1){
                high = mid-1;
            }else if(response == 1){
                low = mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }
}
```

**Range note:** The problem guarantees that the picked number is between `1` and `n`. Starting with `low = 1` is the conventional range. Your submitted `low = 0` may make an unnecessary API call at `0`, but the response moves the search into the valid range.

---

## Step-by-Step Explanation

### 1. Initialize the search range

```java
int low = 0;
int high = n;
```

The code begins with the range from `0` to `n`. As noted above, the problem’s actual candidate range is `1` through `n`.

### 2. Calculate the middle value

```java
int mid = low + (high - low)/2;
```

We choose the middle of the current range.

This form avoids potential overflow from calculating `(low + high) / 2`.

### 3. Call the API

```java
int response = guess(mid);
```

The API compares `mid` with the hidden picked number and returns a direction.

### 4. Update the search boundaries

**Guess is too high:**

```java
if(response == -1){
    high = mid-1;
}
```

The picked number is smaller than `mid`, so discard `mid` and everything to its right.

**Guess is too low:**

```java
else if(response == 1){
    low = mid+1;
}
```

The picked number is greater than `mid`, so discard `mid` and everything to its left.

**Guess is correct:**

```java
else{
    return mid;
}
```

A response of `0` means the picked number has been found.

### 5. Return the result

```java
return -1;
```

Under the problem’s guarantee that a picked number exists in the range, the correct branch returns before the search range becomes empty. The final `-1` is a fallback.

---

## Dry Run

Consider:

```text
n = 10
pick = 6
```

Initial range:

```text
low = 0
high = 10
```

| Iteration | Low | High | Mid | API response | Decision |
|---|---:|---:|---:|---|---|
| 1 | 0 | 10 | 5 | `1` — too low | `low = 6` |
| 2 | 6 | 10 | 8 | `-1` — too high | `high = 7` |
| 3 | 6 | 7 | 6 | `0` — correct | Return `6` |

### Iteration 1

```text
mid = 0 + (10 - 0) / 2
mid = 5
```

The API returns `1`, meaning the picked number is higher than `5`.

Update:

```java
low = mid + 1;
```

New range:

```text
low = 6
high = 10
```

### Iteration 2

```text
mid = 6 + (10 - 6) / 2
mid = 8
```

The API returns `-1`, meaning the picked number is lower than `8`.

Update:

```java
high = mid - 1;
```

New range:

```text
low = 6
high = 7
```

### Iteration 3

```text
mid = 6 + (7 - 6) / 2
mid = 6
```

The API returns `0`.

The picked number is `6`, so the method returns:

```text
6
```

---

## Complexity Analysis

Let `n` be the upper bound of the picked number.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(log n) | Each incorrect guess approximately halves the remaining range. |
| Space | O(1) | Only a fixed number of variables are used. |

The number of API calls is also O(log n).

---

## Pattern Recognition

When a problem involves:

- Searching for a hidden number within a known range.
- Receiving feedback that indicates whether the guess is too low or too high.
- Eliminating half of the remaining candidates after each attempt.

Think about **Binary Search with Directional Feedback**.

The API response determines which half of the search range can be discarded.

---

## Key Takeaway

Binary Search works even when the value being searched for is hidden behind an API.

Use the API response to decide which boundary to move:

- Too high: `high = mid - 1`
- Too low: `low = mid + 1`
- Correct: return `mid`

**Final complexity: O(log n) time and O(1) extra space.**